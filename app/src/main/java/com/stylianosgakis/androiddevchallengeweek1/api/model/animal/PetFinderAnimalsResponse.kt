package com.stylianosgakis.androiddevchallengeweek1.api.model.animal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetFinderAnimalsResponse(
    val animals: List<Animal>,
    val pagination: Pagination,
)

@Serializable
data class Pagination(
    @SerialName("count_per_page")
    val countPerPage: Int,
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("_links")
    val links: Links,
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("total_pages")
    val totalPages: Int,
) {
    @Serializable
    data class Links(
        val next: Next,
        val previous: Previous? = null,
    ) {
        @Serializable
        data class Next(
            val href: String,
        )

        @Serializable
        data class Previous(
            val href: String,
        )
    }
}
