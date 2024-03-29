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
    fun formatTime(millis : Long) = gameManager.formatTime(millis)
    fun addMove(type : String, team : String, id : Int, square : Int) = gameManager.addMove(type, team, id, square)
    fun voteMove(key : String) = gameManager.voteMove(key)

    val players = gameManager.players
    val screenName = gameManager.screenName
    val matchId = gameManager.matchId
    val turn = gameManager.turn
    val itemsTeams = gameManager.itemsTeams
    val cO2 = gameManager.cO2
    val happiness = gameManager.happiness
    val energy = gameManager.energy
    val timer = gameManager.timer
    val hasVoted = gameManager.voted
    val moved = gameManager.moved
    val moves = gameManager.moves
    val surveyOn = gameManager.surveyOn
    val items = gameManager.items
    val winningTeam = gameManager.winningTeam
    val level = gameManager.itemsLevel
    val actionPoints = gameManager.actionPoints
    val timerTurn = gameManager.timerTurn

}