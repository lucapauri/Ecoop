package com.example.greenapplication.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.request.ImageRequest
import com.example.greenapplication.Infrastruttura
import com.example.greenapplication.Mossa
import it.polito.did.gameskeleton.screens.GenericScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


class ShopScreen : ComponentActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //ContentView()
        }
    }


    @Composable
    private fun ContentView(Lista: List<Infrastruttura>, navController: NavController) {
        ShopScreenExample(Lista, navController)
    }


    @Composable
    private fun GridItem(testData: Infrastruttura, navController: NavController) {
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
                        navController.navigate("detailCard?cardId=${testData.id}&squareId=${0}")
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
                    painter = painterResource(testData.imageId),
                    contentDescription = testData.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier.padding(top = 6.dp, start = 6.dp, end = 6.dp),
                    text = testData.nome,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }



    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ShopScreenExample( Lista: List<Infrastruttura>, navController: NavController) {


        Column() {

            //Spacer(modifier = Modifier.height(35.dp))

            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                contentPadding = PaddingValues(start = 5.dp, end = 5.dp, top = 22.dp, bottom = 90.dp),
                modifier = Modifier.background(MaterialTheme.colors.surface),
                content = {
                    items(Lista.size) { index ->

                        GridItem(testData = Lista[index], navController)
                    }
                }
            )
        }
    }




    @Composable
    fun Shop(surveyOn : Boolean, navController: NavController, team : String, CO2 : Int, health : Int, energy : Int, level : Int,
             infrastrutture : List<Infrastruttura>, turn : String, setMossa : (Mossa) -> Unit,
             actionPoints : Int){
        val (view, setview) = remember{ mutableStateOf(1) }
        val lista = infrastrutture.filter { it.level == view }
        Scaffold(bottomBar = {BottomBar(
            SelectedIcon = 1, home = true, shop = true, poll = false, navController = navController
        )}) {

            ValueScreen(title = team, CO2, health, energy)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(150.dp))
                TopBarShop(SelectedIcon = level, setview, setMossa, navController, team, turn,
                    actionPoints, energy) // 1 -> 2 livelli bloccati,
                // 2 -> 1 livello bloccato,
                // 3 -> tutti i livello sbloccati
                ContentView(lista, navController) //permette di passare una
                // lista diversa a seconda del livello (blu, giallo, verde)
            }

        }
    }
    


    @Preview(showBackground = true)
    @Composable
    fun PreviewShopScreen() {
        GameSkeletonTheme {
            //Shop(false, rememberNavController(), "", 65, 70, 25, 3, emptyList(), "") { _: Mossa -> }
        }
    }
}











