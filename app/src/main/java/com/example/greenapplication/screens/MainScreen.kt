package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greenapplication.screens.EndScreen
import com.example.greenapplication.screens.ProfScreen
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.MainActivity
import it.polito.did.gameskeleton.ScreenName

@Composable
fun MainScreen(activity: MainActivity, modifier: Modifier = Modifier) {
    val vm: GameViewModel = viewModel()
    val players = vm.players.observeAsState()
    when (val screenName = vm.screenName.observeAsState().value) {
        is ScreenName.Splash -> SplashScreen(modifier)
        is ScreenName.Initial -> InitialScreen(
            vm::onCreateNewGame,
            vm::onJoinGame,
            activity,
            modifier)
        is ScreenName.SetupMatch -> SetupMatchScreen(
            screenName.matchId,
            players,
            vm::onStartGame,
            modifier)
        is ScreenName.WaitingStart -> WaitScreen(modifier)
        is ScreenName.Dashboard -> ProfScreen(vm::formatTime,vm.timer , vm.cO2, vm.happiness, vm.energy, vm.turn,
            vm.timerTurn, vm.actionPoints ,modifier)
        is ScreenName.Playing -> PlayerScreen(vm::formatTime,vm.timer, screenName.team,vm::onNextTurn,
            vm::addItem, vm::deleteItem, vm, modifier)
        is ScreenName.Error -> ErrorScreen(screenName.message, modifier)
        is ScreenName.End -> EndScreen(screenName.team)
        null -> Box(modifier)
    }
}
