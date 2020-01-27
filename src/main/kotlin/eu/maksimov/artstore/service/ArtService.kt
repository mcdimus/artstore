package eu.maksimov.artstore.service

import eu.maksimov.artstore.dao.ArtRepository
import eu.maksimov.artstore.model.Art
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant
import java.util.*

@Service
class ArtService(
    private val artGenerator: ArtGenerator,
    private val repository: ArtRepository,
    private val clock: Clock
) {

  companion object {
     const val DEFAULT_SIZE = 200
  }

  fun create(name: String, author: String, price: Double): Art {
    return Art(
        name = name,
        author = author,
        price = price,
        content = Base64.getEncoder().encodeToString(artGenerator.generate(DEFAULT_SIZE)),
        created = Instant.now(clock)
    ).also { repository.add(it) }
  }


}
