package it.polito.did.gameskeleton.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.greenapplication.screens.IntroScreen
import com.example.greenapplication.screens.QRCodeScanner
import it.polito.did.gameskeleton.MainActivity
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun InitialScreen(onStartNewGame: () -> Unit,
                  onJoinGame: (String) -> Unit,
                  activity: MainActivity,
                  modifier: Modifier = Modifier
) {
    val matchId = remember {
        mutableStateOf("")
    }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "intro"){
        composable("intro"){
            IntroScreen(navController)
        }
        composable("initial"){
            Initial(onStartNewGame = onStartNewGame, onJoinGame = onJoinGame,
                    matchId = matchId, navController = navController, activity = activity)
        }
        composable("scanner"){
            QRCodeScanner(navController = navController, onJoinGame)
        }
    }
}

@Composable
fun Initial(onStartNewGame: () -> Unit,
            onJoinGame: (String) -> Unit,
            modifier: Modifier = Modifier,
            matchId : MutableState<String>,
            navController: NavController,
            activity: MainActivity
){
    val context = LocalContext.current

    val permission = Manifest.permission.CAMERA
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            navController.navigate("scanner")
        } else {
        }
    }
    fun checkAndRequestCameraPermission(
        context: Context,
        permission: String,
        launcher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            navController.navigate("scanner")
        } else {
            launcher.launch(permission)
        }
    }
    GenericScreen(title = "Inizia partita") {
        Column(modifier.fillMaxWidth()) {
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onStartNewGame ) {
                Text("Crea nuova partita",
                    color = MaterialTheme.colors.background)
            }
            Spacer(Modifier.height(32.dp))
            Row {
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)
                )
                Text("o")
                Divider(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Spacer(
                Modifier.height(32.dp)
                //.background(color = Color.Yellow)
            )
            TextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                singleLine = true,
                value = matchId.value, onValueChange = {matchId.value = it})
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {onJoinGame(matchId.value)}) {
                Text("Unisciti alla partita",
                    color = MaterialTheme.colors.background)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    checkAndRequestCameraPermission(context, permission, launcher)
                }) {
                Text("Scannerizza Qr Code",
                    color = MaterialTheme.colors.background)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInitialScreen() {
    GameSkeletonTheme {
        //InitialScreen({},{})
    }

}