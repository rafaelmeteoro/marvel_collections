package br.com.rafael.marvelcollections

import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.marvelcollections.entities.Character
import br.com.rafael.marvelcollections.entities.Thumbnail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterEntityCharacterMapper @Inject constructor() : Mapper<CharacterEntity, Character>() {

    override fun mapFrom(from: CharacterEntity): Character {
        val character = Character(
                id = from.id,
                name = from.name,
                description = from.description,
                modified = from.modified
        )

        val thumbnail = Thumbnail(
                path = from.thumbnail.path,
                extension = from.thumbnail.extension
        )

        character.thumbnail = thumbnail

        return character
    }
}