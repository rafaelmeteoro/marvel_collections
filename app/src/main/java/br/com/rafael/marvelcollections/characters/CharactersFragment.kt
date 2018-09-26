package br.com.rafael.marvelcollections.characters

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import br.com.rafael.marvelcollections.R
import br.com.rafael.marvelcollections.common.App
import br.com.rafael.marvelcollections.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

class CharactersFragment : Fragment() {

    @Inject
    lateinit var factory: CharactersVMFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: CharactersViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createCharactersComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(CharactersViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.getCharacters()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(activity, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleViewState(state: CharactersViewState) {
        progressBar.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.characters?.let { charactersAdapter.addCharacters(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = characters_progress
        recyclerView = characters_recyclerview
        charactersAdapter = CharactersAdapter(imageLoader)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = charactersAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseCharactersComponent()
    }
}