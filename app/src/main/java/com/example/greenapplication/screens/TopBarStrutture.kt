package com.example.greenapplication.screens

import android.widget.Toast
import androidx.annotation.ContentView
import androidx.compose.foundation.background

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun TopBarStrutture(SelectedIcon: Int) {
    
    val contextForToast = LocalContext.current.applicationContext

    TopAppBar( modifier = Modifier
        .graphicsLayer {
            clip = true
            shape = RoundedCornerShape(bottomStart =  30.dp, bottomEnd = 30.dp)
            shadowElevation = 2.2f
        }
        .height(90.dp).fillMaxWidth(),
        elevation = 5.dp,
        title = {
            Text(text = "")
        },
        backgroundColor = MaterialTheme.colors.background,
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


@Preview
@Composable
fun PreviewTopBar(){
    GameSkeletonTheme() {
        TopBarStrutture(0)
        ContentView()
    }
}

