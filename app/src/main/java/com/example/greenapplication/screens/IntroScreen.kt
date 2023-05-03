package com.example.greenapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greenapplication.R
import com.example.greenapplication.pag1.*
import com.example.greenapplication.ui.theme.GreenApplicationTheme
import com.google.relay.compose.BoxScopeInstanceImpl.align
import it.polito.did.gameskeleton.screens.GenericScreen
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme


@Composable
fun IntroScreen(  modifier: Modifier = Modifier)
{
    GenericScreen(title = "")
    {
        Column(
            modifier.fillMaxWidth()) {
            Spacer(Modifier.weight(1f))
            Image (
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.align(Alignment.CenterHorizontally).size(300.dp).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
            )
            Spacer(Modifier.height(32.dp))
            androidx.compose.material.Text(
                text = "Rendi il tuo",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.surface,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(horizontal = 50.dp)
            )
            Spacer(Modifier.height(32.dp))
            androidx.compose.material.Text(
                text = "QUARTIERE\nun posto migliore",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.surface,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(horizontal = 50.dp)
            )
            Spacer(Modifier.height(22.dp))
            androidx.compose.material.Text(
                text = "Impegnati con la tua squadra a diminuire le emissioni di CO2 e aumentare il benessere della comunità!",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.surface,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(horizontal = 50.dp)
            )

            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {}, shape = RoundedCornerShape(20.dp)
            ) {
                androidx.compose.material.Text("Inizia",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.padding(horizontal = 80.dp))
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntroScreen() {
    GameSkeletonTheme() {
        IntroScreen()
    }

}
