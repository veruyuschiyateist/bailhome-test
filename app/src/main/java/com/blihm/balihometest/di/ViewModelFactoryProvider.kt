package com.blihm.balihometest.di

import com.blihm.balihometest.ui.repositories.UserRepositoriesViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun repositoriesViewModelFactory(): UserRepositoriesViewModel.Factory
}