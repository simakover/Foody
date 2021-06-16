package com.sedavnyh.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sedavnyh.foody.util.Constants
import com.sedavnyh.foody.util.Constants.Companion.API_KEY
import com.sedavnyh.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.sedavnyh.foody.util.Constants.Companion.QUERY_API_KEY
import com.sedavnyh.foody.util.Constants.Companion.QUERY_DIET
import com.sedavnyh.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.sedavnyh.foody.util.Constants.Companion.QUERY_NUMBER
import com.sedavnyh.foody.util.Constants.Companion.QUERY_TYPE


// вью модель для рецетов
class RecipesViewModel(application: Application): AndroidViewModel(application) {
    // Параметры запроса
    fun applyQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "main course"
        queries[QUERY_DIET] = "gluten free"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}