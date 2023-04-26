package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun PlayerScreen(team: String, nextTurn: ()-> Unit , modifier: Modifier = Modifier) {
    GenericScreen(title = "Player(${team})", modifier) {

    }
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = nextTurn)
        { Text("Switch turn") }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayerScreen() {
    GameSkeletonTheme {
        PlayerScreen("Team A",{})
    }
}