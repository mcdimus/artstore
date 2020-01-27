package eu.maksimov.artstore.model

import java.time.LocalDateTime
import java.util.*

data class Art(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val author: String,
    val price: Double,
    val content: String,
    val created: LocalDateTime
)
