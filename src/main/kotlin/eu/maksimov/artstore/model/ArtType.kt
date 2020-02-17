package eu.maksimov.artstore.model

import eu.maksimov.artstore.service.generator.ArtGenerator
import eu.maksimov.artstore.service.generator.FullRandomArtGenerator
import eu.maksimov.artstore.service.generator.VerticalRandomArtGenerator
import kotlin.reflect.KClass

enum class ArtType(val generatorClass: KClass<out ArtGenerator>) {
  FULL_RANDOM(FullRandomArtGenerator::class),
  HORIZONTAL_RANDOM(VerticalRandomArtGenerator::class),
  VERTICAL_RANDOM(VerticalRandomArtGenerator::class)
}
