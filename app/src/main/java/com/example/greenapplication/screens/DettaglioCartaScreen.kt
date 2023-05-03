package com.example.greenapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greenapplication.R
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


data class Struttura(val name: String, val energy: Int, val res: Int, val content: String)
val students = listOf(
    Struttura("Centrale a Gas", 125,
        res = com.example.greenapplication.R.drawable.centralegas, content = "gas"),

)




@Composable
fun DettaglioCarta() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 140.dp, start = 15.dp, end = 15.dp)
            .clickable { },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(65.dp)
        ) {


            Text(
                buildAnnotatedString {
                    append("welcome to ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append("Jetpack Compose Playground")
                    }
                }
            )
            Text(
                buildAnnotatedString {
                    append("Now you are in the ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Card")
                    }
                    append(" section")
                }
            )
        }
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewShopScreen() {
    GameSkeletonTheme {
        MapScreen(SelectedIcon = 1)
        TopBarShop(SelectedIcon = 0)
        Spacer(Modifier.height(30.dp))
        DettaglioCarta()
    }
}