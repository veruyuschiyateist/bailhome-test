package com.blihm.balihometest.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blihm.balihometest.data.local.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    pager: Pager<Int, UserEntity>
) : ViewModel() {

    val usersFlow: Flow<PagingData<UserEntity>> = pager
        .flow
        .cachedIn(viewModelScope)
}