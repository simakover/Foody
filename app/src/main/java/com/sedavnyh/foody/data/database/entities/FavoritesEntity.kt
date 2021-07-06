package com.sedavnyh.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sedavnyh.foody.models.Result
import com.sedavnyh.foody.util.Constants.Companion.FAVORITE_RECIPES_TABLE

// Сущность для хранения избранных рецептов
@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)