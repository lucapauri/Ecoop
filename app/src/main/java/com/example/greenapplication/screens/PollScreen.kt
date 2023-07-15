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
fun PollScreen(proposte : List<Mossa>, infrastrutture : List<Infrastruttura>, items: Map<String, Infrastruttura>){

    val (selectedProposte, setSelectedProposte) = remember { mutableStateOf("") }
    Log.d("GameManager", selectedProposte)

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
                var text = ""
                when(option.type){
                    "add" -> {
                        val i = infrastrutture.find { it.id == option.id }
                        if (i != null) {
                            text = "Costruire ${i.nome}"
                        }
                    }
                    "delete" -> {
                        val i = items[option.square.toString()]
                        if (i != null) {
                            text = "Smantellare ${i.nome}"
                        }
                    }
                    "replace" -> {
                        val i1 = infrastrutture.find { it.id == option.id }
                        val i = items[option.square.toString()]
                        if (i != null && i1 != null) {
                            text = "Smantellare $i e costruire $i1"
                        }
                    }
                    "upgrade" -> {
                        text = "Sbloccare il livello successivo"
                    }
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedProposte == option.key,
                        onClick = { setSelectedProposte(option.key) },
                        modifier = Modifier.padding(8.dp),
                    )
                    Text(text = text, style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onBackground, )
                }
            }
        }

    }

    /*Column(
        modifier = Modifier.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally
    ){
        androidx.compose.material.Text(
            text = "Sondaggio",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.background,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        androidx.compose.material.Text(
            text = "${azioni}:",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        androidx.compose.material.Text(
            text = "${Name}",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )

        Row() {
            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            modifier = Modifier.padding(25.dp)) {
                androidx.compose.material.Text(text = "", color = Color.White)
                Icon(imageVector = Icons.Default.Close, "",
                    tint = Color.White)
            }
            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier.padding(25.dp)) {
                androidx.compose.material.Text(text = "", color = Color.White)
                Icon(imageVector = Icons.Default.Check, "",
                    tint = Color.White)
            }
        } */
}

@Composable
fun Poll(team : String, CO2 : Int, health : Int, energy : Int, timer : List<Int>, navController: NavController,
         surveyOn : Boolean, items : Map<String, Infrastruttura>, moves : List<Mossa>,
         infrastrutture: List<Infrastruttura>){
    if(!surveyOn){
        navController.navigate("main")
    }
    MapScreen(SelectedIcon = 2, home = true, shop = false, poll = true, navController = navController)
    GridScreen(team = team, CO2, health,energy, timer, items, navController)
    PollScreen(moves, infrastrutture, items)
}

@Preview
@Composable
fun PreviewPollScreen(){
    GameSkeletonTheme {
        Poll(
            team = "",
            CO2 = 65,
            health = 70,
            energy = 25,
            timer = listOf(0,0),
            navController = rememberNavController(),
            surveyOn = true,
            items = emptyMap(),
            moves = emptyList(),
            infrastrutture = emptyList()
        )
    }
}