package br.com.rafael.data.db

import android.arch.persistence.room.*
import br.com.rafael.data.entities.CharacterData

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getCharacters(): List<CharacterData>

    @Query("SELECT * FROM character WHERE id=:characterId")
    fun get(characterId: Int): CharacterData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCharacter(character: CharacterData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCharacters(characters: List<CharacterData>)

    @Delete
    fun removeCharacter(character: CharacterData)

    @Query("DELETE from character")
    fun clear()
}