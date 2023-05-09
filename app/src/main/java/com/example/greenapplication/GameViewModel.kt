package it.polito.did.gameskeleton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class GameViewModel: ViewModel() {
    private val gameManager = GameManager(viewModelScope)

    fun onCreateNewGame() = gameManager.createNewGame()
    fun onJoinGame(matchId:String) = gameManager.joinGame(matchId)
    fun onStartGame() = gameManager.startGame()
    fun onNextTurn() = gameManager.nextTurn()
    fun addItem(team : String, id : Int, square : Int) = gameManager.addItem(team, id, square)
    fun deleteItem(team : String, square : Int) = gameManager.deleteItem(team, square)

    val players = gameManager.players
    val screenName = gameManager.screenName
    val matchId = gameManager.matchId
    val turn = gameManager.turn
    val itemsTeams = gameManager.itemsTeams
    val cO2 = gameManager.cO2
    val happiness = gameManager.happiness
    val energy = gameManager.energy

}