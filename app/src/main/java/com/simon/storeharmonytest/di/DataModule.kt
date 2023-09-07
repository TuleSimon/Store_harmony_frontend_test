package com.simon.storeharmonytest.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.simon.storeharmonytest.data.database.ProductsDatabase
import com.simon.storeharmonytest.data.sources.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModules {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideProductsDatabase(@ApplicationContext context: Context): ProductsDatabase {
        return  Room.databaseBuilder(
            context,
            ProductsDatabase::class.java,
            "simon-databse"
        ) .fallbackToDestructiveMigration() .build()
    }

}