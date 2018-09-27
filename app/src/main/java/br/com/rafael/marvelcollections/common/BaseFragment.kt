package br.com.rafael.marvelcollections.common

import android.app.ActivityOptions
import android.support.v4.app.Fragment
import android.util.Pair
import android.view.View
import br.com.rafael.marvelcollections.R
import br.com.rafael.marvelcollections.detail.CharacterDetailActivity
import br.com.rafael.marvelcollections.entities.Character

open class BaseFragment : Fragment() {

    protected fun navigateToCharacterDetailScreen(character: Character, view: View) {
        var activityOptions: ActivityOptions? = null

        val imageForTransition: View? = view.findViewById(R.id.image)
        imageForTransition?.let {
            val posterSharedElement: Pair<View, String> = Pair.create(it, getString(R.string.transition_poster))
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, posterSharedElement)
        }

        startActivity(CharacterDetailActivity.newIntent(
                context = context!!,
                characterId = character.id,
                imageUrl = "${character.thumbnail?.path}.${character.thumbnail?.extension}"
        ), activityOptions?.toBundle())

        activity?.overridePendingTransition(0, 0)
    }
}