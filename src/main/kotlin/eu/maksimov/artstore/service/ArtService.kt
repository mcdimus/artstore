package eu.maksimov.artstore.service

import eu.maksimov.artstore.dao.ArtRepository
import eu.maksimov.artstore.model.Art
import eu.maksimov.artstore.model.ArtSpecification
import eu.maksimov.artstore.model.ArtType
import eu.maksimov.artstore.service.generator.ArtGeneratorFactory
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant
import java.util.Base64
import java.util.UUID

@Service
class ArtService(
    private val artGeneratorFactory: ArtGeneratorFactory,
    private val repository: ArtRepository,
    private val clock: Clock
) {

  companion object {
    const val DEFAULT_SIZE = 200
  }

  fun create(type: ArtSpecification): Art {
    return this.create(type.type, type.name, type.author, type.price.toDouble())
  }

  fun create(type: ArtType, name: String, author: String, price: Double): Art {
    val artGenerator = artGeneratorFactory.get(type)
    return Art(
        type = type,
        name = name,
        author = author,
        price = price,
        content = Base64.getEncoder().encodeToString(artGenerator.generate(DEFAULT_SIZE)),
        created = Instant.now(clock)
    ).also { repository.add(it) }
  }

  fun find(id: UUID): Art? {
    return repository.find(id)
  }

  fun delete(id: UUID) {
    repository.delete(id)
  }

}
