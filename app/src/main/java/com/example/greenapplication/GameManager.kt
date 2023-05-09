package it.polito.did.gameskeleton

import android.util.Log
import androidx.compose.animation.core.snap
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
    private val teamNames = listOf("team1", "team2", "team3", "team4")
    private val items : MutableList<Infrastruttura> = mutableListOf<Infrastruttura>();
    private val initialItems = 2
    private val initialEnergy = 50

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
        items.add(Infrastruttura(1,"Parco",-4,4,5, 10,1,""))
        items.add(Infrastruttura(2,"Bus",5,3,10,10,1,""))
        items.add(Infrastruttura(3,"Centrale a carbone",15,1,30,10,1,""))
        items.add(Infrastruttura(4,"Centrale a gas",13,2,28,10, 1,""))
        items.add(Infrastruttura(5,"Impianto a biometano",12,3,25,10,1,""))
        items.add(Infrastruttura(6,"Termovalorizzatore",4,-3,30,10, 1,""))
        items.add(Infrastruttura(7,"Metropolitana",3,6,12,10,2,""))
        items.add(Infrastruttura(8,"Area Verde",-8,8,8,10,2,""))
        items.add(Infrastruttura(9,"Centrale idroelettrica",10,4,40,10,2,""))
        items.add(Infrastruttura(10,"Centrale Geotermica",10,7,40,10,2,""))
        items.add(Infrastruttura(11,"Centrale nucleare",5,2,40,10,2,""))
        items.add(Infrastruttura(12,"Colonnine per auto elettriche",3,7,40,10,3,""))
        items.add(Infrastruttura(13,"Orto Cittadino",-12,10,10,10,3,""))
        items.add(Infrastruttura(14,"Pista ciclabile",0,8,8,10,3,""))
        items.add(Infrastruttura(15,"Centrale eolica",5,2,3,10,3,""))
        items.add(Infrastruttura(16,"Centrale fotovoltaica",4,30,40,10,3,""))
    }

    //Current screen name
    private val mutableScreenName = MutableLiveData<ScreenName>().also {
        it.value = ScreenName.Splash
    }
    val screenName: LiveData<ScreenName> = mutableScreenName

    //Current match id
    private val mutableMatchId = MutableLiveData<String>()
    val matchId: LiveData<String> = mutableMatchId

    //Current players
    private val mutablePlayers = MutableLiveData<Map<String, String>>().also {
        it.value = emptyMap()
    }
    val players: LiveData<Map<String, String>> = mutablePlayers

    //Current team turn
    private val mutableTurn = MutableLiveData<String>().also {
        it.value = ""
    }
    val turn: LiveData<String> = mutableTurn

    //Current items assigned
    private val mutableItemsTeams = MutableLiveData<Map<String, Map<String,String>>>().also {
        var map : MutableMap<String,Map<String,String>> = mutableMapOf<String,Map<String,String>>()
        teamNames.forEach{ team ->
            map[team] = emptyMap()
        }
        it.value = map
    }
    val itemsTeams : LiveData<Map<String, Map<String,String>>> = mutableItemsTeams

    //Current CO2
    private val mutableCO2 = MutableLiveData<Map<String,String>>().also {
        var map : MutableMap<String,String> = mutableMapOf<String,String>()
        teamNames.forEach{ team ->
            map[team] = "0"
        }
        it.value = map
    }
    val cO2 : LiveData<Map<String,String>> = mutableCO2

    //Current Happiness
    private val mutableHappiness = MutableLiveData<Map<String,String>>().also {
        var map : MutableMap<String,String> = mutableMapOf<String,String>()
        teamNames.forEach{ team ->
            map[team] = "0"
        }
        it.value = map
    }
    val happiness : LiveData<Map<String,String>> = mutableHappiness

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
                    //Listen variable turn
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
                    teamNames.forEach{
                        val team = ref.child("teams").child(it).child("items")
                        //Listen items change
                        team.addValueEventListener(object:ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                fetchItems(snapshot,it)
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                        //Listen CO2 change
                        ref.child("teams").child(it).child("CO2").addValueEventListener(object:ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                fetchCO2(snapshot, it)
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                        //Listen happiness change
                        ref.child("teams").child(it).child("Happiness").addValueEventListener(object:ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                fetchHappiness(snapshot, it)
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                    }
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
                    //Listen items change
                    team.addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val v = snapshot.value
                            fetchItems(snapshot,it)
                            ref.child("teams").child(it).child("CO2").setValue(sumCO2(it))
                            ref.child("teams").child(it).child("Happiness").setValue(sumHappiness(it))
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //Listen CO2 change
                    ref.child("teams").child(it).child("CO2").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            fetchCO2(snapshot, it)
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //Listen happiness change
                    ref.child("teams").child(it).child("Happiness").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            fetchHappiness(snapshot, it)
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //random items assignment
                    val squares = generateRandom(initialItems, 1, 9)
                    generateRandom(initialItems, 1, 6).forEachIndexed{ index, it ->
                        team.child(squares[index].toString()).setValue(it.toString())
                    }
                }
                mutableScreenName.value = ScreenName.Dashboard
                Log.d("GameManager", "Game started")
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
    }

    fun fetchCO2(snapshot: DataSnapshot, teamName: String){
        val v = snapshot?.value ?: "0"
        Log.d("GameManager", snapshot.toString())
        if(v is String){
            var map = mutableMapOf<String,String>()
            mutableCO2.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = v
                }else
                    map[item.key] = item.value
            }
            mutableCO2.value = map
            Log.d("GameManager", map.toString())
        }
    }

    fun fetchHappiness(snapshot: DataSnapshot, teamName: String){
        val v = snapshot?.value ?: "0"
        if(v is String){
            var map = mutableMapOf<String,String>()
            mutableHappiness.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = v
                }else
                    map[item.key] = item.value
            }
            mutableHappiness.value = map
        }
    }

    fun fetchItems(snapshot: DataSnapshot, teamName: String){
        val v = snapshot.value
        if (v!=null && v is Map<*, *>) {
            var map = mutableMapOf<String, Map<String,String>>()
            mutableItemsTeams.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = v as Map<String, String>
                }else
                    map[item.key] = item.value
            }
            mutableItemsTeams.value = map
        }
        if(v == null){
            var map = mutableMapOf<String, Map<String,String>>()
            mutableItemsTeams.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = emptyMap()
                }else
                    map[item.key] = item.value
            }
            mutableItemsTeams.value = map
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

    private fun generateRandom(numbers : Int, min : Int, max:Int): List<Int>{
        val list : MutableList<Int> = mutableListOf<Int>()
        var i = 0;
        while (i < numbers){
            val rand = (min..max).shuffled().last()
            if(list.find { it == rand } == null){
                list.add(rand)
                i++
            }
        }
        return list
    }

    fun sumCO2(team : String) : String{
        var ids = itemsTeams.value?.get(team)?.values
        var objects = items.filter { ids?.contains(it.id.toString()) ?: false }
        return objects.sumOf { it.CO2 }.toString()
    }

    fun sumHappiness(team : String) : String{
        var ids = itemsTeams.value?.get(team)?.values
        var objects = items.filter { ids?.contains(it.id.toString()) ?: false }
        return objects.sumOf { it.happiness }.toString()
    }

    fun addItem(team : String, id : Int, square : Int){
        val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
        ref.child("teams").child(team).child("items").child(square.toString()).setValue(id.toString())
    }

    fun deleteItem(team : String, square : Int){
        val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
        ref.child("teams").child(team).child("items").child(square.toString()).removeValue()
    }
}