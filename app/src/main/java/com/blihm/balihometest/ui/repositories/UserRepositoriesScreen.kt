package com.blihm.balihometest.ui.repositories

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ForkLeft
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blihm.balihometest.data.local.model.RepositoryEntity
import com.blihm.balihometest.di.ViewModelFactoryProvider
import com.blihm.balihometest.ui.theme.BalihomeTestTheme
import dagger.hilt.android.EntryPointAccessors



@Composable
fun UserRepositoriesScreen() {

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

            LazyColumn {
                items(items = createRepoStatList(repository)) {
                    Row(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = it.painter,
                            contentDescription = it.painter.name,
                            modifier = Modifier.size(18.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = "${it.count} ${it.text}",
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }
            }

        }
    }
}



data class RepoStat(
    val painter: ImageVector, val count: Int, val text: String
)

private fun createRepoStatList(repository: RepositoryEntity) = listOf(
    RepoStat(
        painter = Icons.Outlined.StarBorder, count = repository.stargazersCount, text = "stars"
    ), RepoStat(
        painter = Icons.Outlined.RemoveRedEye, count = repository.watchersCount, text = "watching"
    ), RepoStat(
        painter = Icons.Outlined.ForkLeft, count = repository.forksCount, text = "forks"
    )
)


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

