package com.sedavnyh.foody.util

//Константы проекта. Ключ и основной URL
class Constants {
    companion object{

        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "0e80f2f85b8b41d8be40e6febaba0634"

        // API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"
    }
}