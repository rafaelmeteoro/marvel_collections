package br.com.rafael.data.utils

import br.com.rafael.data.entities.CharacterData
import br.com.rafael.data.entities.ThumbnailData

class DataTestsUtils {

    companion object {
        fun getMockedCharacterData(id: Int, name: String = "CharacterData"): CharacterData {
            return CharacterData(
                    id = id,
                    name = name,
                    description = "Description$id",
                    modified = "Modified$id",
                    thumbnail = ThumbnailData(path = "Path$id", extension = "Extension$id")
            )
        }

        fun generateCharacterDataList(size: Int): List<CharacterData> {
            return (1..size).map { getMockedCharacterData(it) }
        }
    }
}