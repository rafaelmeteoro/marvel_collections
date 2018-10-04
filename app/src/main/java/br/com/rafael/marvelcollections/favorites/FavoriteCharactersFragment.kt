package br.com.rafael.marvelcollections.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import br.com.rafael.marvelcollections.R
import br.com.rafael.marvelcollections.common.App
import br.com.rafael.marvelcollections.common.BaseFragment
import br.com.rafael.marvelcollections.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_chracters_favorite.*
import javax.inject.Inject

class FavoriteCharactersFragment : BaseFragment() {

    @Inject
    lateinit var factory: FavoriteCharactersVMFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: FavoriteCharactersViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyMessage: TextView
    private lateinit var favoriteCharactersAdapter: FavoriteCharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as App).createFavoritesComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(FavoriteCharactersViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleViewState(state: FavoriteCharactersViewState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        emptyMessage.visibility = if (!state.isLoading && state.isEmpty) View.VISIBLE else View.GONE
        state.characters?.let { favoriteCharactersAdapter.setCharacters(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_chracters_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = favorite_characters_progress
        recyclerView = favorite_characters_recyclerview
        emptyMessage = favorite_characters_empty_message
        favoriteCharactersAdapter = FavoriteCharactersAdapter(imageLoader, { character, view ->
            navigateToCharacterDetailScreen(character, view)
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = favoriteCharactersAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseFavoritesComponent()
    }
}