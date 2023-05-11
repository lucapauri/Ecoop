package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun DashboardScreen(formatTime : (Long)->List<Int>,time : LiveData<Long> ,modifier: Modifier = Modifier) {
    val timer = time.observeAsState()
    val ms = formatTime(timer.value ?: 0)
    GenericScreen(title = "Dashboard", modifier) {
        Column(modifier= Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxHeight()){
            Text(text = ms[0].toString() + ":" + ms[1].toString())
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDashboardScreen() {
    GameSkeletonTheme {
        //DashboardScreen()
    }
}