package br.com.rafael.marvelcollections.favorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rafael.marvelcollections.R
import br.com.rafael.marvelcollections.common.ImageLoader
import br.com.rafael.marvelcollections.entities.Character
import kotlinx.android.synthetic.main.favorite_characters_adapter_row.view.*

class FavoriteCharactersAdapter constructor(private val imageLoader: ImageLoader,
                                            private val onCharacterSelected: (Character, View) -> Unit) : RecyclerView.Adapter<FavoriteCharactersAdapter.CharacterCellViewHolder>() {

    private var characters: List<Character> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.favorite_characters_adapter_row,
                parent,
                false)
        return CharacterCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: CharacterCellViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character, imageLoader, onCharacterSelected)
    }

    fun setCharacters(characters: List<Character>) {
        this.characters = characters
        notifyDataSetChanged()
    }

    class CharacterCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character, imageLoader: ImageLoader, listener: (Character, View) -> Unit) = with(itemView) {
            name.text = character.name
            val path = character.thumbnail?.path
            val extension = character.thumbnail?.extension
            if (path != null && extension != null) {
                imageLoader.load("$path.$extension", image)
            }
            character.description?.let {
                description.text = character.description
                description.visibility = View.VISIBLE
            } ?: run {
                description.visibility = View.GONE
            }
            character_card.setOnClickListener { listener(character, itemView) }
        }
    }
}