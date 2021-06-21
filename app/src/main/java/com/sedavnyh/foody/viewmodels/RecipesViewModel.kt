package com.sedavnyh.foody.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sedavnyh.foody.data.DataStoreRepository
import com.sedavnyh.foody.util.Constants
import com.sedavnyh.foody.util.Constants.Companion.API_KEY
import com.sedavnyh.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.sedavnyh.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.sedavnyh.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.sedavnyh.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.sedavnyh.foody.util.Constants.Companion.QUERY_API_KEY
import com.sedavnyh.foody.util.Constants.Companion.QUERY_DIET
import com.sedavnyh.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.sedavnyh.foody.util.Constants.Companion.QUERY_NUMBER
import com.sedavnyh.foody.util.Constants.Companion.QUERY_SEARCH
import com.sedavnyh.foody.util.Constants.Companion.QUERY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


// вью модель для рецетов
class RecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    // хранилище с параметрами
    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    // Cохраняем в хранилище из вью модели
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    //!!!!!
    fun saveBackOnline(backOnline: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }


    // Параметры запроса
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        // Забираем параметры из хранилища, тк тип данных FLOW - можно забирать более одного
        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries

    }

    fun showNetworkStatus() {
        if (networkStatus == false) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if(networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "Back Online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}