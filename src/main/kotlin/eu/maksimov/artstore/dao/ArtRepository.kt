package eu.maksimov.artstore.dao

import eu.maksimov.artstore.model.Art
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ArtRepository(
    private val arts: MutableMap<UUID, Art> = mutableMapOf()
) {

  fun add(art: Art) {
    if (arts.containsKey(art.id)) {
      throw IllegalArgumentException("cannot add: art with id '${art.id}' already exists")
    }
    arts[art.id] = art
  }

  fun update(art: Art) {
    if (!arts.containsKey(art.id)) {
      throw IllegalArgumentException("cannot update: art with id '${art.id}' does not exist")
    }
    arts[art.id] = art
  }

  fun find(id: UUID): Art? = arts[id]

  fun findAll(): List<Art> = arts.values.toList()

  fun delete(id: UUID) = arts.remove(id)

}
