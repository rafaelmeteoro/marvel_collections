package br.com.rafael.marvelcollections.characters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rafael.marvelcollections.R
import br.com.rafael.marvelcollections.common.ImageLoader
import br.com.rafael.marvelcollections.entities.Character
import kotlinx.android.synthetic.main.character_adapter_cell.view.*

class CharactersAdapter constructor(
        private val imageLoader: ImageLoader,
        private val onCharacterSelected: (Character, View) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.CharacterCellViewHolder>() {

    private val characters: MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.character_adapter_cell,
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

    fun addCharacters(characters: List<Character>) {
        this.characters.addAll(characters)
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
//            setOnClickListener { listener(character, itemView) }
            character_card.setOnClickListener { listener(character, itemView) }
        }
    }
}