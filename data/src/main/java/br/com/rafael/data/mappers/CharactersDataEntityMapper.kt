package br.com.rafael.data.mappers

import br.com.rafael.data.entities.ResultData
import br.com.rafael.domain.common.Mapper
import br.com.rafael.domain.entities.CharacterEntity
import br.com.rafael.domain.entities.DataEntity
import br.com.rafael.domain.entities.ResultEntity
import br.com.rafael.domain.entities.ThumbnailEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersDataEntityMapper @Inject constructor() : Mapper<ResultData, ResultEntity>() {

    override fun mapFrom(from: ResultData): ResultEntity {
        val resultEntity = ResultEntity(
                code = from.code,
                status = from.status,
                copyright = from.copyright,
                attributionText = from.attributionText,
                attributionHTML = from.attributionHTML,
                etag = from.etag
        )

        from.data?.let { dataData ->
            val dataEntity = DataEntity(
                    offset = dataData.offset,
                    limit = dataData.limit,
                    total = dataData.total,
                    count = dataData.count
            )


            dataData.results?.let {
                val charactersEntities = it.map { characherEntitie ->

                    val thumbnailEntity = ThumbnailEntity(
                            characherEntitie.thumbnail.path,
                            characherEntitie.thumbnail.extension
                    )

                    return@map CharacterEntity(
                            id = characherEntitie.id,
                            description = characherEntitie.description,
                            name = characherEntitie.name,
                            modified = characherEntitie.modified,
                            thumbnail = thumbnailEntity
                    )
                }

                dataEntity.results = charactersEntities
            }

            resultEntity.data = dataEntity
        }

        return resultEntity
    }
}