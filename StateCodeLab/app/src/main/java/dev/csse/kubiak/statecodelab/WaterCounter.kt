package dev.csse.kubiak.statecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {

            Text("You've had $count glasses.")
        }
        Row(Modifier.padding(top = 8.dp)) {
            Button(onClick = onIncrement,
                Modifier.padding(top = 8.dp),
                enabled = count < 10) {
                Text("Add one")
            }

        }
    }
}