package com.example.p2_artspace_sergio_ivan

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p2_artspace_sergio_ivan.ui.theme.P2_ArtSpace_Sergio_IvanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            P2_ArtSpace_Sergio_IvanTheme {
                Surface {
                    ShowArtwork()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowArtwork()
{
    var index by remember{ mutableStateOf(0) }
    val artworkList: Array<Artwork> = arrayOf(
        Artwork(
            "Flashazo mañanero",
            R.drawable.perro1,
            "Mohamed P. Rro",
            1945),
        Artwork(
            "Hound Dog",
            R.drawable.perro2,
            "Elvis Perri",
            1966
        ),
        Artwork(
            "Oda al perro",
            R.drawable.perro3,
            "Dog Snoop",
            1998
        ),
        Artwork(
            "Perrástica",
            R.drawable.perro4,
            "Blondi",
            1939
        ),
        Artwork(
            "Autorretrato",
            R.drawable.perro5,
            "El Doggi",
            1999
        ),
        Artwork(
            "Baby Boss' Dog",
            R.drawable.perro6,
            "Baby Dog",
            2017
        )
    )
    val artwork = artworkList[index]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp, vertical = 25.dp)
    ) {
        Column {1
            val maxWidthModifier: Modifier = Modifier.fillMaxWidth()

            ArtworkTitle(
                artwork.title,
                maxWidthModifier)
            ArtworkImage(
                artwork.image,
                maxWidthModifier)
            ArtworkInfo(
                artwork.author,
                artwork.year,
                index + 1,
                artworkList.size,
                maxWidthModifier)
            ArtworkControls(
                onPreviousClick = { if (index - 1 < 0) index = artworkList.size - 1 else index-- },
                onNextClick = { if (index + 1 < artworkList.size) index++ else index = 0 },
                maxWidthModifier)
        }
    }
}

@Composable
fun ArtworkTitle(title: String, maxWidthModifier: Modifier)
{
    Row(modifier = maxWidthModifier) {
        Text(
            text = "$title",
            modifier = maxWidthModifier,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ArtworkImage(drawableImage: Int, maxWidthModifier: Modifier)
{
    Row(modifier = maxWidthModifier) {
        Image(
            painter = painterResource(id = drawableImage),
            contentDescription = null,
            modifier = maxWidthModifier
        )
    }
}

@Composable
fun ArtworkInfo(artistName: String, year: Int, imageIndex: Int, totalImages: Int, maxWidthModifier: Modifier)
{
    Row(modifier = maxWidthModifier) {
        Column(modifier = maxWidthModifier) {
            // artist info
            Row {
                Text(
                    text = "$artistName ($year)",
                    modifier = maxWidthModifier,
                    textAlign = TextAlign.Center
                )
            }
            // image index
            Row {
                Text(
                    text = "$imageIndex/$totalImages",
                    modifier = maxWidthModifier,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ArtworkControls(onPreviousClick: () -> Unit, onNextClick: () -> Unit, maxWidthModifier: Modifier)
{
    val buttonWidth: Modifier = Modifier.width(60.dp)

    Row(
        modifier = maxWidthModifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = onPreviousClick) {
            Text(
                text = "Previous",
                modifier = buttonWidth,
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = onNextClick ) {
            Text(
                text = "Next",
                modifier = buttonWidth,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class Artwork(
    val title: String,
    val image: Int,
    val author: String,
    val year: Int
)
