package it.polito.did.gameskeleton

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.greenapplication.Infrastruttura
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

class GameManager(private val scope:CoroutineScope) {
    private val URL = "https://greenapplication-bfb6f-default-rtdb.europe-west1.firebasedatabase.app/"
    private val firebase = Firebase.database(URL)
    private val firebaseAuth = Firebase.auth
    val teamNames = listOf("team1", "team2", "team3", "team4")
    val items : MutableList<Infrastruttura> = mutableListOf<Infrastruttura>();
    val initialItems = 3

    init {
        //firebase.setLogLevel(Logger.Level.DEBUG)
        scope.launch {
            try {
                firebaseAuth.signInAnonymously().await()
                Log.d("GameManager", "Current User: ${firebaseAuth.uid}")
                delay(500)
                mutableScreenName.value = ScreenName.Initial
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?:"Unknown error")
            }
        }
        items.add(Infrastruttura(1,"infrastruttura1",20,30,40,1,""))
        items.add(Infrastruttura(2,"infrastruttura2",20,30,40,1,""))
        items.add(Infrastruttura(3,"infrastruttura3",20,30,40,1,""))
        items.add(Infrastruttura(4,"infrastruttura4",20,30,40,1,""))
        items.add(Infrastruttura(5,"infrastruttura5",20,30,40,1,""))
        items.add(Infrastruttura(6,"infrastruttura6",20,30,40,1,""))
        items.add(Infrastruttura(7,"infrastruttura7",20,30,40,1,""))
        items.add(Infrastruttura(8,"infrastruttura8",20,30,40,1,""))
        items.add(Infrastruttura(9,"infrastruttura9",20,30,40,1,""))
        items.add(Infrastruttura(10,"infrastruttura10",20,30,40,1,""))
        items.add(Infrastruttura(11,"infrastruttura11",20,30,40,1,""))
        items.add(Infrastruttura(12,"infrastruttura12",20,30,40,1,""))
        items.add(Infrastruttura(13,"infrastruttura13",20,30,40,2,""))
        items.add(Infrastruttura(14,"infrastruttura14",20,30,40,2,""))
        items.add(Infrastruttura(25,"infrastruttura25",20,30,40,3,""))
        items.add(Infrastruttura(26,"infrastruttura26",20,30,40,3,""))
    }

    private val mutableScreenName = MutableLiveData<ScreenName>().also {
        it.value = ScreenName.Splash
    }
    val screenName: LiveData<ScreenName> = mutableScreenName

    private val mutableMatchId = MutableLiveData<String>()
    val matchId: LiveData<String> = mutableMatchId

    private val mutablePlayers = MutableLiveData<Map<String, String>>().also {
        it.value = emptyMap()
    }
    val players: LiveData<Map<String, String>> = mutablePlayers

    private val mutableTurn = MutableLiveData<String>().also {
        it.value = ""
    }
    val turn: LiveData<String> = mutableTurn



    private fun assignTeam(players: Map<String,String>): Map<String,String>? {
        val teams = players.keys.groupBy { players[it].toString() }
        val sizes = teamNames.map{ teams[it]?.size ?: 0 }
        val min: Int = sizes.stream().min(Integer::compare).get()
        var index = sizes.indexOf(min)
        val updatedPlayers = players.toMutableMap()
        var changed = false
        teams[""]?.forEach {
            updatedPlayers[it] = teamNames[index]
            index = (index +1) % teamNames.size
            changed = true
        }
        return if (changed) updatedPlayers else null
    }

    private fun watchPlayers() {
        val id = matchId.value ?: throw RuntimeException("Missing match Id")
        val ref = firebase.getReference(id)
        ref.child("players").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.value
                if (v!=null && v is Map<*, *>) {
                    val updatedPlayers = assignTeam(v as Map<String,String>)
                    if (updatedPlayers != null) {
                        ref.child("players").setValue(updatedPlayers)
                    } else {
                        mutablePlayers.value = v
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

    }

    private fun getMyTeam(): String {
        Log.d("GameManager", "players: ${players.value}")
        Log.d("GameManager", "uid: ${firebaseAuth.uid}")
        return players.value?.get(firebaseAuth.uid) ?: ""
    }

    fun createNewGame() {
        scope.launch {
            try {
                val ref = firebase.getReference("abc")
                //val ref = firebase.reference.push()
                Log.d("GameManager","Creating match ${ref.key}")
                ref.setValue(
                    mapOf(
                        "date" to LocalDateTime.now().toString(),
                        "owner" to firebaseAuth.uid,
                        "screen" to "WaitingStart"
                    )
                ).await()
                Log.d("GameManager", "Match creation succeeded")
                mutableMatchId.value = ref.key
                mutableScreenName.value = ScreenName.SetupMatch(ref.key!!)
                watchPlayers()
            } catch (e:Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
    }

    private fun watchScreen() {
        val id = matchId.value ?: throw RuntimeException("Missing match Id")
        val ref = firebase.getReference(id)
        ref.child("screen").addValueEventListener(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    mutableScreenName.value = getScreenName(snapshot.value?.toString()?: "")
                }

                override fun onCancelled(error: DatabaseError) {
                    mutableScreenName.value = ScreenName.Error(error.message)
                }
            }
        )

    }

    private fun getScreenName(name:String): ScreenName {
        return when (name) {
            "WaitingStart" -> ScreenName.WaitingStart
            "Playing" -> ScreenName.Playing(getMyTeam())
            else -> ScreenName.Error("Unknown screen $name")
        }
    }

    fun joinGame(matchId:String) {
        if (matchId.isEmpty()) return
        scope.launch {
            try {
                val ref = firebase.getReference(matchId)
                val data = ref.get().await()
                if (data!=null) {
                    mutableMatchId.value = matchId
                    ref
                        .child("players")
                        .child(firebaseAuth.uid!!)
                        .setValue("").await()
                    watchPlayers()
                    watchScreen()
                    ref.child("turn").addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val v = snapshot.value
                            if (v!=null && v is String) {
                                mutableTurn.value = v
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                } else {
                    mutableScreenName.value = ScreenName.Error("Invalid gameId")
                }
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    fun startGame() {
        scope.launch {
            try {
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                ref.child("turn").addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val v = snapshot.value
                        if (v!=null && v is String) {
                            mutableTurn.value = v
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                ref.child("turn").setValue(teamNames[0]).await()
                ref.child("screen").setValue("Playing").await()
                teamNames.forEach{
                    val team = ref.child("teams").child(it).child("items")
                    var i = 1;
                    val squares = generateRandom(initialItems, 1, 9)
                    generateRandom(initialItems, 1, 12).forEachIndexed{ index, it ->
                        team.child(it.toString()).setValue(squares[index])
                    }
                }
                mutableScreenName.value = ScreenName.Dashboard
                Log.d("GameManager", "Game started")
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
    }

    fun nextTurn(){
        scope.launch {
            try{
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                var v = turn.value
                if (v is String){
                    var index = teamNames.indexOf(v)
                    index = (index + 1) % teamNames.size
                    ref.child("turn").setValue(teamNames[index]).await()
                }
            }catch (e:Exception){

            }
        }
    }

    fun generateRandom(numbers : Int, min : Int, max:Int): List<Int>{
        val list : MutableList<Int> = mutableListOf<Int>()
        var i = 0;
        while (i < numbers){
            val rand = (min..max).random()
            if(list.find { it == rand } == null){
                list.add(rand)
                i++
            }
        }
        return list
    }

    fun sumParameter(parameter : String){
        /*TODO*/
    }

}