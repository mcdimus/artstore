package eu.maksimov.artstore.dao

import eu.maksimov.artstore.model.Art
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class ArtRepositoryTest {

  @Test
  fun `adds new Art successfully`() {
    // given
    val arts = mutableMapOf<UUID, Art>()
    val artRepository = ArtRepository(arts)

    val newArtId = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val newArt = Art(
        id = newArtId,
        name = "New Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )

    // when
    artRepository.add(newArt)

    // then
    assertThat(arts).containsOnly(
        entry(newArtId, newArt)
    )
  }

  @Test
  fun `throws exception than adding Art with same id`() {
    // given
    val artId = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val art = Art(
        id = artId,
        name = "Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )

    val arts = mutableMapOf<UUID, Art>(
        artId to art
    )
    val artRepository = ArtRepository(arts)

    // when
    val result = catchThrowable { artRepository.add(art.copy(name = "New Art")) }

    // then
    assertThat(result).isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("cannot add: art with id 'a7c7a9f3-f91e-49dd-9a52-61ca282cecdc' already exists")
    assertThat(arts).containsOnly(
        entry(artId, art)
    )
  }

  @Test
  fun `updates Art successfully`() {
    // given
    val artId = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val art = Art(
        id = artId,
        name = "Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )

    val arts = mutableMapOf<UUID, Art>(
        artId to art
    )
    val artRepository = ArtRepository(arts)

    // when
    artRepository.update(art.copy(name = "Updated Art", price = 200.0))

    // then
    assertThat(arts).containsOnly(
        entry(artId, Art(
            id = artId,
            name = "Updated Art",
            author = "John Smith",
            price = 200.0,
            content = "",
            created = art.created
        ))
    )
  }

  @Test
  fun `throws exception than updating Art that does not exist`() {
    // given
    val artId = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val art = Art(
        id = artId,
        name = "Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )

    val arts = mutableMapOf<UUID, Art>()
    val artRepository = ArtRepository(arts)

    // when
    val result = catchThrowable { artRepository.update(art.copy(name = "New Art")) }

    // then
    assertThat(result).isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("cannot update: art with id 'a7c7a9f3-f91e-49dd-9a52-61ca282cecdc' does not exist")
    assertThat(arts).isEmpty()
  }

  @Test
  fun `find returns Art found by id leaving repository unchanged`() {
    // given
    val artId = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val art = Art(
        id = artId,
        name = "Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )

    val arts = mutableMapOf<UUID, Art>(
        artId to art
    )
    val artRepository = ArtRepository(arts)

    // when
    val result = artRepository.find(artId)

    // then
    assertThat(result).isEqualTo(art)
    assertThat(arts).containsOnly(
        entry(artId, art)
    )
  }

  @Test
  fun `find returns null if did not find Art by id leaving repository unchanged`() {
    // given
    val artId = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val art = Art(
        id = artId,
        name = "Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )

    val arts = mutableMapOf<UUID, Art>(
        artId to art
    )
    val artRepository = ArtRepository(arts)

    // when
    val result = artRepository.find(UUID.fromString("00000000-f91e-49dd-9a52-61ca28200000"))

    // then
    assertThat(result).isNull()
    assertThat(arts).containsOnly(
        entry(artId, art)
    )
  }

  @Test
  fun `findAll returns empty list if repository is empty`() {
    // given
    val arts = mutableMapOf<UUID, Art>()
    val artRepository = ArtRepository(arts)

    // when
    val result = artRepository.findAll()

    // then
    assertThat(result).isEmpty()
    assertThat(arts).isEmpty()
  }

  @Test
  fun `findAll returns list of arts leaving repository unchanged`() {
    // given
    val artId1 = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val artId2 = UUID.fromString("d4cb620d-9f68-4f92-987f-5ba119f27bdf")
    val art1 = Art(
        id = artId1,
        name = "Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )
    val art2 = Art(
        id = artId1,
        name = "Art 2",
        author = "John Doe",
        price = 111.0,
        content = "",
        created = LocalDateTime.now()
    )

    val arts = mutableMapOf<UUID, Art>(
        artId1 to art1,
        artId2 to art2
    )
    val artRepository = ArtRepository(arts)

    // when
    val result = artRepository.findAll()

    // then
    assertThat(result).containsOnly(art1, art2)
    assertThat(arts).containsOnly(
        entry(artId1, art1),
        entry(artId2, art2)
    )
  }

  @Test
  fun `delete removes and returns Art from repository by id`() {
    // given
    val artId1 = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val artId2 = UUID.fromString("d4cb620d-9f68-4f92-987f-5ba119f27bdf")
    val art1 = Art(
        id = artId1,
        name = "Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )
    val art2 = Art(
        id = artId1,
        name = "Art 2",
        author = "John Doe",
        price = 111.0,
        content = "",
        created = LocalDateTime.now()
    )

    val arts = mutableMapOf<UUID, Art>(
        artId1 to art1,
        artId2 to art2
    )
    val artRepository = ArtRepository(arts)

    // when
    val result = artRepository.delete(artId1)

    // then
    assertThat(result).isEqualTo(art1)
    assertThat(arts).containsOnly(
        entry(artId2, art2)
    )
  }

  @Test
  fun `delete returns null and leaves repository unchanged if Art not found by id`() {
    // given
    val artId = UUID.fromString("a7c7a9f3-f91e-49dd-9a52-61ca282cecdc")
    val art = Art(
        id = artId,
        name = "Art 1",
        author = "John Smith",
        price = 100.0,
        content = "",
        created = LocalDateTime.now()
    )

    val arts = mutableMapOf<UUID, Art>(
        artId to art
    )
    val artRepository = ArtRepository(arts)

    // when
    val result = artRepository.delete(UUID.fromString("d4cb620d-9f68-4f92-987f-5ba119f27bdf"))

    // then
    assertThat(result).isNull()
    assertThat(arts).containsOnly(
        entry(artId, art)
    )
  }

}
