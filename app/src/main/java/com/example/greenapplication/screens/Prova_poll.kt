package com.example.greenapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme

@Composable
fun Poll() {
    // Define the state for your poll options
    val options = listOf("Option 1", "Option 2", "Option 3")

    // Define the state to track the selected option
    val selectedOption = remember { mutableStateOf("") }

    // Create a RadioGroup with options
    Column {
        options.forEach { option ->
            Row(Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = selectedOption.value == option,
                    onClick = { selectedOption.value = option },
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = option)
            }
        }
    }

    // Display the selected option
    Text(text = "Selected option: \n ${selectedOption.value}")
}

@Preview
@Composable
fun PreviewPoll(){
    GameSkeletonTheme {
        Poll()
    }
}
