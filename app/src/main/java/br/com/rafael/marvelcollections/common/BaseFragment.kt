package br.com.rafael.marvelcollections.common

import android.support.v4.app.Fragment
import android.view.View
import br.com.rafael.marvelcollections.detail.CharacterDetailActivity
import br.com.rafael.marvelcollections.entities.Character

open class BaseFragment : Fragment() {

    protected fun navigateToCharacterDetailScreen(character: Character, view: View) {
        startActivity(CharacterDetailActivity.newIntent(
                context = context!!,
                characterId = character.id,
                imageUrl = "${character.thumbnail?.path}.${character.thumbnail?.extension}"
        ))
    }
}