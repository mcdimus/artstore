package eu.maksimov.artstore.service.generator

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class HorizontalRandomArtGeneratorTest {

  @Test
  fun `generates image close to the reference`() {
    // given
    val size = 256
    val random = mockRandom()

    // when
    val result = HorizontalRandomArtGenerator(random).generate(size)

    // then
    val generatedImage = ByteArrayInputStream(result).use { ImageIO.read(it) }
    val referenceImage = this.javaClass.classLoader.getResourceAsStream("reference_art_horizontal_random.jpg")
        .use { ImageIO.read(it) }

    assertThat(differencePercent(generatedImage, referenceImage)).isCloseTo(0.0, Offset.offset(1.0))
  }

}
