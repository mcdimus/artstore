package eu.maksimov.artstore.service.generator

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

internal fun mockRandom() = object : Random() {
  private var counter = AtomicInteger(0)
  private var switch = AtomicBoolean(true)
  override fun nextBits(bitCount: Int): Int {
    return if (switch.get()) {
      counter.getAndIncrement().also { if (it == 255) switch.set(false) }
    } else {
      counter.decrementAndGet().also { if (it == 0) switch.set(true) }
    }
  }
}
