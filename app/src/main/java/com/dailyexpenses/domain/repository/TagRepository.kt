package com.dailyexpenses.domain.repository

import com.dailyexpenses.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun createTag(name: String)
    suspend fun editTag(id: Int, name: String)
    suspend fun deleteTag(id: Int)
    suspend fun getTagList(): Flow<List<Tag?>>
    suspend fun getTag(id: Int): Flow<Tag?>
}
