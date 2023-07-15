package com.example.greenapplication.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
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
import com.example.greenapplication.pag1.Text
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun EliminaStruttura(mossa : Mossa, items : Map<String, Infrastruttura>,
                     infrastrutture : List<Infrastruttura>,
                     setMossa : (Mossa) -> Unit,
                     addMove : (String, String, Int, Int) -> Unit,
                     navController: NavController
                     ){
    var text1 = ""
    var text2 = ""
    var text3 = ""
    when(mossa.type){
        "add" -> {
            text1 = "Costruire"
            text2 = "La tua proposta è costruire:"
            val i = infrastrutture.find { it.id == mossa.id }
            if (i != null) {
                text3 = i.nome
            }
        }
        "delete" -> {
            text1 = "Smantellare"
            text2 = "La tua proposta è smantellare:"
            val i = items[mossa.square.toString()]
            if (i != null) {
                text3 = i.nome
            }
        }
        "replace" -> {
            text1 = "Sostituire"
            text2 = "La tua proposta è smantellare e costruire rispettivamente:"
            val i1 = infrastrutture.find { it.id == mossa.id }
            if (i1 != null) {
                text3 = i1.nome + " \n"
            }
            val i = items[mossa.square.toString()]
            if (i != null) {
                text3 += i.nome
            }
        }
        "upgrade" -> {
            text1 = "Sbloccare"
            text2 = "La tua proposta è sbloccare il livello successivo"
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 470.dp, start = 15.dp, end = 15.dp)
            .clickable { },
        elevation = 10.dp
    ){
        Column(
            modifier = Modifier.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally
        ){
            androidx.compose.material.Text(
                text = text1,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            androidx.compose.material.Text(
                text = text2,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            androidx.compose.material.Text(
                text = text3,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )


            Row() {
                Button(onClick = {
                     setMossa(Mossa("","", "", 0, 0,0))
                     navController.navigate("main")
                },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    modifier = Modifier.padding(25.dp)) {
                    androidx.compose.material.Text(text = "", color = Color.White)
                    Icon(imageVector = Icons.Default.Close, "",
                        tint = Color.White)
                }
                Button(onClick = {
                    addMove(mossa.type, mossa.team, mossa.id, mossa.square)
                    setMossa(Mossa("", "", "", 0, 0,0))
                    navController.navigate("main")
                },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier.padding(25.dp)) {
                    androidx.compose.material.Text(text = "", color = Color.White)
                    Icon(imageVector = Icons.Default.Check, "",
                        tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun ConfermaMossa(surveyOn : Boolean, team : String, CO2 : Int, health : Int, energy : Int,
                  timer : List<Int>, items : Map<String, Infrastruttura>,
                  navController: NavController, mossa : Mossa, infrastrutture : List<Infrastruttura>,
                  setMossa : (Mossa)->Unit, addMove: (String, String, Int, Int) -> Unit
){
    if(surveyOn){
        navController.navigate("poll")
    }
    MapScreen(SelectedIcon = 0, home = false, shop = false, poll = false, navController = navController)
    GridScreen(team, CO2, health, energy, timer, items, navController)
    EliminaStruttura(mossa, items, infrastrutture, setMossa, addMove, navController)
}

@Preview
@Composable
fun PreviewEliminaStruttura(){
    GameSkeletonTheme {
        ConfermaMossa(false, "team1", 65, 70, 25, listOf(0,0),
        emptyMap(), rememberNavController(), Mossa("", "", "", 0, 0, 0),
            emptyList(), { _: Mossa -> }
        ) { _: String, _: String, _: Int, _: Int -> }
    }
}