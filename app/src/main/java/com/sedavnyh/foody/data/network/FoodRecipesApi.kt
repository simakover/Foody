package com.sedavnyh.foody.data.network

import com.sedavnyh.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    // Запрос GET на поиск рецептов.
    // Принимет на вход список параметров в виде карты. Использует аннтоацию для complexSearch
    // Отдает распонс, который содержит объект со списоком из рецетов
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

}