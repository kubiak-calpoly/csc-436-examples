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
}

class Division(
  val beat: Int = 1,
  val subdivision: Int = 0
)
