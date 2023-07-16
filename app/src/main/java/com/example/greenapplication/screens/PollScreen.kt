package com.example.greenapplication.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.greenapplication.Infrastruttura
import com.example.greenapplication.Mossa
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


val azioni = listOf("Aggiungere", "Smantellare")

@Composable
fun PollScreen(proposte : Map<String, String>, voted : Boolean, voteMove : (String) -> Unit){

    Log.d("GameManager", voted.toString())
    val (selectedProposte, setSelectedProposte) = remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 350.dp, start = 15.dp, end = 15.dp)
            .clickable { },
        elevation = 10.dp
    )

    {
        Column (Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp)) {
            Text(text = "Scegli l'opzione che preferisci: \n ", style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.background,)
            proposte.forEach { option ->
                if(option.value!=""){
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedProposte == option.key,
                            onClick = {
                                setSelectedProposte(option.key)
                                voteMove(option.key)
                            },
                            modifier = Modifier.padding(8.dp),
                            enabled = !voted
                        )
                        Text(text = option.value, style = MaterialTheme.typography.subtitle2,
                            color = MaterialTheme.colors.onBackground, )
                    }
                }
            }
        }

    }
}

@Composable
fun Poll(team : String, CO2 : Int, health : Int, energy : Int, timer : List<Int>, navController: NavController,
         surveyOn : Boolean, items : Map<String, Infrastruttura>, moves : Map<String, String>, voted : Boolean,
            voteMove : (String) -> Unit, turn : String, actionPoints : Int, mst : List<Int>){
    MapScreen(SelectedIcon = 2, home = true, shop = false, poll = true, navController = navController)
    GridScreen(team, CO2, health,energy, timer, items, navController, turn, actionPoints, mst)
    PollScreen(moves, voted, voteMove)
}

@Preview
@Composable
fun PreviewPollScreen(){
    GameSkeletonTheme {

    }
}