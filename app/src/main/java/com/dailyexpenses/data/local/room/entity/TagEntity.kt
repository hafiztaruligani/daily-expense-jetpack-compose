package com.dailyexpenses.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dailyexpenses.domain.model.Tag

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 ,
    val name: String
) {
    constructor() : this(0, "")

    fun toModel() = Tag(id, name)

}
