package eu.maksimov.artstore.model

import java.math.BigDecimal

data class ArtSpecification(
    val type: ArtType,
    val name: String,
    val author: String,
    val price: BigDecimal
)
