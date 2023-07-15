package com.example.greenapplication.screens

import android.accounts.AuthenticatorDescription
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.greenapplication.Infrastruttura
import com.example.greenapplication.Mossa
import com.example.greenapplication.R
import com.google.relay.compose.BoxScopeInstanceImpl.align
import it.polito.did.gameskeleton.screens.GenericScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import kotlinx.coroutines.processNextEventInCurrentThread


data class Struttura(val name: String, val energy: Int, val qualitylife: Int, val co2: Int,
                     val res: Int, val content: String, val description: String)
val strutture = listOf(
    Struttura("Centrale a Gas", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.centralegas, content = "gas",
    description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Centrale a Carbone", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.carbone, content = "carbone",
        description = "Una centrale a carbone è una centrale termoelettrica che brucia carbone per generare energia elettrica."),
    Struttura("Bus", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.bus, content = "bus",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Centrale a Biometano", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.biometano, content = "biometano",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Parco", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.parco, content = "parco",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),

    Struttura("Metro", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.metro, content = "metro",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Area Verde", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.areaverde, content = "areaVerde",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Centrale Nucleare", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.nucleare, content = "nucleare",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Centrale Idroelettrica", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.idroelettrica, content = "idroelettrica",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Centrale Geotermica", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.geotermica, content = "geotermica",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),

    Struttura("Pista ciclabile", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.ciclabile, content = "ciclabile",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Orto cittadino", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.orto, content = "orto",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Colonnine auto elettriche", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.colonnine, content = "colonnine",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Centrale Fotovoltaica", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.fotovoltaica, content = "fotovoltaica",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),
    Struttura("Centrale Eolica", 15, 2, 8,
        res = com.example.greenapplication.R.drawable.eolica, content = "eolica",
        description = "Una centrale a gas è una centrale termoelettrica che brucia gas naturale per generare energia elettrica."),


)
//.elementAt(0)



@Composable
fun DettaglioCarta(CardData: Infrastruttura, square : Int = 0,
                   navController: NavController, surveyOn : Boolean,
                        team : String, CO2 : Int, health : Int, energy: Int,
                            turn : String, setMossa : (Mossa) -> Unit) {
    var text = if(square != 0){
        "Smantella"
    }else{
        "Acquista"
    }
    Scaffold(bottomBar = {BottomBar(SelectedIcon = 1, home = true, shop = true, poll = false, navController = navController)},
    ) {
        ValueScreen(team, CO2, health, energy)



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp, start = 15.dp, end = 15.dp)
                .clickable { },
            elevation = 10.dp
        ) {
            IconButton(onClick = {
                if(square != 0){
                    navController.navigate("main")
                }else{
                    navController.navigate("shop")
                }
            }, modifier = Modifier.padding(start = 300.dp)) {
                Icon(
                    imageVector = Icons.Default.Close, "",
                    tint = Color.DarkGray
                )
            }

            Column(
                modifier = Modifier.padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painterResource(id = CardData.imageId),
                    contentDescription = CardData.nome,
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .size(200.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = CardData.nome,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp, bottom = 15.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        painter = painterResource(id = R.drawable.energy),
                        contentDescription = "energy",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 2.dp)
                    )
                    Text(
                        text = CardData.energy.toString(),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.subtitle2
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.life),
                        contentDescription = "energy",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            //.padding(start = 35.dp, end = 5.dp)
                            .size(33.dp)
                            .padding(end = 2.dp)
                    )
                    Text(
                        text = CardData.happiness.toString(),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.subtitle2
                    )
                    Spacer(modifier = Modifier.width(18.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.co2),
                        contentDescription = "energy",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            // .padding(start = 35.dp, end = 5.dp)
                            .size(35.dp)
                            .padding(end = 3.dp)
                    )
                    Text(
                        text = CardData.CO2.toString(),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.subtitle2
                    )

                }

                Text(
                    text = CardData.description,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(top = 15.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                      if(square != 0){
                          setMossa(Mossa("A","delete", team, CardData.id, square, 0))
                          navController.navigate("confirm")
                      }else{
                          setMossa(Mossa("A","", team, CardData.id, square, 0))
                          navController.navigate("select")
                      }
                    },
                    shape = RoundedCornerShape(20.dp),
                    enabled = team == turn
                ) {
                    androidx.compose.material.Text(
                        text,
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.padding(horizontal = 80.dp)
                    )
                }

            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewDettaglioCarta() {
    GameSkeletonTheme {

        DettaglioCarta(
            Infrastruttura(1,"Parco",-4,4,5, 10,1,
                com.example.greenapplication.R.drawable.parco, "Aooooo"), 1,
            navController = rememberNavController(), false, "team1", 65,
            70, 25, ""
        ) { _: Mossa -> } //Inserire l'indice dell'infrastruttura a seconda
                                                        // della carta che si vuole visualizzare
    }
}