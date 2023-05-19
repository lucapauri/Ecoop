@file:OptIn(ExperimentalFoundationApi::class)

package com.example.greenapplication.screens

import android.accounts.AuthenticatorDescription
import android.net.wifi.aware.AwareResources
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
import it.polito.did.gameskeleton.screens.GenericScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


@Composable
fun BottomBar(SelectedIcon: Int) {
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
                selectedIndex.value = 0
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.ShoppingCart, "", modifier = Modifier.size(30.dp))
        },

            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Notifications,"",modifier = Modifier.size(30.dp))
        },
            selected = (selectedIndex.value == 2),
            onClick = {
                selectedIndex.value = 2
            })
    }
}

@Composable
fun MapScreen(modifier: Modifier = Modifier, SelectedIcon: Int) {

        Scaffold(

            bottomBar = {BottomBar(SelectedIcon) }

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
fun SingleCard(res: Int, content: String ) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable { },
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
fun CardDemo() {
  Column(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.Center
  ) {
      Spacer(modifier = Modifier.height(20.dp))
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(115.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
    }
      Spacer(modifier = Modifier.height(20.dp))
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(115.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
      }
      Spacer(modifier = Modifier.height(20.dp))
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(115.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
      }

  }
}

@Composable
fun GridScreen(team: String){
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
        IconBar(percentCO2 = 65, percentHealth = 70, energyValue = 25)
        Divider(modifier = Modifier.padding(15.dp), thickness = 1.dp, color = Color.LightGray)
        CardDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapScreen() {
    GameSkeletonTheme {

        MapScreen(SelectedIcon = 0)
        GridScreen(team = "Quartiere Rosso")

    }
}

