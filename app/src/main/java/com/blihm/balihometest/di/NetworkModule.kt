package com.blihm.balihometest.di

import com.blihm.balihometest.data.network.api.GithubApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_BASE_URL = "https://api.github.com/"
    private const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): GithubApi {
        return retrofit
            .create(GithubApi::class.java)
    }

    private fun createGson(): Gson {
        return GsonBuilder()
            .setDateFormat(API_DATE_FORMAT)
            .create()
    }

}