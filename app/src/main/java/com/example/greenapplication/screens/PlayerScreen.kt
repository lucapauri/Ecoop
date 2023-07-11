package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greenapplication.screens.mainPlayerScreen
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun PlayerScreen(formatTime : (Long)->List<Int>, time : LiveData<Long>, team: String,
                 nextTurn: ()-> Unit, addItem : (String, Int, Int)-> Unit,
                 deleteItem : (String, Int) -> Unit, vm: GameViewModel, modifier: Modifier = Modifier) {
    val timer = time.observeAsState()
    val c = vm.cO2.observeAsState()
    val CO2 = c.value?.get(team)?.toInt()?:0
    val h = vm.happiness.observeAsState()
    val happiness = h.value?.get(team)?.toInt()?:0
    val e = vm.energy.observeAsState()
    val energy = e.value?.get(team)?.toInt()?:0
    var ms = formatTime(timer.value ?: 0)
    if(ms.isEmpty()){
        ms = listOf(0,0)
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        mainPlayerScreen(team = team, CO2 = CO2, health = happiness, energy = energy, timer = ms)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayerScreen() {
    GameSkeletonTheme {
        PlayerScreen({ emptyList() }, MutableLiveData<Long>(),"Team A",{},{ _: String, _: Int, _: Int -> }, { _ : String, _: Int ->}, GameViewModel())
    }
}