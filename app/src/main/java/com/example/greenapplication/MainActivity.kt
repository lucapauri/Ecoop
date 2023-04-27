package it.polito.did.gameskeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.greenapplication.pag1.Pag1
import com.example.greenapplication.pag2registrati.Pag2Registrati
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameSkeletonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Pag1()
                }
            }
        }
    }
}

@Preview(widthDp = 375, heightDp = 812)
@Composable
fun PreviewFrame() {
    GameSkeletonTheme {

        Pag1()
    }

}

