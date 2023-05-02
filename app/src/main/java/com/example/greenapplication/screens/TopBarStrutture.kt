package com.example.greenapplication.screens

import android.widget.Toast
import androidx.annotation.ContentView
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun TopBarStrutture(SelectedIcon: Int) {
    
    val contextForToast = LocalContext.current.applicationContext

    TopAppBar( modifier = Modifier
        .height(90.dp)
        .fillMaxWidth(),
        elevation = 5.dp,
        title = {
            Text(text = "")
        },
        backgroundColor = MaterialTheme.colors.surface,
        actions = {
            // search icon
            TopAppBarActionButton(
                imageVector = Icons.Outlined.Search,
                description = "Search"
            ) {
                Toast.makeText(contextForToast, "Search Click", Toast.LENGTH_SHORT)
                    .show()
            }

            // lock icon
            TopAppBarActionButton(
                imageVector = Icons.Outlined.Lock,
                description = "Lock"
            ) {
                Toast.makeText(contextForToast, "Lock Click", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    )
}

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Composable
fun TopBarShop(SelectedIcon: Int){
    val contextForToast = LocalContext.current.applicationContext

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.surface)
        .size(95.dp))
    {
        Row(horizontalArrangement = Arrangement.spacedBy(84.dp), modifier = Modifier.padding(14.dp)){
            val selectedIcon = remember { mutableStateOf(SelectedIcon) }

            Column(horizontalAlignment = Alignment.CenterHorizontally){
            Button(onClick = { Toast.makeText(contextForToast, "Clicked on Button", Toast.LENGTH_SHORT).show() },
                modifier = Modifier.shadow(8.dp),
                enabled= true) {
                    Icon(imageVector = Icons.Default.Star,"Livello 1", modifier = Modifier.size(30.dp),
                        tint = Color(0xff2C4BD7)
                    )
                }
                Text(text = "Livello 1", color = MaterialTheme.colors.onBackground)

            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { Toast.makeText(contextForToast, "Clicked on Button", Toast.LENGTH_SHORT).show() },
                modifier = Modifier.shadow(8.dp),
                enabled = (SelectedIcon > 1 )) {
                    Icon(imageVector = Icons.Default.Star,"Livello 1", modifier = Modifier.size(30.dp), tint = MaterialTheme.colors.background)
                }
                Text(text = "Livello 2", color = MaterialTheme.colors.onBackground)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { Toast.makeText(contextForToast, "Clicked on Button", Toast.LENGTH_SHORT).show() },
                modifier = Modifier.shadow(8.dp),
                enabled= (SelectedIcon > 2 )) {
                    Icon(imageVector = Icons.Default.Star,"Livello 1", modifier = Modifier.size(30.dp), tint = MaterialTheme.colors.background)
                }
                Text(text = "Livello 3", color = MaterialTheme.colors.onBackground)
            }
        }
    }
}


@Preview
@Composable
fun PreviewTopBar(){
    GameSkeletonTheme() {
        TopBarShop(SelectedIcon = 1)
        ContentView()
    }
}

