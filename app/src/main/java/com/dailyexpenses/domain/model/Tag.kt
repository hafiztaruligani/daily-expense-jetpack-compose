package com.dailyexpenses.domain.model

import com.dailyexpenses.data.local.room.entity.TagEntity

data class Tag(
    val id: Int,
    val name: String
) {
    fun toEntity() = TagEntity(id, name)
}
