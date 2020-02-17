package eu.maksimov.artstore.controller

import eu.maksimov.artstore.model.Art
import eu.maksimov.artstore.model.ArtSpecification
import eu.maksimov.artstore.service.ArtService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = ["/art"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ArtController(
    private val artService: ArtService
) {

  @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
  fun create(@RequestBody artSpec: ArtSpecification, request: HttpServletRequest): ResponseEntity<Void> {
    val createdArt = artService.create(artSpec)

    val location = ServletUriComponentsBuilder.fromRequest(request).path("/{id}")
        .buildAndExpand(createdArt.id).toUri()
    return ResponseEntity.created(location).build<Void>()
  }

  @GetMapping(path = ["/{id}"])
  fun get(@PathVariable id: UUID): ResponseEntity<Art> {
    return artService.find(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build<Art>()
  }

  @DeleteMapping(path = ["/{id}"])
  fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
    artService.delete(id)
    return ResponseEntity.noContent().build()
  }
}
