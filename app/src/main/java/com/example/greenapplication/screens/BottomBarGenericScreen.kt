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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

@Composable
fun BottomBar() {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(elevation = 10.dp, backgroundColor = MaterialTheme.colors.background) {

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
fun MapScreen(modifier: Modifier = Modifier) {

        Scaffold(

            bottomBar = {BottomBar() }

        )
        {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .fillMaxSize()
            )
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
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(120.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
    }
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(120.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
      }
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(120.dp),
          verticalAlignment = Alignment.Top,
          horizontalArrangement  =  Arrangement.SpaceBetween
      ){
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
          SingleCard(res = com.example.greenapplication.R.drawable.logo, content = "logo")
      }
      Row(modifier = Modifier
          .fillMaxWidth()
          .height(120.dp),
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
fun GridScreen(){
    Column(modifier = Modifier, verticalArrangement = Arrangement.Center
    ){
        Box( //Sostitire BOX con quello che manca
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .height(220.dp)
                .fillMaxWidth()
        )
        CardDemo()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapScreen() {
    GameSkeletonTheme {

        MapScreen()
        //CardDemo()
        GridScreen()
    }

}

