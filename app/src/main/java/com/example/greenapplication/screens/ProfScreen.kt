package com.example.greenapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.greenapplication.pag1.Text
import it.polito.did.gameskeleton.screens.GenericScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


@Composable
fun ProfScreen(formatTime : (Long)->List<Int>, time : LiveData<Long>, cO2 : LiveData<Map<String, String>>,
               happiness : LiveData<Map<String, String>>, energy : LiveData<Map<String, String>>,
                turn : LiveData<String>, timerTurn : LiveData<Long>, action : LiveData<Int>, modifier: Modifier = Modifier){
    val timer = time.observeAsState()
    val ms = formatTime(timer.value ?: 0)
    val tt = timerTurn.observeAsState()
    val mst = formatTime(tt.value ?: 0)
    val isTurn = turn.observeAsState().value
    val actionPoints = action.observeAsState().value
    val CO2 = cO2.observeAsState().value
    val health = happiness.observeAsState().value
    val money = energy.observeAsState().value
    val teams = CO2?.keys?:listOf("team1", "team2", "team3", "team4")
    GenericScreen(title = "Dashboard")
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(Modifier.height(70.dp))
        androidx.compose.material.Text(
            text = String.format(
                "%02d",
                ms[0]
            ) + ":" + String.format("%02d", ms[1])
        )
        androidx.compose.material.Text(
            text = "Turno $isTurn"
        )
        androidx.compose.material.Text(
            text = "Punti azione: $actionPoints"
        )
        androidx.compose.material.Text(
            text = String.format(
                "%02d",
                mst[0]
            ) + ":" + String.format("%02d", mst[1])
        )
        teams.forEach{ t ->
            Row(modifier = Modifier.padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically)
            {
                androidx.compose.material.Text(text = t[4].toString(), style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.background, modifier = Modifier.padding(end = 18.dp))
                IconBar(percentCO2 = CO2?.get(t)?.toInt()?:0, percentHealth = health?.get(t)?.toInt()?:0,
                    energyValue = money?.get(t)?.toInt()?:0)
            }

            Divider(modifier = Modifier.padding(15.dp), thickness = 1.dp, color = Color.LightGray)
        }

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewProfScreen(){
    GameSkeletonTheme {
        //ProfScreen()
    }
}