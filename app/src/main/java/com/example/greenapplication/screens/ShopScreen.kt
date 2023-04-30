package com.example.greenapplication.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ContentView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

private data class TestData(
    val text: String,
    val res: Int,
    val content: String
)


class ShopScreen : ComponentActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //ContentView()
        }
    }


    @Composable
    private fun ContentView() {
        ShopScreenExample()
    }


    @Composable
    private fun GridItem(testData: TestData, onItemClick: (testData: TestData) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .padding(4.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(
                        bounded = true,
                        color = MaterialTheme.colors.background
                    ),
                    onClick = {
                        onItemClick(testData)
                    }
                ),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 12.dp,
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(testData.res),
                    contentDescription = testData.content,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier.padding(top = 6.dp, start = 6.dp, end = 6.dp),
                    text = testData.text,
                    fontSize = 15.sp,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }



    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ShopScreenExample() {
        val list = createDataList()
        Column() {

            TopBarStrutture(SelectedIcon = 1)
            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp),
                content = {
                    items(list.size) { index ->

                        GridItem(testData = list[index], onItemClick = {
                            Toast.makeText(this@ShopScreen, it.text, Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            )
        }
    }

    private fun createDataList(): List<TestData> {

        val list = mutableListOf<TestData>()

        list.add(
            TestData(
                "Centrale a gas",
                res = com.example.greenapplication.R.drawable.centralegas,
                content = "gas"

            )
        )
        list.add(
            TestData(
                "Centrale a carbone",
                res = com.example.greenapplication.R.drawable.carbone,
                content = "carbone"
            )
        )
        list.add(
            TestData(
                "Bus",
                res = com.example.greenapplication.R.drawable.bus,
                content = "Bus"
            )
        )
        list.add(
            TestData(
                "Centrale a biometano",
                res = com.example.greenapplication.R.drawable.biometano,
                content = "biometano"
            )
        )
        list.add(
            TestData(
                "Parco",
                res = com.example.greenapplication.R.drawable.parco,
                content = "parco"
            )
        )


        return list
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewShopScreen() {
        GameSkeletonTheme {
            TopBarStrutture(SelectedIcon = 1)
            MapScreen(SelectedIcon = 1)
           ContentView()
        }
    }
}










