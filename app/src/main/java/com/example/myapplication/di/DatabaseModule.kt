package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.network.RetrofitServices
import com.example.myapplication.data.repositories.AppRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDB(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    @Singleton
    @Provides
    fun provideRepository(
        database: AppDatabase,
        retrofitServices: RetrofitServices
    ): AppRepository {
        return AppRepository(database, retrofitServices)
    }
}
