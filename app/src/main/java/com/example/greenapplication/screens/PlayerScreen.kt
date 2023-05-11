package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun PlayerScreen(formatTime : (Long)->List<Int>, time : LiveData<Long>, team: String, nextTurn: ()-> Unit, addItem : (String, Int, Int)-> Unit, deleteItem : (String, Int) -> Unit, modifier: Modifier = Modifier) {
    val timer = time.observeAsState()
    val ms = formatTime(timer.value ?: 0)
    GenericScreen(title = "Player(${team})", modifier) {

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = ms[0].toString() + ":" + ms[1].toString())
        Button(onClick = nextTurn)
        { Text("Switch turn") }
        Button(onClick = {addItem(team,1,1)}  )
        { Text(text = "Add Item") }
        Button(onClick = { deleteItem(team, 1) }) {
            Text(text = "Remove Item")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayerScreen() {
    GameSkeletonTheme {
        //PlayerScreen(Long,"Team A",{},{ _: String, _: Int, _: Int -> }, {_ : String, _: Int ->})
    }
}