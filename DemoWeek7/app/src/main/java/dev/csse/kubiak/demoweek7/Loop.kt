package dev.csse.kubiak.demoweek7

import androidx.compose.runtime.Composable

data class Loop(
  val barsToLoop: Int = 1,
  val beatsPerBar: Int = 4,
  val subdivisions: Int = 2
) {

  data class Position(
    val iteration: Int = 1,
    val bar: Int = 1,
    val beat: Int = 1,
    val subdivision: Int = 1
  )

  val ticksPerIteration: Int
    get() {
      return beatsPerBar * subdivisions * barsToLoop
    }

  fun getPosition(tickCount: Int): Position {
    val iterationNumber = tickCount / ticksPerIteration
    val ticksThisIteration = tickCount -
            (ticksPerIteration * iterationNumber)
    val barNumber = ticksThisIteration /
            (beatsPerBar * subdivisions)
    val ticksThisBar = ticksThisIteration -
            (beatsPerBar * subdivisions * barNumber)
    val beatNumber = ticksThisBar / subdivisions
    val subdivisionNumber = ticksThisBar -
            (subdivisions * beatNumber)
    return Position(iterationNumber + 1,
      barNumber + 1,
      beatNumber + 1,
      subdivisionNumber + 1
    )
  }

  @Composable
  fun forEachTick(
    doTick: @Composable (bar: Int, beat: Int, subdivision: Int) -> Unit
  ) {
    for (bar in (1)..barsToLoop) {
      for (beat in (1)..beatsPerBar) {
        for (div in (1)..subdivisions) {
          doTick(bar, beat, div)
        }
      }
    }
  }
}



