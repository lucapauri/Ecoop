package it.polito.did.gameskeleton

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greenapplication.Infrastruttura
import com.example.greenapplication.Mossa
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
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
    val items : MutableList<Infrastruttura> = mutableListOf()
    private val initialItems = 2
    private val initialEnergy = 50
    private val initialTime = 1800000
    private val initialTimeTurn = 150000
    private val refreshTimer = 500
    private val initialActionsPoints = 2
    private val upgradeCost = 200

    init {
        //firebase.setLogLevel(Logger.Level.DEBUG)
        scope.launch {
            try {
                firebaseAuth.signInAnonymously().await()
                delay(500)
                mutableScreenName.value = ScreenName.Initial
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?:"Unknown error")
            }
        }
        items.add(Infrastruttura(1,"Parco",-4,4,5, 10,1,
            com.example.greenapplication.R.drawable.parco,
            "Terreno pubblico con prati e piante." ))
        items.add(Infrastruttura(2,"Bus",5,3,10,10,1,
            com.example.greenapplication.R.drawable.bus,
        "Autoveicolo attrezzato per il trasporto collettivo di persone su percorsi urbani e suburbani"))
        items.add(Infrastruttura(3,"Centrale a carbone",15,1,30,10,1,
            com.example.greenapplication.R.drawable.carbone,
            "Centrale termoelettrica che brucia carbone per generare energia elettrica."))
        items.add(Infrastruttura(4,"Centrale a gas",13,2,28,10, 1,
            com.example.greenapplication.R.drawable.centralegas,
            "Centrale termoelettrica che brucia gas naturale per generare energia elettrica."))
        items.add(Infrastruttura(5,"Impianto a biometano",12,3,25,10,1,
            com.example.greenapplication.R.drawable.biometano,
        "Impianto alimentato a biogas che produce energia elettrica e termicapartire da materiali organici."))
        items.add(Infrastruttura(6,"Termovalorizzatore",4,-3,30,10, 1,
            com.example.greenapplication.R.drawable.biometano,
        "Inceneritore che converte il calore generato dalla combustione dei rifiuti in energia destinata ad altro uso."))
        items.add(Infrastruttura(7,"Metropolitana",3,6,12,10,2,
            com.example.greenapplication.R.drawable.metro,
        "Mezzo di trasporto rapido collettivo costituito da una linea ferroviaria prevalentemente sotterranea, che consente spostamenti all'interno di una grande città."))
        items.add(Infrastruttura(8,"Area Verde",-8,8,8,10,2,
            com.example.greenapplication.R.drawable.areaverde,
        "Zona costituita da parchi, prati e giardini e situata nell'area urbana."))
        items.add(Infrastruttura(9,"Centrale idroelettrica",10,4,40,10,2,
            com.example.greenapplication.R.drawable.idroelettrica,
        "Centrale che trasforma l'energia idraulica di un corso d'acqua, naturale o artificiale, in energia elettrica rinnovabile."))
        items.add(Infrastruttura(10,"Centrale Geotermica",10,7,40,10,2,
            com.example.greenapplication.R.drawable.geotermica,
        "Centrale termoelettrica che produce energia elettrica utilizzando come fonte di energia primaria l'energia geotermica ovvero il calore proveniente dalle profondità della Terra."))
        items.add(Infrastruttura(11,"Centrale nucleare",5,2,40,10,2,
            com.example.greenapplication.R.drawable.nucleare,
        "Centrale che produce energia elettrica sfruttando il calore generato dalle reazioni nucleari."))
        items.add(Infrastruttura(12,"Colonnine per auto elettriche",3,7,40,10,3,
            com.example.greenapplication.R.drawable.colonnine,
        "Dispositivi di distribuzione dell'energia elettrica, attraverso il quale è possibile ricaricare la batteria di autoveicoli elettrici."))
        items.add(Infrastruttura(13,"Orto Cittadino",-12,10,10,10,3,
            com.example.greenapplication.R.drawable.orto,
        "Spazio verde di dimensione variabile e generalmente di proprietà comunale che viene affidato a cittadini o associazioni per la produzione di erbe aromatiche, frutta, verdura e fiori."))
        items.add(Infrastruttura(14,"Pista ciclabile",0,8,8,10,3,
            com.example.greenapplication.R.drawable.ciclabile,
        "Percorso riservato alle biciclette, dove il traffico motorizzato è escluso."))
        items.add(Infrastruttura(15,"Centrale eolica",5,2,3,10,3,
            com.example.greenapplication.R.drawable.eolica,
        "Aree territoriali in cui sono state concentrate diverse turbine eoliche di grandi dimensioni che producono energia elettrica."))
        items.add(Infrastruttura(16,"Centrale fotovoltaica",4,30,40,10,3,
            com.example.greenapplication.R.drawable.fotovoltaica,
        "Centrale che immagazzina l'energia solare attraverso dei pannelli solari per produrre energia elettrica."))
    }

    private val mutableWinningTeam = MutableLiveData<String>().also {
        it.value = ""
    }
    val winningTeam: LiveData<String> = mutableWinningTeam


    val winningTreshold = 30

    //Current items level
    private val mutableItemsLevel = MutableLiveData<Map<String, Int>>().also {
        var m = mutableMapOf<String, Int>()
        teamNames.forEach{t->
            m[t] = 1
        }
        it.value = m
    }
    val itemsLevel: LiveData<Map<String, Int>> = mutableItemsLevel

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
        val map : MutableMap<String,Map<String,String>> = mutableMapOf()
        teamNames.forEach{ team ->
            map[team] = emptyMap()
        }
        it.value = map
    }
    val itemsTeams : LiveData<Map<String, Map<String,String>>> = mutableItemsTeams

    //Current CO2
    private val mutableCO2 = MutableLiveData<Map<String,String>>().also {
        val map : MutableMap<String,String> = mutableMapOf()
        teamNames.forEach{ team ->
            map[team] = "0"
        }
        it.value = map
    }
    val cO2 : LiveData<Map<String,String>> = mutableCO2

    private val mutableVoted = MutableLiveData<Boolean>().also{
        it.value = false
    }
    val voted : LiveData<Boolean> = mutableVoted

    var moved = false

    //Current moves
    private val mutablemoves = MutableLiveData<List<Mossa>>().also {
        it.value = emptyList()
    }
    val moves : LiveData<List<Mossa>> = mutablemoves

    //Current Happiness
    private val mutableHappiness = MutableLiveData<Map<String,String>>().also {
        val map : MutableMap<String,String> = mutableMapOf()
        teamNames.forEach{ team ->
            map[team] = "0"
        }
        it.value = map
    }
    val happiness : LiveData<Map<String,String>> = mutableHappiness

    //Current energy
    private val mutableEnergy = MutableLiveData<Map<String,String>>().also {
        val map : MutableMap<String,String> = mutableMapOf()
        teamNames.forEach{ team ->
            map[team] = initialEnergy.toString()
        }
        it.value = map
    }
    val energy : LiveData<Map<String,String>> = mutableEnergy

    private val mutableTimer = MutableLiveData<Long>().also {
        it.value = initialTime.toLong()
    }

    val timer : LiveData<Long> = mutableTimer

    private val mutableTimerTurn = MutableLiveData<Long>().also {
        it.value = initialTimeTurn.toLong()
    }

    val timerTurn : LiveData<Long> = mutableTimerTurn

    private val mutableActionPoints = MutableLiveData<Int>().also {
        it.value = initialActionsPoints
    }

    val actionPoints : LiveData<Int> = mutableActionPoints

    private val mutableSurveyOn = MutableLiveData<Boolean>().also {
        it.value = false
    }

    val surveyOn : LiveData<Boolean> = mutableSurveyOn

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
        return players.value?.get(firebaseAuth.uid) ?: ""
    }

    fun createNewGame() {
        scope.launch {
            try {
                //val ref = firebase.getReference("abc")
                val ref = firebase.reference.push()
                ref.setValue(
                    mapOf(
                        "date" to LocalDateTime.now().toString(),
                        "owner" to firebaseAuth.uid,
                        "screen" to "WaitingStart"
                    )
                ).await()
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
            "End" -> ScreenName.End(winningTeam?.value?:"")
            else -> ScreenName.Error("Unknown screen $name")
        }
    }

    fun joinGame(matchId:String) {
        Log.d("GameManager", "Entered")
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
                        //Listen items level change
                        ref.child("level").child(it).addValueEventListener(object:ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(snapshot.value != null){
                                    mutableItemsLevel.value = mutableItemsLevel.value?.mapValues { m->
                                        if(m.key == it ){
                                            snapshot.value.toString().toInt()
                                        }else{
                                            m.value
                                        }
                                    }
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
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
                        //Listen energy change
                        ref.child("teams").child(it).child("Energy").addValueEventListener(object:ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                fetchEnergy(snapshot, it)
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                    }
                    //Listen Timer
                    ref.child("Timer").addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.value != null){
                                mutableTimer.value = snapshot.value as Long
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //Listen TimerTurn
                    ref.child("TimerTurn").addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.value != null){
                                mutableTimerTurn.value = snapshot.value as Long
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //Listen action points
                    ref.child("actionPoints").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val v = snapshot.value
                            if (v!=null) {
                                mutableActionPoints.value = v.toString().toInt()
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //Listen survey on
                    ref.child("surveyOn").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val v = snapshot.value
                            if(v!=null){
                                val b = v as Boolean
                                if(surveyOn.value == true && !v){
                                    moved = false
                                    mutableVoted.value = false
                                }
                                mutableSurveyOn.value = b
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //Listen winning team
                    ref.child("winningTeam").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val v = snapshot.value
                            if (v!=null) {
                                mutableWinningTeam.value = v.toString()
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //Listen moves
                    ref.child("moves").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val v = snapshot.value
                            if(v != null && v is Map<*,*>){
                                var l = mutableListOf<Mossa>()
                                v.values.forEach {
                                    if(it != null && it is HashMap<*,*>){
                                        l.add(Mossa(it["key"].toString(), it["type"].toString(), it["team"].toString(),
                                            it["id"].toString().toInt(), it["square"].toString().toInt(), it["votes"].toString().toInt()
                                        ))
                                    }
                                }
                                mutablemoves.value = l
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
                    //Initialize items level
                    ref.child("level").child(it).setValue(1)
                    //Listen items level change
                    ref.child("level").child(it).addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.value != null){
                                mutableItemsLevel.value = mutableItemsLevel.value?.mapValues { m->
                                    if(m.key == it ){
                                        snapshot.value.toString().toInt()
                                    }else{
                                        m.value
                                    }
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    val team = ref.child("teams").child(it).child("items")
                    //Listen items change
                    team.addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            fetchItems(snapshot,it)
                            ref.child("teams").child(it).child("CO2").setValue(sumCO2(it))
                            ref.child("teams").child(it).child("Happiness").setValue(sumHappiness(it))
                            updateSumItems(it)
                            if(sumHappiness(it).toInt() - sumCO2(it).toInt() > winningTreshold){
                                ref.child("winningTeam").setValue(it)
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
                                ref.child("screen").setValue("End")
                            }
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
                    //Listen energy change
                    ref.child("teams").child(it).child("Energy").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            fetchEnergy(snapshot, it)
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    //random items assignment
                    val squares = generateRandom(initialItems, 1, 9)
                    generateRandom(initialItems, 1, 6).forEachIndexed{ index, id ->
                        team.child(squares[index].toString()).setValue(id.toString())
                        updateSumItems(it)
                    }
                    //Initialize energy
                    ref.child("teams").child(it).child("Energy").setValue(initialEnergy.toString())
                }
                mutableScreenName.value = ScreenName.Dashboard
                //Listen Timer
                ref.child("Timer").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.value != null){
                            mutableTimer.value = snapshot.value as Long
                            if(mutableTimer?.value?:0 <= 0.toLong()){
                                endGame()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                //Listen TimerTurn
                ref.child("TimerTurn").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.value != null){
                            mutableTimerTurn.value = snapshot.value as Long
                            if(mutableTimerTurn.value!! < 0){
                                nextTurn()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                //Create Timer
                startTimer(ref)
                //Create TimerTurn
                startTimerTurn(ref)
                //Listen winning team
                ref.child("winningTeam").addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val v = snapshot.value
                        if (v!=null) {
                            mutableWinningTeam.value = v.toString()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                //Listen action points change
                ref.child("actionPoints").addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val v = snapshot.value
                        if (v!=null) {
                            mutableActionPoints.value = v.toString().toInt()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                //Initialize actions points
                ref.child("actionPoints").setValue(initialActionsPoints)
                //Initialize survey on
                ref.child("surveyOn").setValue(false)
                //Listen survey on
                ref.child("surveyOn").addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val v = snapshot.value
                        if(v!=null){
                            mutableSurveyOn.value = v as Boolean
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                //Listen moves
                ref.child("moves").addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val v = snapshot.value
                        if(v != null && v is Map<*,*>){
                            var l = mutableListOf<Mossa>()
                            v.values.forEach {
                                if(it != null && it is Map<*,*>){
                                    l.add(Mossa(it["key"].toString(), it["type"].toString(), it["team"].toString(),
                                        it["id"].toString().toInt(), it["square"].toString().toInt(), it["votes"].toString().toInt()
                                    ))
                                }
                            }
                            if(l.size == players.value?.filter { it.value==turn.value }?.size ?: -1){
                                ref.child("surveyOn").setValue(true)
                            }
                            if(l.sumOf { it.votes } == players.value?.filter { it.value==turn.value }?.size ?: -1){
                                ref.child("surveyOn").setValue(false)
                                val max = l.maxByOrNull { it.votes }
                                if(max != null){
                                    move(max)
                                }
                                ref.child("moves").removeValue()
                            }
                            mutablemoves.value = l
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
    }

    fun upgradeLevel(team : String){
        scope.launch {
            try {
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                ref.child("level").child(team).setValue(ServerValue.increment(1))
            }catch (e : Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
            updateEnergy(-upgradeCost, team)
            nextTurn()
        }
    }

    private fun endGame(){
        scope.launch {
            var m = mutableMapOf<String, Int>()
            teamNames.forEach{
                val happiness = happiness.value?.get(it)?.toInt()?:0
                val co2 = cO2.value?.get(it)?.toInt()?:0
                val sum = happiness - co2
                m[it] = sum
            }
            val win = m.maxByOrNull { it.value }?.key?: ""
            try{
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                ref.child("winningTeam").setValue(win)
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
                ref.child("screen").setValue("End")
            }catch (e : Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    private fun move(move : Mossa){
        when(move.type){
            "add" -> addItem(move.team, move.id, move.square)
            "delete" -> deleteItem(move.team, move.square)
            "replace" -> {
                deleteItem(move.team, move.square)
                addItem(move.team, move.id, move.square)
            }
            "upgrade" -> {
                upgradeLevel(move.team)
            }
        }
    }

    private fun startTimer(ref : DatabaseReference){
        val handlerTime = Handler()
        handlerTime.postDelayed(
            object : Runnable {
                override fun run() {
                    ref.child("Timer").setValue(timer.value?.minus(refreshTimer.toLong()))
                    handlerTime.postDelayed(this, refreshTimer.toLong())
                }
            }
        , refreshTimer.toLong())
    }

    private fun startTimerTurn(ref : DatabaseReference){
        val handlerTime = Handler()
        handlerTime.postDelayed(
            object : Runnable {
                override fun run() {
                    ref.child("TimerTurn").setValue(timerTurn.value?.minus(refreshTimer.toLong()))
                    handlerTime.postDelayed(this, refreshTimer.toLong())
                }
            }
            , refreshTimer.toLong())
    }

    fun fetchCO2(snapshot: DataSnapshot, teamName: String){
        val v = snapshot.value ?: "0"
        if(v is String){
            val map = mutableMapOf<String,String>()
            mutableCO2.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = v
                }else
                    map[item.key] = item.value
            }
            mutableCO2.value = map
        }
    }

    fun fetchHappiness(snapshot: DataSnapshot, teamName: String){
        val v = snapshot.value ?: "0"
        if(v is String){
            val map = mutableMapOf<String,String>()
            mutableHappiness.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = v
                }else
                    map[item.key] = item.value
            }
            mutableHappiness.value = map
        }
    }

    fun fetchEnergy(snapshot: DataSnapshot, teamName: String){
        val v = snapshot.value ?: "0"
        if(v is String){
            val map = mutableMapOf<String,String>()
            mutableEnergy.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = v
                }else
                    map[item.key] = item.value
            }
            mutableEnergy.value = map
        }
    }

    fun fetchItems(snapshot: DataSnapshot, teamName: String){
        val v = snapshot.value
        if (v!=null && v is Map<*, *>) {
            val map = mutableMapOf<String, Map<String,String>>()
            mutableItemsTeams.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = v as Map<String, String>
                }else
                    map[item.key] = item.value
            }
            mutableItemsTeams.value = map
        }
        if(v == null){
            val map = mutableMapOf<String, Map<String,String>>()
            mutableItemsTeams.value?.forEach{ item ->
                if(item.key == teamName){
                    map[item.key] = emptyMap()
                }else
                    map[item.key] = item.value
            }
            mutableItemsTeams.value = map
        }
    }

    private fun updateSumItems(team : String){
        scope.launch {
            try{
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                val currentItems = itemsTeams.value?.get(team)?.values?.map { item -> item.toInt() }
                items.forEach{
                    val id = it.id
                    val occurrencies = currentItems?.count { item -> item == id  }
                    ref.child("teams").child(team).child("sumItems").child(id.toString()).setValue(occurrencies)
                }
            }catch (e : Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    fun nextTurn(){
        scope.launch {
            try{
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                val v = turn.value
                if (v is String){
                    var index = teamNames.indexOf(v)
                    index = (index + 1) % teamNames.size
                    ref.child("turn").setValue(teamNames[index]).await()
                    val ids = itemsTeams.value?.get(teamNames[index])?.values
                    val objects = items.filter { ids?.contains(it.id.toString()) ?: false }
                    val price = objects.sumOf { it.energy }
                    updateEnergy(price, teamNames[index])
                    ref.child("actionPoints").setValue(initialActionsPoints)
                    ref.child("TimerTurn").setValue(initialTimeTurn.toLong())
                    ref.child("moves").removeValue()
                }
            }catch (e:Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    private fun generateRandom(numbers : Int, min : Int, max:Int): List<Int>{
        val list : MutableList<Int> = mutableListOf()
        var i = 0
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
        val ids = mutableItemsTeams.value?.get(team)?.values
        val objects = items.filter { ids?.contains(it.id.toString()) ?: false }
        return objects.sumOf { it.CO2 }.toString()
    }

    fun sumHappiness(team : String) : String{
        val ids = itemsTeams.value?.get(team)?.values
        val objects = items.filter { ids?.contains(it.id.toString()) ?: false }
        return objects.sumOf { it.happiness }.toString()
    }

    private fun updateEnergy(newEnergy : Int, team :String){
        scope.launch {
            try{
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                val pEnergy = energy.value?.get(team)?.toInt() ?: 0
                val sum = pEnergy + newEnergy
                ref.child("teams").child(team).child("Energy").setValue(sum.toString())
            }catch (e:Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    private fun updateActionPoints(){
        scope.launch {
            try{
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                if((actionPoints.value?.compareTo(2) ?: -1) >= 0){
                    ref.child("actionPoints").setValue(actionPoints.value?.minus(1))
                }else{
                    nextTurn()
                }
            }catch(e:Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    fun addItem(team : String, id : Int, square : Int){
        scope.launch {
            try {
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                ref.child("teams").child(team).child("items").child(square.toString()).setValue(id.toString())
            }catch (e:Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
        val price = -items.first { it.id == id }.price
        updateEnergy(price, team)
        updateActionPoints()
    }

    fun deleteItem(team : String, square : Int){
        val id = itemsTeams.value?.get(team)?.get(square.toString()) ?: ""
        if(id != ""){
            scope.launch {
                try {
                    val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                    ref.child("teams").child(team).child("items").child(square.toString()).removeValue()
                }catch (e:Exception){
                    mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
                }
            }
            val price = -(items.first { it.id == id.toInt() }.price)/2
            updateEnergy(price, team)
            updateActionPoints()
        }
    }

    fun addMove(type : String, team : String, id : Int, square : Int){
        scope.launch {
            try {
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                if(mutablemoves.value?.filter { it.type == type && it.id == id && it.square == square }?.size?:0 == 0){
                    val m = Mossa(firebaseAuth.uid.toString(), type, team, id, square, 0)
                    ref.child("moves").child(firebaseAuth.uid.toString()).setValue(m)
                    moved = true
                }else{
                    val m = Mossa(firebaseAuth.uid.toString(), "duplicate", team, id, square, 0)
                    ref.child("moves").child(firebaseAuth.uid.toString()).setValue(m)
                    moved = true
                }
            }catch (e:Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    fun voteMove(key : String){
        scope.launch {
            try {
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                ref.child("moves").child(key).child("votes")
                    .setValue(ServerValue.increment(1))
                mutableVoted.value = true
            }catch (e:Exception){
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    fun formatTime(millis : Long) : List<Int>{
        var seconds = (millis / 1000).toInt()
        val minutes = seconds / 60
        seconds %= 60
        return listOf(minutes, seconds)
    }
}