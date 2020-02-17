package eu.maksimov.artstore.service

import com.nhaarman.mockitokotlin2.argWhere
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import eu.maksimov.artstore.dao.ArtRepository
import eu.maksimov.artstore.model.ArtType
import eu.maksimov.artstore.model.ArtType.FULL_RANDOM
import eu.maksimov.artstore.service.ArtService.Companion.DEFAULT_SIZE
import eu.maksimov.artstore.service.generator.ArtGeneratorFactory
import eu.maksimov.artstore.service.generator.FullRandomArtGenerator
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.util.Base64

class ArtServiceTest {

  private val NOW = Instant.parse("2020-01-27T17:45:33Z")

  private lateinit var artGeneratorFactory: ArtGeneratorFactory
  private lateinit var artGenerator: FullRandomArtGenerator
  private lateinit var repository: ArtRepository

  private lateinit var artService: ArtService

  @BeforeEach
  fun setUp() {
    artGeneratorFactory = mock()
    artGenerator = mock()
    repository = mock()
    artService = ArtService(
        artGeneratorFactory,
        repository,
        Clock.fixed(NOW, ZoneId.systemDefault())
    )
  }

  @Test
  fun `creates new art and stores it in repository`() {
    // given
    whenever(artGeneratorFactory.get(FULL_RANDOM)).thenReturn(artGenerator)
    whenever(artGenerator.generate(DEFAULT_SIZE)).thenReturn(ByteArray(10) { it.toByte() })

    // when
    val result = artService.create(ArtType.FULL_RANDOM, "The Art", "John Smith", 200.0)

    // then
    assertSoftly {
      it.assertThat(result.id).isNotNull()
      it.assertThat(result.name).isEqualTo("The Art")
      it.assertThat(result.author).isEqualTo("John Smith")
      it.assertThat(result.price).isEqualTo(200.0)
      it.assertThat(result.content).isEqualTo(
          Base64.getEncoder().encodeToString(ByteArray(10) { i -> i.toByte() })
      )
      it.assertThat(result.created).isEqualTo(NOW)
      it.assertAll()
    }

    verify(repository).add(argWhere { it.name == "The Art" })
    verifyNoMoreInteractions(repository)
  }

}
