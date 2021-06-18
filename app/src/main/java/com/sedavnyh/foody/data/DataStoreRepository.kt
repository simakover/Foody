package com.sedavnyh.foody.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.sedavnyh.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.sedavnyh.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.sedavnyh.foody.util.Constants.Companion.PREFERENCES_NAME
import com.sedavnyh.foody.util.Constants.Companion.PREFERENCE_BACK_ONLINE
import com.sedavnyh.foody.util.Constants.Companion.PREFERENCE_DIET_TYPE
import com.sedavnyh.foody.util.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.sedavnyh.foody.util.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.sedavnyh.foody.util.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


// Хранилище параметров
@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    // Ключи для выбранного типа рецепта
    private object PreferenceKeys {
        val selectedMealType = preferencesKey<String>(PREFERENCE_MEAL_TYPE)
        val selectedMealTypeId = preferencesKey<Int>(PREFERENCE_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(PREFERENCE_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(PREFERENCE_DIET_TYPE_ID)
        val backOnline = preferencesKey<Boolean>(PREFERENCE_BACK_ONLINE)
    }

    // Хранилище
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    // Функция сохранения настроек
    suspend fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        dataStore.edit { pereferences ->
            pereferences[PreferenceKeys.selectedMealType] = mealType
            pereferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            pereferences[PreferenceKeys.selectedDietType] = dietType
            pereferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    // Достать настройки из хранилища(или взять дефолтные) и отдать ввиде дата класса
    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    //Запись статуса онлайн в хранилище
    suspend fun saveBackOnline(backOnline: Boolean){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)