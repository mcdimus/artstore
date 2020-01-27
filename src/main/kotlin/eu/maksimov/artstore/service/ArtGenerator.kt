package eu.maksimov.artstore.service

import org.springframework.stereotype.Component
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.random.Random

@Component
class ArtGenerator {

  companion object {
    private const val MAX_COLOR_BITS = 256
  }

  fun generate(size: Int): ByteArray {
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until size) {
      for (x in 0 until size) {
        val red = Random.nextInt(MAX_COLOR_BITS)
        val green = Random.nextInt(MAX_COLOR_BITS)
        val blue = Random.nextInt(MAX_COLOR_BITS)

        val pixel = (red shl 16) or (green shl 8) or blue
        image.setRGB(x, y, pixel)
      }
    }

    return ByteArrayOutputStream().use {
      ImageIO.write(image, "JPEG", it)
      it.toByteArray()
    }
  }

}
