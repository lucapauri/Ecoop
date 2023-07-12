package com.example.greenapplication.screens

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
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


val azioni = listOf("Aggiungere", "Smantellare")

@Composable
fun PollScreen(azioni: String, Name: String){


    val proposte = listOf("Proposta1", "Proposta2", "Proposta3", "Proposta4", "Proposta5" )
    val selectedProposte = remember { mutableStateOf("") }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 350.dp, start = 15.dp, end = 15.dp)
            .clickable { },
        elevation = 10.dp
    )

    {
        Column (Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp)) {
            Text(text = "Scegli l'opzione che preferisci: \n ${selectedProposte.value}", style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.background,)
            proposte.forEach { option ->
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedProposte.value == option,
                        onClick = { selectedProposte.value = option },
                        modifier = Modifier.padding(8.dp),
                    )
                    Text(text = option, style = MaterialTheme.typography.subtitle2,
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
fun Poll(team : String, CO2 : Int, health : Int, energy : Int, timer : List<Int>){
    MapScreen(SelectedIcon = 2)
    GridScreen(team = team, CO2, health,energy, timer)
    PollScreen(azioni.elementAt(0), strutture.elementAt(1).name.toString())
}

@Preview
@Composable
fun PreviewPollScreen(){
    GameSkeletonTheme {
        MapScreen(SelectedIcon = 2)
        GridScreen(team = "Quartiere Rosso", 65, 70,25, listOf(0,0))
        PollScreen(azioni.elementAt(0), strutture.elementAt(1).name.toString())
    }
}