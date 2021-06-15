package com.sedavnyh.foody.data

import com.sedavnyh.foody.data.database.RecipesDao
import com.sedavnyh.foody.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


//По сути репозиторий для получения данных с базы данных с использованием инжектов
class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readDatabase(): Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

}