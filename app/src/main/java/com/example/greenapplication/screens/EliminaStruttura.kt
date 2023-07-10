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
import com.example.greenapplication.Infrastruttura
import com.example.greenapplication.pag1.Text
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun EliminaStruttura(infrastruttura: String){
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
                text = "Smantellare",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            androidx.compose.material.Text(
                text = "La tua proposta è smantellare infrastruttura:",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            androidx.compose.material.Text(
                text = "${infrastruttura}?",
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
            }
        }
    }
}


@Preview
@Composable
fun PreviewEliminaStruttura(){
    GameSkeletonTheme {
        MapScreen(SelectedIcon = 0)
        GridScreen(team = "Quartiere Rosso")
        EliminaStruttura("Centrale geotermica")
    }
}