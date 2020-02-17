package eu.maksimov.artstore.service.generator

interface ArtGenerator {
  fun generate(size: Int): ByteArray
}
