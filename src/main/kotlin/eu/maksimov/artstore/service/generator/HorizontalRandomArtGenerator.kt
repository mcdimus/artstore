package eu.maksimov.artstore.service.generator

import org.springframework.stereotype.Component
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.random.Random

@Component
class HorizontalRandomArtGenerator(private val random: Random = Random.Default) : ArtGenerator {

  override fun generate(size: Int): ByteArray {
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until size) {
      val rgb = random.nextRGB()

      for (x in 0 until size) {
        image.setRGB(x, y, rgb)
      }
    }

    return ByteArrayOutputStream().use {
      ImageIO.write(image, "JPEG", it)
      it.toByteArray()
    }
  }

}
