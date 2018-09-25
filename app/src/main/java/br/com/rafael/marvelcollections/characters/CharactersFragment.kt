package br.com.rafael.marvelcollections.characters

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createCharactersComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(CharactersViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = characters_progress
        recyclerView = characters_recyclerview
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseCharactersComponent()
    }
}