package dev.csse.kubiak.audiodemo.ui

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.audiodemo.R

@Composable
@RequiresPermission(Manifest.permission.RECORD_AUDIO)
fun SamplerScreen(
  modifier: Modifier = Modifier,
  context: Context = LocalContext.current,
  model: SamplerViewModel = viewModel()
) {
  val samplesToKeep = 20
  val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  )  { isGranted ->
    model.hasPermission = isGranted
    model.initializeSampler(context)
  }

  LaunchedEffect(model.hasPermission) {
    Log.d("RecordScreen", "Checking permission to record ${model.hasPermission}")
    if ( !model.hasPermission )
      model.requestPermission(context, permissionLauncher)
    else
      model.initializeSampler(context)
  }

  Column(modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally) {
    SamplerGraph(
      samplesToKeep,
      modifier = Modifier.fillMaxWidth().aspectRatio(1f)
    )
    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
      IconButton(
        enabled = !model.isRunning,
        onClick = {
          model.startSampling(samplesToKeep)
        }
      ) {
        Icon(
          painterResource(R.drawable.outline_mic_24),
          contentDescription = "Record",
          modifier = Modifier.scale(2.0f)
        )
      }
      IconButton(
        enabled = model.isRunning,
        onClick = { model.pauseSampling() }
      ) {
        Icon(
          painterResource(R.drawable.outline_pause_circle_24),
          contentDescription = "Pause",
          modifier = Modifier.scale(2.0f)
        )
      }
      IconButton(
        onClick = { model.resetSampler() }
      ) {
        Icon(
          painterResource(R.drawable.outline_restart_alt_24),
          contentDescription = "Reset",
          modifier = Modifier.scale(2.0f)
        )
      }
    }
  }
}

@Composable
fun SamplerGraph(
  samplesToKeep: Int,
  modifier: Modifier = Modifier,
  model: SamplerViewModel = viewModel()
) {

  BoxWithConstraints(
    modifier = modifier
  ) {
    val h = this.maxHeight
    val w = this.maxWidth

    val vectorPainter = rememberVectorPainter(
      defaultHeight = h,
      defaultWidth = w,
      viewportHeight = h.value,
      viewportWidth = w.value,
      autoMirror = true
    ) { vpWidth, vpHeight ->
      val segWidth = vpWidth/samplesToKeep

      Group() {
        model.latestSamples.mapIndexed { index, sample ->
          val x = index * segWidth
          val y = sample.amplitude * (vpHeight / 2 / 0xffff)
          val bottom = vpHeight/2 - y
          val top = vpHeight/2 + y
          Path(
            listOf(
              PathNode.MoveTo(x, bottom),
              PathNode.LineTo(x, top),
              PathNode.LineTo(x + segWidth, top),
              PathNode.LineTo(x + segWidth, bottom),
              PathNode.LineTo(x, bottom)
            ),
            fill = SolidColor(Color(0xFF559C55))
          )
        }
      }
    }

    Image(
      vectorPainter,
      contentDescription = "Grid of pads for all the beatz",
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    )
  }
}