package dev.csse.kubiak.demoweek4.ui

import android.R
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.csse.kubiak.demoweek4.ui.theme.DemoWeek4Theme
import java.nio.file.WatchEvent

data class Message(
  var text: String = "",
  var index: Int = 0
)

val TAG = "MessageList"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageList(messages: List<Message>) {
  Box(modifier = Modifier.fillMaxSize() ) {
    LazyColumn(
      modifier = Modifier.fillMaxWidth()
        .background(Color(0xFF424832))
    ) {
      stickyHeader {
        Row(
          modifier = Modifier.fillMaxWidth()
            .border(1.dp, Color(0xFF000000))
            .background(Color(0xFFF8F8D0))
            .padding(10.dp)
        ) {

          Text(
            "Messages",
            style = MaterialTheme.typography.titleLarge
          )
        }

      }
      items(messages) { message ->
        MessageRow(message)
      }
    }
    Row(modifier = Modifier
      .align(Alignment.BottomCenter )
      .border(1.dp, Color(0xFF000000))
      .background(Color(0xFFF8F8D0))
      .padding(10.dp)) {
      Text("Sticky Footer")
    }
  }
}


@Composable
fun MessageRow(message: Message) {
  Log.i(TAG, "Composing MessageRow ${message.index}")
  Text(message.text,
    style = MaterialTheme.typography.titleLarge,
    modifier = Modifier.fillMaxWidth()
      .padding(5.dp)
      .background(Color.White)
      .padding(5.dp))
}

@Preview
@Composable
fun MessageListPreview() {
  val messages = Array<Message>(200) {
      n -> Message("This is message $n", n)
  }

  DemoWeek4Theme {
    Surface {
      MessageList(listOf(*messages))
    }
  }
}
