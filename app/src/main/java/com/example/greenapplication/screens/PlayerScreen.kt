package it.polito.did.gameskeleton.screens

import android.util.Log
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.greenapplication.Infrastruttura
import com.example.greenapplication.screens.DettaglioCarta
import com.example.greenapplication.screens.Poll
import com.example.greenapplication.screens.ShopScreen
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
    val survey = vm.surveyOn.observeAsState()
    val surveyOn = survey.value?:false
    val i = vm.itemsTeams.observeAsState()
    val infrastrutture = vm.items
    val items = i.value?.get(team)?.toSortedMap(compareBy{it.toInt()})?.
        mapValues { infrastrutture.find { inf -> inf.id == it.value.toInt() } }?: emptyMap<String,String>()
    if(ms.isEmpty()){
        ms = listOf(0,0)
    }
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "main") {
            composable("main"){ mainPlayerScreen(
                team = team,
                CO2 = CO2,
                health = happiness,
                energy = energy,
                timer = ms,
                navController,
                surveyOn,
                items as Map<String, Infrastruttura>
            )}
            composable("poll"){ Poll(
                team = team,
                CO2 = CO2,
                health = happiness,
                energy = energy,
                timer = ms,
                navController,
                surveyOn,
                items as Map<String, Infrastruttura>
            )}
            composable("detailCard?cardId={cardId}&squareId={squareId}",
                arguments = listOf(
                    navArgument("squareId") { defaultValue = 0 },
                    navArgument("cardId") {defaultValue = 0}
                )){backStackEntry ->
                val cardId = backStackEntry?.arguments?.getInt("cardId")?:1
                val squareId = backStackEntry?.arguments?.getInt("squareId")?:1
                val card = infrastrutture.find { it.id == cardId }
                if (card != null) {
                    DettaglioCarta(CardData = card, squareId, navController, surveyOn,
                    team, CO2, happiness, energy)
                }
            }
            composable("shop"){
                ShopScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayerScreen() {
    GameSkeletonTheme {
        PlayerScreen({ emptyList() }, MutableLiveData<Long>(),"Team A",{},{ _: String, _: Int, _: Int -> }, { _ : String, _: Int ->}, GameViewModel())
    }
}