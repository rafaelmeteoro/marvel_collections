package br.com.rafael.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.rafael.data.entities.CharacterData

@Database(entities = [CharacterData::class], version = 1, exportSchema = false)
abstract class CharactersDataBase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
}