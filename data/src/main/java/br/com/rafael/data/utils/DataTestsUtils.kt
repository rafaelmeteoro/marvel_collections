package br.com.rafael.data.utils

import br.com.rafael.data.entities.CharacterData

class DataTestsUtils {

    companion object {
        fun getMockedCharacterData(id: Int, name: String = "CharacterData"): CharacterData {
            return CharacterData(
                    id = id,
                    name = name,
                    description = "Description$id",
                    modified = "Modified$id",
                    path = "Path$id",
                    extension = "Extension$id")
        }

        fun generateCharacterDataList(size: Int): List<CharacterData> {
            return (1..size).map { getMockedCharacterData(it) }
        }
    }
}