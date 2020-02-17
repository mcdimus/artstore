package eu.maksimov.artstore.model

import java.time.Instant
import java.util.*

data class Art(
    val id: UUID = UUID.randomUUID(),
    val type: ArtType,
    val name: String,
    val author: String,
    val price: Double,
    val content: String,
    val created: Instant
)
