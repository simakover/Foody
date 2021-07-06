package com.sedavnyh.foody.data

import com.sedavnyh.foody.data.database.RecipesDao
import com.sedavnyh.foody.data.database.entities.FavoritesEntity
import com.sedavnyh.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


//По сути репозиторий для получения данных с базы данных с использованием инжектов
class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    // Функции для кешированого списка рецетов
    suspend fun insertCachedRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readCachedRecipes(): Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    // Функции для избранных рецептов
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity){
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>{
        return recipesDao.readFavoriteRecipes()
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

}