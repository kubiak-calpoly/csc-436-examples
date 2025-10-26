package dev.csse.kubiak.demoweek6

class Loop {
  var barsToLoop: Int = 2
  var beatsPerBar: Int = 4
  val beats: MutableList<Division> = mutableListOf()

  init {
    for(bar in (1)..barsToLoop) {
      for(beat in (1)..beatsPerBar) {
          beats.add(
            Division(
              bar = bar,
              beat = beat,
            )
          )
      }
    }
  }

  data class Position(
    val iteration: Int = 1,
    val bar: Int = 1,
    val beat: Int = 1
  )

  val ticksPerIteration: Int
    get() {
      return beatsPerBar * barsToLoop
    }

  fun getPosition(tickCount: Int): Position {
    val iterationNumber = tickCount / ticksPerIteration
    val ticksThisIteration = tickCount -
            (ticksPerIteration * iterationNumber)
    val barNumber = ticksThisIteration /
            (beatsPerBar )
    val beatNumber = ticksThisIteration -
            (beatsPerBar *  barNumber)
    return Position(iterationNumber + 1,
      barNumber + 1,
      beatNumber + 1
    )
  }
}

class Division(
  val bar: Int = 1,
  val beat: Int = 1
)


