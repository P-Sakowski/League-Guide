package com.sakovsky.leagueguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sakovsky.leagueguide.repository.ChampionDetail
import com.sakovsky.leagueguide.ui.theme.LeagueGuideTheme

class DetailsActivity : ComponentActivity() {
    private val viewModel: DetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("ID")
        if (id != null) {
            viewModel.getData(id)
        }

        setContent {
            LeagueGuideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailsView(viewModel)
                }
            }
        }
    }
}

@Composable
fun DetailsView(viewModel: DetailsViewModel){
    val uiState by viewModel.immutableChampionDetail.observeAsState(UiState())

    Row(
        modifier = Modifier
            .background(Color.Black)
    )
    {
        when {
            uiState.isLoading -> { DetailLoadingView() }

            uiState.error != null -> { DetailErrorView(uiState.error.toString()) }

            uiState.values != null -> {
                LazyColumn {
                    items(uiState.values!!) { champion ->
                        ChampionDetailView(champion = champion)
                    }
                }
            }
        }
    }
}


@Composable
fun DetailErrorView(errorMessage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Error",
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}

@Composable
fun DetailLoadingView()  {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ChampionDetailView(champion: ChampionDetail, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black)
    )
    {
        AsyncImage(
            model = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + champion.id + "_0.jpg",
            contentDescription = champion.name,
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = champion.name,
                color = Color.LightGray,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(top = 15.dp)
            )
            Text(
                text = champion.title,
                color = Color.LightGray,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(top = 10.dp)
            )
            Text(
                text = champion.lore,
                color = Color.LightGray,
                fontStyle = FontStyle.Italic,
                fontSize = 10.sp,
                textAlign = TextAlign.Justify,
                modifier = modifier
                    .padding(
                        top = 15.dp,
                        start = 15.dp,
                        end = 15.dp
                    )
            )
            Text(
                text = "Resource Type: ${champion.partype}",
                color = Color.LightGray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 10.dp)
            )
            Text(
                text = "Tags: ${champion.tags.joinToString()}",
                color = Color.LightGray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 5.dp)
            )
            Text(
                text = "Ally Tips:",
                color = Color.Green,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 10.dp)
            )
            champion.allytips.forEach {
                Text(
                    text = "- $it",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(start = 10.dp)
                )
            }
            Text(
                text = "Enemy Tips:",
                color = Color.Red,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 10.dp)
            )
            champion.enemytips.forEach {
                Text(
                    text = "- $it",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(start = 10.dp)
                )
            }
        }
    }
}
