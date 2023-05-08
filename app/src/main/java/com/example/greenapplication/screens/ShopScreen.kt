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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import it.polito.did.gameskeleton.screens.GenericScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

public data class TestData(
    val text: String,
    //val description: String,
   // val icon: ImageVector,
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
    private fun ContentView(Lista: List<TestData>) {
        ShopScreenExample(Lista)
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
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }



    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ShopScreenExample( Lista: List<TestData>) {


        val list = Lista

        Column() {

            //Spacer(modifier = Modifier.height(35.dp))

            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                contentPadding = PaddingValues(start = 5.dp, end = 5.dp, top = 22.dp, bottom = 90.dp),
                modifier = Modifier.background(MaterialTheme.colors.surface),
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

    public fun createDataList(): List<TestData> {

        val list = mutableListOf<TestData>()

        list.add(
            TestData(
                "Centrale a gas",
                //description = "50",
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

    private fun createDataListGiallo(): List<TestData> {

        val list = mutableListOf<TestData>()

        list.add(
            TestData(
                "Metro",
                res = com.example.greenapplication.R.drawable.metro,
                content = "Metro"

            )
        )
        list.add(
            TestData(
                "Area verde",
                res = com.example.greenapplication.R.drawable.areaverde,
                content = "Area verde"
            )
        )
        list.add(
            TestData(
                "Centrale Nucleare",
                res = com.example.greenapplication.R.drawable.nucleare,
                content = "Centrale Nucleare"
            )
        )
        list.add(
            TestData(
                "Centrale idroelettrica",
                res = com.example.greenapplication.R.drawable.idroelettrica,
                content = "Centrale idroelettrica"
            )
        )
        list.add(
            TestData(
                "Centrale geotermica",
                res = com.example.greenapplication.R.drawable.geotermica,
                content = "Centrale geotermica"
            )
        )


        return list
    }

    private fun createDataListVerde(): List<TestData> {

        val list = mutableListOf<TestData>()

        list.add(
            TestData(
                "Pista ciclabile",
                res = com.example.greenapplication.R.drawable.ciclabile,
                content = "ciclabile"

            )
        )
        list.add(
            TestData(
                "Orto cittadino",
                res = com.example.greenapplication.R.drawable.orto,
                content = "orto"
            )
        )
        list.add(
            TestData(
                "Colonnine auto elettriche",
                res = com.example.greenapplication.R.drawable.colonnine,
                content = "auto elettriche"
            )
        )
        list.add(
            TestData(
                "Centrale fotovoltaica",
                res = com.example.greenapplication.R.drawable.fotovoltaica,
                content = "fotovoltaica"
            )
        )
        list.add(
            TestData(
                "Centrale eolica",
                res = com.example.greenapplication.R.drawable.eolica,
                content = "Centrale eolica"
            )
        )


        return list
    }
    
    



    @Preview(showBackground = true)
    @Composable
    fun PreviewShopScreen() {
        GameSkeletonTheme {

            Scaffold(bottomBar = {BottomBar(SelectedIcon = 1)}) {

                GenericScreen(title = "Quartiere Rosso")
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(150.dp))
                    TopBarShop(SelectedIcon = 3) // 1 -> 2 livelli bloccati,
                                                        // 2 -> 1 livello bloccato,
                                                        // 3 -> tutti i livello sbloccati
                    ContentView(Lista = createDataListVerde()) //permette di passare una
                                            // lista diversa a seconda del livello (blu, giallo, verde)
                }

            }

        }
    }
}











