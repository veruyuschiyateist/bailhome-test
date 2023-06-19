package com.blihm.balihometest.ui.repositories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class UserRepositoriesViewModel @AssistedInject constructor(
    @Assisted val login: String
) : ViewModel() {

    fun logLogin() {
        Log.d(TAG, "logLogin: $login")
    }

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