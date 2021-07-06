package com.sedavnyh.foody.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sedavnyh.foody.models.FoodRecipe
import com.sedavnyh.foody.models.Result

// конвертация сущностей
class RecipesTypeConverter {

    var gson = Gson()


    //Конвертации сущности списка рецептов
    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

    //Конвертации сущности избранного рецепта
    @TypeConverter
    fun resultToString(result : Result): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val type = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, type)
    }

}