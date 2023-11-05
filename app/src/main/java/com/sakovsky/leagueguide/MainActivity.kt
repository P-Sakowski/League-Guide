package com.sakovsky.leagueguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sakovsky.leagueguide.ui.theme.LeagueGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeagueGuideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .background(Color.Black)
    )
    {
        Image(
            painter = painterResource(id = R.drawable.aatrox_0),
            contentDescription = "Aatrox"
        )
        Column{
            Text(
                text = name,
                color = Color.LightGray,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(horizontal = 15.dp)
            )
            Text(
                text = "the Darkin Blade",
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
                rating = 4.0f,
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
                rating = 2.0f,
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
                rating = 1.5f,
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
                rating = 2.0f,
                spaceBetween = 3.dp,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LeagueGuideTheme {
        Greeting("Aatrox")
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