package com.blihm.balihometest.ui.repositories

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ForkLeft
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.blihm.balihometest.data.local.model.RepositoryEntity
import com.blihm.balihometest.ui.theme.BalihomeTestTheme

@Composable
fun UserRepositoriesScreen(
    viewModel: UserRepositoriesViewModel
) {
    val repositories = viewModel.repositoriesFlow.collectAsLazyPagingItems()

    val context = LocalContext.current
    LaunchedEffect(key1 = repositories.loadState) {
        if (repositories.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (repositories.loadState.refresh as LoadState.Error).error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (repositories.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            RepositoryScreenWithList(repositories = repositories)
        }
    }
}

@Composable
fun RepositoryScreenWithList(
    repositories: LazyPagingItems<RepositoryEntity>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = repositories.loadState) {
        if (repositories.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (repositories.loadState.refresh as LoadState.Error).error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = repositories.itemCount,
            key = repositories.itemKey { it.repId },
            contentType = repositories.itemContentType { "UserPagingItems" }
        ) { index ->
            val user: RepositoryEntity? = repositories[index]
            user?.let {
                RepositoryItem(repository = it)
            }
        }
        item {
            if (repositories.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun RepositoryItem(
    repository: RepositoryEntity, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFEF7FF)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = repository.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = repository.fullName,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = repository.description ?: "No description, website, or topics provided.",
                fontStyle = FontStyle.Italic
            )

            RepoStatRow(repository, Icons.Outlined.StarBorder, "stars")

            RepoStatRow(repository, Icons.Outlined.RemoveRedEye, "watching")

            RepoStatRow(repository, Icons.Outlined.ForkLeft, "forks")

        }
    }
}

@Composable
private fun RepoStatRow(repository: RepositoryEntity, painter: ImageVector, text: String) {
    Row(
        modifier = Modifier.padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = painter,
            contentDescription = painter.name,
            modifier = Modifier.size(18.dp),
            tint = Color.Gray
        )
        Text(
            text = "${repository.stargazersCount} $text",
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RepositoryItemPreview() {
    BalihomeTestTheme {
        RepositoryItem(
            repository = RepositoryEntity().copy(
                name = "30daysoflaptops.github.io",
                fullName = "mojombo/30daysoflaptops.github.io",
                description = null,
                forksCount = 4,
                watchersCount = 7,
                stargazersCount = 3
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UserRepositoriesPreview() {

}

