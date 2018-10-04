package br.com.rafael.marvelcollections.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.rafael.marvelcollections.R
import br.com.rafael.marvelcollections.common.App
import br.com.rafael.marvelcollections.common.BaseFragment
import br.com.rafael.marvelcollections.common.ImageLoader
import javax.inject.Inject

class FavoriteCharactersFragment : BaseFragment() {

    @Inject
    lateinit var factory: FavoriteCharactersVMFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: FavoriteCharactersViewModel

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

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_chracters_favorite, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseFavoritesComponent()
    }
}