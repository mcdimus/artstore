package eu.maksimov.artstore.service.generator

import kotlin.random.Random

private const val MAX_COLOR_BITS = 256

internal fun Random.nextRGB(): Int {
  val red = this.nextInt(MAX_COLOR_BITS)
  val green = this.nextInt(MAX_COLOR_BITS)
  val blue = this.nextInt(MAX_COLOR_BITS)

  return (red shl 16) or (green shl 8) or blue
}
