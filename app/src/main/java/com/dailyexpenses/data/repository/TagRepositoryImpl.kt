package com.dailyexpenses.data.repository

import com.dailyexpenses.data.local.room.TagDao
import com.dailyexpenses.data.local.room.entity.TagEntity
import com.dailyexpenses.domain.model.Tag
import com.dailyexpenses.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagRepositoryImpl(
    private val tagDao: TagDao
) : TagRepository {

    override suspend fun createTag(name: String) {
        tagDao.insertTag(TagEntity(name = name))
    }

    override suspend fun editTag(id: Int, name: String) {
        tagDao.updateTag(id, name)
    }

    override suspend fun deleteTag(id: Int) {
        tagDao.deleteTag(id)
    }

    override suspend fun getTagList(): Flow<List<Tag?>> =
        tagDao.getTagList().map { it.map { entity -> entity?.toModel() } }

    override suspend fun getTag(id: Int): Flow<Tag?> =
        tagDao.getTag(id).map { it?.toModel() }
}
