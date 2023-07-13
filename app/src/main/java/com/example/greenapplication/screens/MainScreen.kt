package it.polito.did.gameskeleton.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greenapplication.screens.EndScreen
import it.polito.did.gameskeleton.GameViewModel
import it.polito.did.gameskeleton.ScreenName

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val vm: GameViewModel = viewModel()
    val players = vm.players.observeAsState()
    when (val screenName = vm.screenName.observeAsState().value) {
        is ScreenName.Splash -> SplashScreen(modifier)
        is ScreenName.Initial -> InitialScreen(
            vm::onCreateNewGame,
            vm::onJoinGame,
            modifier)
        is ScreenName.SetupMatch -> SetupMatchScreen(
            screenName.matchId,
            players,
            vm::onStartGame,
            modifier)
        is ScreenName.WaitingStart -> WaitScreen(modifier)
        is ScreenName.Dashboard -> DashboardScreen(vm::formatTime,vm.timer ,modifier)
        is ScreenName.Playing -> PlayerScreen(vm::formatTime,vm.timer, screenName.team,vm::onNextTurn, vm::addItem, vm::deleteItem, vm, modifier)
        is ScreenName.Error -> ErrorScreen(screenName.message, modifier)
        is ScreenName.End -> EndScreen(vm::winningTeam as String)
        null -> Box(modifier)
    }
}
