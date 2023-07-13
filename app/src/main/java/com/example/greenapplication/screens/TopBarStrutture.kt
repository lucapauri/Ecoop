package com.example.greenapplication.screens

import android.graphics.Color.GRAY
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.greenapplication.Mossa
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


@Composable
fun TopBarShop(SelectedIcon: Int, setView : (Int) -> Unit, setMossa : (Mossa)->Unit,
               navController: NavController, team : String, turn : String){
    val isTurn = team == turn

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.surface)
        .size(100.dp))
    {
        Row(horizontalArrangement = Arrangement.spacedBy(84.dp), modifier = Modifier.padding(bottom = 11.dp, top = 15.dp, start = 15.dp)){

            Column(horizontalAlignment = Alignment.CenterHorizontally){
            Button(onClick = { setView(1) },
                modifier = Modifier.shadow(8.dp),
                enabled= true,
            ) {
                    Icon(imageVector = Icons.Default.Star,"Livello 1", modifier = Modifier.size(30.dp),
                        tint = Color(0xff2C4BD7)
                    )
                }
                Spacer(Modifier.height(3.dp))
                Text(text = "Livello 1", color = MaterialTheme.colors.onBackground)

            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                if(SelectedIcon > 1){
                    setView(2)
                }else{
                    setMossa(Mossa("A", "upgrade", team, 0, 0, 0))
                    navController.navigate("confirm")
                }
                             },
                modifier = Modifier.shadow(8.dp),
                enabled = isTurn || SelectedIcon > 1
            ) {
                    Icon(imageVector =
                    if(SelectedIcon>1){
                        Icons.Default.Star
                    }else{
                         Icons.Default.Lock
                         }
                        ,"Livello 1",
                        modifier = Modifier.size(30.dp),
                        tint = Color(0xffe4b90b))
                }
                Spacer(Modifier.height(3.dp))
                Text(text = "Livello 2", color = MaterialTheme.colors.onBackground)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                if(SelectedIcon > 2){
                    setView(3)
                }else{
                    setMossa(Mossa("A", "upgrade", team, 0, 0, 0))
                    navController.navigate("confirm")
                }
            },
                modifier = Modifier.shadow(8.dp),
                enabled= (isTurn && SelectedIcon > 1) || SelectedIcon > 2
            ) {
                    Icon(imageVector =
                    if(SelectedIcon>2){
                        Icons.Default.Star
                    }else{
                        Icons.Default.Lock
                    }
                        ,"Livello 1", modifier = Modifier.size(30.dp),
                        tint = Color(0xff66ae27))
                }
                Spacer(Modifier.height(3.dp))
                Text(text = "Livello 3", color = MaterialTheme.colors.onBackground)
            }
        }
    }
}




@Preview
@Composable
fun PreviewTopBar(){
    GameSkeletonTheme() {
        TopBarShop(SelectedIcon = 3,{ _: Int -> },{_:Mossa->}, rememberNavController(), "", "")
        ContentView()
    }
}

