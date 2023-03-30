package com.dailyexpenses.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dailyexpenses.data.local.room.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tagEntity: TagEntity)

    @Query("UPDATE tag SET name=:name WHERE id=:id")
    suspend fun updateTag(id: Int, name: String)

    @Query("DELETE FROM tag WHERE id=:id")
    suspend fun deleteTag(id: Int)

    @Query("SELECT * FROM tag")
    fun getTagList(): Flow<List<TagEntity?>>

    @Query("SELECT * FROM tag WHERE id=:id")
    fun getTag(id: Int): Flow<TagEntity?>
}
