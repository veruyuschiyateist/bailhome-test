package com.blihm.balihometest.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.blihm.balihometest.R
import com.blihm.balihometest.data.local.model.UserEntity

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSelect: (String) -> Unit
) {
    val users = viewModel.usersFlow.collectAsLazyPagingItems()

    val context = LocalContext.current
    LaunchedEffect(key1 = users.loadState) {
        if (users.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (users.loadState.refresh as LoadState.Error).error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (users.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            HomeScreenWithList(users = users) { login ->
                onSelect(login)
            }
        }
    }
}

@Composable
fun HomeScreenWithList(
    users: LazyPagingItems<UserEntity>,
    onSelect: (String) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = users.loadState) {
        if (users.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (users.loadState.refresh as LoadState.Error).error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (users.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = users.itemCount,
                    key = users.itemKey { it.userId },
                    contentType = users.itemContentType { "UserPagingItems" }
                ) { index ->
                    val user: UserEntity? = users[index]
                    user?.let {
                        UserItem(user = it) { login ->
                            onSelect(login)
                        }
                    }
                }
                item {
                    if (users.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun UserItem(
    user: UserEntity,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                Log.d("TAG", "UserItem: ${user.login}")
                onSelect(user.login)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFFEF7FF))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val image = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(user.avatarUrl)
                    .placeholder(R.drawable.user_placeholder)
                    .build()
            )

            Image(
                painter = image,
                contentDescription = user.login,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Text(
                text = user.login,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Divider(
            modifier = modifier.padding(start = 72.dp, top = 8.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
}
