package eu.maksimov.artstore.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ArtGeneratorTest {

  @Test
  fun generate() {
    val result = ArtGenerator().generate(20)
    assertThat(result).isNotEmpty()
  }

}
