package com.example.greenapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenapplication.pag1.Text
import it.polito.did.gameskeleton.screens.GenericScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


@Composable
fun ProfScreen(){
    val team = listOf("1", "2", "3", "4")

    GenericScreen(title = "Punteggi squadre")
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(Modifier.height(70.dp))
        team.forEach{ t ->
            Row(modifier = Modifier.padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically)
            {
                androidx.compose.material.Text(text = t, style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.background, modifier = Modifier.padding(end = 18.dp))
                IconBar(percentCO2 = 65, percentHealth = 70, energyValue = 25)
            }

            Divider(modifier = Modifier.padding(15.dp), thickness = 1.dp, color = Color.LightGray)
        }

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewProfScreen(){
    GameSkeletonTheme {
        ProfScreen()
    }
}