package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greenapplication.R
import com.example.greenapplication.screens.IconBar
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun GenericScreen(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit = {}
)
{
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface)
                .fillMaxSize()){
            Image(
                painterResource(id = R.drawable.sfondo2),
                contentDescription = "Sfondo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radiusX = 5.dp, radiusY = 5.dp)
            )

        }
        Column(modifier = Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text ( title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xD9FFFFFF).compositeOver(Color.White))
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        shadowElevation = 2.2f
                    },
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.background,
                textAlign = TextAlign.Center)

            Spacer(Modifier.height(20.dp))

            IconBar(percentCO2 = 65, percentHealth = 70, energyValue = 25)
            Divider(modifier = Modifier.padding(15.dp), thickness = 1.dp, color = Color.LightGray)
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenericScreen() {
    GameSkeletonTheme {
        GenericScreen(title = "Generic Screen")
    }

}