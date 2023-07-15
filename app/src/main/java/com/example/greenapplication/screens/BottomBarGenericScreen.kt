@file:OptIn(ExperimentalFoundationApi::class)

package com.example.greenapplication.screens

import android.accounts.AuthenticatorDescription
import android.net.wifi.aware.AwareResources
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.greenapplication.Infrastruttura
import com.example.greenapplication.Mossa
import it.polito.did.gameskeleton.screens.GenericScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


@Composable
fun BottomBar(SelectedIcon: Int, home : Boolean, shop : Boolean, poll : Boolean, navController: NavController) {
    val selectedIndex = remember { mutableStateOf(SelectedIcon) }
    BottomNavigation(backgroundColor = Color(0xD9FFFFFF).compositeOver(Color.White),
        modifier = Modifier
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                shadowElevation = 2.2f
            }
            .height(90.dp)
            .fillMaxWidth(),
        elevation = 5.dp) {

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Home,"", modifier = Modifier.size(30.dp))
        },

            selected = (selectedIndex.value == 0),
            onClick = {
                if(home){
                    navController.navigate("main")
                    selectedIndex.value = 0
                }
            },
            enabled = home
        )

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.ShoppingCart, "", modifier = Modifier.size(30.dp))
        },

            selected = (selectedIndex.value == 1),
            onClick = {
                if(shop){
                    navController.navigate("shop")
                    selectedIndex.value = 1
                }
            },
            enabled = shop
        )

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Notifications,"",modifier = Modifier.size(30.dp))
        },
            selected = (selectedIndex.value == 2),
            onClick = {
                if(poll){
                    navController.navigate("poll")
                    selectedIndex.value = 2
                }
            },
            enabled = poll
        )
    }
}

@Composable
fun MapScreen(modifier: Modifier = Modifier, SelectedIcon: Int, home : Boolean, shop : Boolean, poll : Boolean,
              navController: NavController) {

        Scaffold(

            bottomBar = {BottomBar(SelectedIcon, home, shop, poll, navController) }

        )
        {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxSize()){
                Image(
                    painterResource(id =com.example.greenapplication.R.drawable.sfondo2),
                    contentDescription = "Sfondo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(radiusX = 5.dp, radiusY = 5.dp)
                    )

            }

        }

}

@Composable
fun SingleCard(res: Int, content: String, square : Int, navController: NavController, id : Int, select: Boolean,
               mossa: Mossa, setMossa: (Mossa) -> Unit) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable(
                onClick = {
                    if (!select) {
                        if (id != 0) {
                            navController.navigate("detailCard?cardId=${id}&squareId=${square}")
                        }
                    } else {
                        if (id == 0) {
                            setMossa(Mossa("A", "add", mossa.team, mossa.id, square, 0))
                        } else {
                            setMossa(Mossa("A", "replace", mossa.team, mossa.id, square, 0))
                        }
                        navController.navigate("confirm")
                    }
                }
            ),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Image(
                painter = painterResource(res),
                contentDescription = content
            )
        }
    }
}
@Composable
fun CardDemo(items: Map<String, Infrastruttura>, navController: NavController, select: Boolean,
             mossa: Mossa, setMossa: (Mossa) -> Unit) {
  Column(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.Center
  ) {
      Spacer(modifier = Modifier.height(20.dp))
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(115.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = items?.get("1")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 1, navController, items?.get("1")?.id?:0, select, mossa, setMossa)
          SingleCard(res = items?.get("2")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 2, navController, items?.get("2")?.id?:0, select, mossa, setMossa)
          SingleCard(res = items?.get("3")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 3, navController, items?.get("3")?.id?:0, select, mossa, setMossa)
    }
      Spacer(modifier = Modifier.height(20.dp))
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(115.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = items?.get("4")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 4, navController, items?.get("4")?.id?:0, select, mossa, setMossa)
          SingleCard(res = items?.get("5")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 5, navController, items?.get("5")?.id?:0, select, mossa, setMossa)
          SingleCard(res = items?.get("6")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 6, navController, items?.get("6")?.id?:0, select, mossa, setMossa)
      }
      Spacer(modifier = Modifier.height(20.dp))
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(115.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = items?.get("7")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 7, navController, items?.get("7")?.id?:0, select, mossa, setMossa)
          SingleCard(res = items?.get("8")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 8, navController, items?.get("8")?.id?:0, select, mossa, setMossa)
          SingleCard(res = items?.get("9")?.imageId?: com.example.greenapplication.R.drawable.logo,
              content = "logo", 9, navController, items?.get("9")?.id?:0, select, mossa, setMossa)
      }

  }
}

@Composable
fun GridScreen(team: String, CO2 : Int, health : Int, energy : Int, ms : List<Int>,
               items: Map<String, Infrastruttura>, navController: NavController, turn : String,
               actionPoints : Int, mst : List<Int>, select : Boolean = false ,
               mossa: Mossa = Mossa("","","",0,0,0), setMossa: (Mossa) -> Unit = {}){
    Column(modifier = Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text ( team,
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
        IconBar(percentCO2 = CO2, percentHealth = health, energyValue = energy)
        Divider(modifier = Modifier.padding(15.dp), thickness = 1.dp, color = Color.LightGray)
        Text(text = String.format("%02d", ms[0]) + ":" + String.format("%02d", ms[1]))
        Row() {
            Text(text = "Turno $turn  ")
            Text(text = "Punti azione: $actionPoints  ")
            Text(text = String.format("%02d", mst[0]) + ":" + String.format("%02d", mst[1]))
        }
        CardDemo(items, navController, select, mossa, setMossa)
    }
}

@Composable
fun mainPlayerScreen(team : String, CO2 : Int, health : Int, energy : Int, timer : List<Int>,
                     navController: NavController, surveyOn : Boolean, items : Map<String, Infrastruttura>, turn : String,
                     actionPoints : Int, mst : List<Int>){
    MapScreen(SelectedIcon = 0, home = true, shop = true, poll = false, navController = navController)
    GridScreen(team, CO2, health, energy, timer, items, navController, turn, actionPoints, mst)
}

@Composable
fun selectCell(team : String, CO2 : Int, health : Int, energy : Int, timer : List<Int>,
               navController: NavController, surveyOn : Boolean, items : Map<String, Infrastruttura>,
               mossa: Mossa, setMossa : (Mossa) -> Unit,turn : String,
               actionPoints : Int, mst : List<Int>){
    MapScreen(SelectedIcon = 1, home = true, shop = true, poll = false, navController = navController)
    GridScreen(team, CO2, health, energy, timer, items, navController, turn, actionPoints, mst, true, mossa, setMossa)
}

@Preview(showBackground = true)
@Composable
fun PreviewMapScreen() {
    GameSkeletonTheme {
        //mainPlayerScreen(team = "Team 1", 65, 70 , 25, listOf(0,0),
            //navController = rememberNavController(), false, emptyMap()
        //)
    }
}

