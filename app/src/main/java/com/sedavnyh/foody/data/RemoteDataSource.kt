package com.sedavnyh.foody.data

import com.sedavnyh.foody.data.network.FoodRecipesApi
import com.sedavnyh.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject


//По сути репозиторий для получения данных с удаленного сервера м использованием инжектов и ретрофита
class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    //с помощью инжекта находим инстанс интерфейса FoodRecipesApi
    //запускаем запрос с прокидыванием мапы с параметрами
    // Получам инстанс дата класса FoodRecipe который хранит список рецептов
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(searchQuery)
    }

}