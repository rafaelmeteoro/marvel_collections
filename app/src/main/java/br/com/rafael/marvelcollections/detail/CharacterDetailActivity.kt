package br.com.rafael.marvelcollections.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import br.com.rafael.marvelcollections.R
import br.com.rafael.marvelcollections.common.App
import br.com.rafael.marvelcollections.common.ImageLoader
import br.com.rafael.marvelcollections.common.SimpleTransitionEndedCallback
import kotlinx.android.synthetic.main.activity_character_detail.*
import kotlinx.android.synthetic.main.details_description_section.*
import javax.inject.Inject

class CharacterDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: CharacterDetailVMFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var detailsViewModel: CharacterDetailViewModel
    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var posterImage: ImageView
    private lateinit var backdropImage: ImageView
    private lateinit var backButton: View
    private lateinit var favoriteButton: FloatingActionButton

    companion object {
        private const val CHARACTER_ID: String = "extra_character_id"
        private const val CHARACTER_IMAGE_URL: String = "extra_character_image_url"

        fun newIntent(context: Context, characterId: Int, imageUrl: String?): Intent {
            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(CHARACTER_ID, characterId)
            intent.putExtra(CHARACTER_IMAGE_URL, imageUrl)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        postponeEnterTransition()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_FULLSCREEN

        (application as App).createDetailsSubComponent().inject(this)

        factory.characterId = intent.getIntExtra(CHARACTER_ID, 0)
        detailsViewModel = ViewModelProviders.of(this, factory).get(CharacterDetailViewModel::class.java)

        name = details_name
        description = details_description
        posterImage = details_poster
        backdropImage = details_backdrop
        backButton = details_back_button
        backButton.setOnClickListener { finish() }
        favoriteButton = details_favorite_fab

        val posterUrl = intent.getStringExtra(CHARACTER_IMAGE_URL)
        posterUrl?.let {
            imageLoader.load(it, posterImage) {
                startPostponedEnterTransition()
            }
        } ?: run {
            startPostponedEnterTransition()
        }

        window.sharedElementEnterTransition.addListener(SimpleTransitionEndedCallback {
            observerViewState()
        })

        // If we don't have any entering transition
        if (savedInstanceState != null) {
            observerViewState()
        } else {
            detailsViewModel.getCharacterDetails()
            observerViewState()
        }
    }

    private fun observerViewState() {
        detailsViewModel.viewState.observe(this, Observer { viewState -> handleViewState(viewState) })
        detailsViewModel.favoriteState.observe(this, Observer { favorite -> handleFavoriteStateChange(favorite) })
        detailsViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleViewState(state: CharacterDetailViewState?) {
        if (state == null) return

        name.text = state.name
        description.text = state.description

        val transition = Slide()
        transition.excludeTarget(posterImage, true)
        transition.duration = 750
        TransitionManager.beginDelayedTransition(details_root_view, transition)

        name.visibility = View.VISIBLE
        details_description_section.visibility =
                if (!state.description.isNullOrEmpty()) View.VISIBLE else View.GONE

        state.backdropUrl?.let { imageLoader.load(it, backdropImage) }
    }

    private fun handleFavoriteStateChange(favorite: Boolean?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releaseDetailsComponent()
    }
}