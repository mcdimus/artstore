package eu.maksimov.artstore.service

import com.nhaarman.mockitokotlin2.argWhere
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import eu.maksimov.artstore.dao.ArtRepository
import eu.maksimov.artstore.service.ArtService.Companion.DEFAULT_SIZE
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.util.*

@ExtendWith(MockitoExtension::class)
class ArtServiceTest {

  private val NOW = Instant.parse("2020-01-27T17:45:33Z")

  @Mock
  private lateinit var artGenerator: ArtGenerator
  @Mock
  private lateinit var repository: ArtRepository
  @Spy
  private val clock: Clock = Clock.fixed(NOW, ZoneId.systemDefault())

  @InjectMocks
  private lateinit var artService: ArtService

  @Test
  fun `creates new art and store it in repository`() {
    // given
    whenever(artGenerator.generate(DEFAULT_SIZE)).thenReturn(ByteArray(10) { it.toByte() })

    // when
    val result = artService.create("The Art", "John Smith", 200.0)

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
  }

}
