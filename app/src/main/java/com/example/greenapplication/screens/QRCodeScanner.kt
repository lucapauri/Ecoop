package com.example.greenapplication.screens

import android.Manifest
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import it.polito.did.gameskeleton.MainActivity

@Composable
fun QRCodeScanner(navController: NavHostController,
                  onJoinGame: (String) -> Unit) {


    /*val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result -> onJoinGame(result.contents) }
    )
    SideEffect {
        scanLauncher.launch(ScanOptions())
    }*/

    val context = LocalContext.current
    var scanFlag = remember {
        mutableStateOf(false)
    }

    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {
            val capture = CaptureManager(context as Activity, this)
            capture.initializeFromIntent(context.intent, null)
            this.setStatusText("")
            this.resume()
            capture.decode()
            this.decodeContinuous { result ->
                Log.d("Gamemanager", result.text)
                if(scanFlag.value){
                    return@decodeContinuous
                }
                scanFlag.value = true
                result.text?.let { barCodeOrQr->
                    //Do something and when you finish this something
                    //put scanFlag = false to scan another item
                    Log.d("Gamemanager", barCodeOrQr)
                    onJoinGame(barCodeOrQr)
                    scanFlag.value = false
                }
                //If you don't put this scanFlag = false, it will never work again.
                //you can put a delay over 2 seconds and then scanFlag = false to prevent multiple scanning

            }
        }
    }

    AndroidView(
        factory = { compoundBarcodeView },
    )
}
