package com.blihm.balihometest.di

import com.blihm.balihometest.data.PagingUserRepsRepository
import com.blihm.balihometest.data.UserRepsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsUserRepsRepository(userRepsRepository: PagingUserRepsRepository): UserRepsRepository
}