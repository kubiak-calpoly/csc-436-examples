package dev.csse.kubiak.demoweek4.ui

import android.R
import android.nfc.Tag
import android.telephony.TelephonyCallback
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme

data class Message(
  var text: String = "",
  var index: Int = 0
)

val TAG = "MessageList"

@Composable
fun MessageList(messages: List<Message>) {
  LazyColumn {
    items(messages) { message ->
      MessageRow(message)
    }
  }
}


@Composable
fun MessageRow(message: Message,
               modifier: Modifier = Modifier) {
  Log.i(TAG, "Composing MessageRow ${message.index}")
  Row(modifier = modifier.fillMaxWidth() ) {
    Text(
      message.text,
      style = MaterialTheme.typography.titleLarge
    )
  }
}

@Preview
@Composable
fun MessageListPreview() {
  DemoWeek4Theme {
    Surface {
       MessageList(sampleMessages())
    }
  }
}

fun sampleMessages(): List<Message> {
  val messages = Array<Message>(200) { n ->
    Message("This is message $n", n)
  }
  return listOf(*messages)
}