package com.example.myapplication.di

import com.example.myapplication.data.network.RetrofitBuilder
import com.example.myapplication.data.network.RetrofitServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return RetrofitBuilder.build()
    }

    @Provides
    @Singleton
    fun getServices(retrofit: Retrofit): RetrofitServices {
        return retrofit.create(RetrofitServices::class.java)
    }
}
