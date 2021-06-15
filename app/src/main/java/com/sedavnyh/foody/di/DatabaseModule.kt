package com.sedavnyh.foody.di

import android.content.Context
import androidx.room.Room
import com.sedavnyh.foody.data.database.RecipesDatabase
import com.sedavnyh.foody.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

// Модуль базы данных с инжектом
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    // получить экземпляр баззы данных
    @Singleton
    @Provides
    fun provideDatabase( @ApplicationContext context: Context ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    // получить экземпляр дао
    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}