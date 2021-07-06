package com.sedavnyh.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sedavnyh.foody.models.FoodRecipe
import com.sedavnyh.foody.util.Constants.Companion.RECIPES_TABLE

// Сущность базы данных
// Хранилище для последнего кешированного запроса, перезаписывается каждый раз
@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}