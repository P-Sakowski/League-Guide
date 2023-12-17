package com.sakovsky.leagueguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sakovsky.leagueguide.repository.Champion
import com.sakovsky.leagueguide.ui.theme.LeagueGuideTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData()

        setContent {
            LeagueGuideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainView(viewModel: MainViewModel) {
    val uiState by viewModel.immutableChampions.observeAsState(UiState())

    Row(
        modifier = Modifier
            .background(Color.Black)
    )
    {
        when {
            uiState.isLoading -> { MyLoadingView() }

            uiState.error != null -> { MyErrorView(uiState.error.toString()) }

            uiState.values != null -> {
                LazyColumn {
                    items(uiState.values!!) { champion ->
                        ChampionView(champion = champion)
                    }
                }
            }
        }
    }
}

@Composable
fun MyErrorView(errorMessage: String) {
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
fun MyLoadingView()  {
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
fun ChampionView(champion: Champion, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .border(width = 5.dp, Color.Black)
    )
    {
        AsyncImage(
            model = "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/" + champion.id + "_0.jpg",
            contentDescription = champion.name,
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(start = 10.dp)
        ){
            Text(
                text = champion.name,
                color = Color.LightGray,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(horizontal = 15.dp)
            )
            Text(
                text = champion.title,
                color = Color.LightGray,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(horizontal = 15.dp)
            )
            Text(
                text = "Attack",
                color = Color.LightGray,
                fontSize = 13.sp,
                modifier = modifier
                    .padding(top = 10.dp)
                    .padding(start = 5.dp)
            )
            RatingBar(
                rating = champion.info.attack/2.0f,
                spaceBetween = 3.dp,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Text(
                text = "Defense",
                color = Color.LightGray,
                fontSize = 13.sp,
                modifier = modifier
                    .padding(start = 5.dp)
            )
            RatingBar(
                rating = champion.info.defense/2.0f,
                spaceBetween = 3.dp,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Text(
                text = "Magic",
                color = Color.LightGray,
                fontSize = 13.sp,
                modifier = modifier
                    .padding(start = 5.dp)
            )
            RatingBar(
                rating = champion.info.magic/2.0f,
                spaceBetween = 3.dp,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Text(
                text = "Difficulty",
                color = Color.LightGray,
                fontSize = 13.sp,
                modifier = modifier
                    .padding(start = 5.dp)
            )
            RatingBar(
                rating = champion.info.difficulty/2.0f,
                spaceBetween = 3.dp,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
    }
}

@Composable
private fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    spaceBetween: Dp = 0.dp
) {

    val image = ImageBitmap.imageResource(id = R.drawable.star)
    val imageFull = ImageBitmap.imageResource(id = R.drawable.star_full)

    val totalCount = 5

    val height = LocalDensity.current.run { image.height.toDp() }
    val width = LocalDensity.current.run { image.width.toDp() }
    val space = LocalDensity.current.run { spaceBetween.toPx() }
    val totalWidth = width * totalCount + spaceBetween * (totalCount - 1)


    Box(
        modifier
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRating(rating, image, imageFull, space)
            })
}

private fun DrawScope.drawRating(
    rating: Float,
    image: ImageBitmap,
    imageFull: ImageBitmap,
    space: Float
) {

    val totalCount = 5

    val imageWidth = image.width.toFloat()
    val imageHeight = size.height

    val reminder = rating - rating.toInt()
    val ratingInt = (rating - reminder).toInt()

    for (i in 0 until totalCount) {

        val start = imageWidth * i + space * i

        drawImage(
            image = image,
            topLeft = Offset(start, 0f)
        )
    }

    drawWithLayer {
        for (i in 0 until totalCount) {
            val start = imageWidth * i + space * i
            // Destination
            drawImage(
                image = imageFull,
                topLeft = Offset(start, 0f)
            )
        }

        val end = imageWidth * totalCount + space * (totalCount - 1)
        val start = rating * imageWidth + ratingInt * space
        val size = end - start

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(size, height = imageHeight),
            blendMode = BlendMode.SrcIn
        )
    }
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}