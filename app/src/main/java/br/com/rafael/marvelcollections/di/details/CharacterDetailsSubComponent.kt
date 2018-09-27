package br.com.rafael.marvelcollections.di.details

import br.com.rafael.marvelcollections.detail.CharacterDetailActivity
import dagger.Subcomponent

@DetailsScope
@Subcomponent(modules = [CharacterDetailsModule::class])
interface CharacterDetailsSubComponent {
    fun inject(characterDetailsActivity: CharacterDetailActivity)
}