package com.blihm.balihometest.ui.repositories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blihm.balihometest.data.UserRepsRepository
import com.blihm.balihometest.data.local.model.RepositoryEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow

class UserRepositoriesViewModel @AssistedInject constructor(
    userRepsRepository: UserRepsRepository,
    @Assisted val login: String
) : ViewModel() {

    val repositoriesFlow: Flow<PagingData<RepositoryEntity>> = userRepsRepository
        .getRepositories(login)
        .cachedIn(viewModelScope)

    @AssistedFactory
    interface Factory {
        fun create(login: String): UserRepositoriesViewModel
    }

    companion object {
        private const val TAG = "UserRepositoriesViewMod"

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            login: String
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(login) as T
                }
            }
        }
    }
}