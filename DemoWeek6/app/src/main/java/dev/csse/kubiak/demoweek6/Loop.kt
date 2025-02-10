package dev.csse.kubiak.demoweek6

class Loop {
  var barsToLoop: Int = 1
  var beatsPerBar: Int = 4
  var subdivisions: Int = 2
  val beats: MutableList<Division> = mutableListOf()

  init {
    for(bar in (1)..barsToLoop) {
      for(beat in (1)..beatsPerBar) {
        for(div in (1)..subdivisions) {
          beats.add(
            Division(
              beat = beat,
              subdivision = div
            )
          )
        }
      }
    }
  }

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
}

class Division(
  val beat: Int = 1,
  val subdivision: Int = 1
)


