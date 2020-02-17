package eu.maksimov.artstore.service.generator

import eu.maksimov.artstore.model.ArtType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ArtGeneratorFactoryTest {

  private lateinit var artGeneratorFactory: ArtGeneratorFactory

  @BeforeEach
  fun setUp() {
    artGeneratorFactory = ArtGeneratorFactory(mapOf(
        "fullRandomArtGenerator" to FullRandomArtGenerator(),
        "horizontalRandomArtGenerator" to HorizontalRandomArtGenerator(),
        "verticalRandomArtGenerator" to VerticalRandomArtGenerator()
    ))
  }

  @ParameterizedTest
  @EnumSource(ArtType::class)
  fun `gets correct generator instance for every art type`(type: ArtType) {
    // when
    val result = artGeneratorFactory.get(type)

    // then
    assertThat(result).isInstanceOf(type.generatorClass.java)
  }

}
