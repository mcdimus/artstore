package eu.maksimov.artstore.service.generator

import eu.maksimov.artstore.model.ArtType
import org.springframework.stereotype.Component

@Component
class ArtGeneratorFactory(
    private val artGenerators: Map<String, ArtGenerator>
) {

  fun get(type: ArtType): ArtGenerator {
    return artGenerators[type.generatorClass.simpleName?.decapitalize()]
        ?: throw IllegalArgumentException("unsupported art type: $type")
  }

}
