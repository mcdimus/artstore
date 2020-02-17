package eu.maksimov.artstore.controller

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import eu.maksimov.artstore.model.Art
import eu.maksimov.artstore.model.ArtSpecification
import eu.maksimov.artstore.model.ArtType.FULL_RANDOM
import eu.maksimov.artstore.service.ArtService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.net.URI
import java.time.Instant
import java.util.UUID
import javax.servlet.http.HttpServletRequest

@ExtendWith(MockitoExtension::class)
class ArtControllerTest {

  @Mock
  private lateinit var artService: ArtService

  @InjectMocks
  private lateinit var artController: ArtController

  @Test
  fun `creates art and returns 201 response with location header`() {
    // given
    val spec = ArtSpecification(type = FULL_RANDOM, name = "NewArt", author = "Picasso", price = BigDecimal(100.0))
    whenever(artService.create(spec)).thenReturn(Art(
        id = UUID.fromString("8677c432-de2c-48ba-af32-d9d49ffc41e8"),
        type = FULL_RANDOM,
        price = 100.0,
        author = "Picasso",
        name = "NewArt",
        content = "base64_string",
        created = Instant.parse("2020-02-17T15:39:05Z")
    ))
    val request = mock<HttpServletRequest> {
      on { scheme } doReturn "http"
      on { serverName } doReturn "localhost"
      on { serverPort } doReturn 8080
      on { requestURI } doReturn "/artstore-api/art"
    }

    // when
    val result = artController.create(spec, request)

    // then
    assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
    assertThat(result.body).isNull()
    assertThat(result.headers.location)
        .isEqualTo(URI("http://localhost:8080/artstore-api/art/8677c432-de2c-48ba-af32-d9d49ffc41e8"))
  }

  @Test
  fun `get returns 200 response and found art`() {
    // given
    val id = UUID.fromString("8677c432-de2c-48ba-af32-d9d49ffc41e8")
    val art = Art(
        id = id,
        type = FULL_RANDOM,
        price = 100.0,
        author = "Picasso",
        name = "NewArt",
        content = "base64_string",
        created = Instant.parse("2020-02-17T15:39:05Z")
    )
    whenever(artService.find(id)).thenReturn(art.copy())

    // when
    val result = artController.get(id)

    // then
    assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(result.body).isNotSameAs(art).isEqualTo(art)
  }

  @Test
  fun `get returns 404 response if art was not found`() {
    // given
    val id = UUID.fromString("8677c432-de2c-48ba-af32-d9d49ffc41e8")
    whenever(artService.find(id)).thenReturn(null)

    // when
    val result = artController.get(id)

    // then
    assertThat(result.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    assertThat(result.body).isNull()
  }

  @Test
  fun `delete calls service's delete method and returns 204 response`() {
    // given
    val id = UUID.fromString("8677c432-de2c-48ba-af32-d9d49ffc41e8")

    // when
    val result = artController.delete(id)

    // then
    assertThat(result.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    assertThat(result.body).isNull()

    verify(artService).delete(id)
  }

}
