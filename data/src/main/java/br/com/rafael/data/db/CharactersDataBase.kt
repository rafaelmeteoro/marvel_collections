package br.com.rafael.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.rafael.data.entities.CharacterData

@Database(entities = arrayOf(CharacterData::class), version = 1)
abstract class CharactersDataBase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
}