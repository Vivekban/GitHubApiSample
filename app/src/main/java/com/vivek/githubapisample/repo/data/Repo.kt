package com.vivek.githubapisample.repo.data

import androidx.compose.runtime.Immutable
import com.vivek.githubapisample.user.data.User

/**
 * A business data object that represents a Repo this object wil be available in domain/presentation layer
 * It act act as bridge between data layer and domain layer
 */
@Immutable
data class Repo(

    val name: String,

    val owner: User,

    val description: String?,

    val forks: Int?,

    val updatedAt: String?
) {
    /** Safe description for UI, returns empty if null */
    var displayDescription = description ?: ""

    companion object {

        /**
         * Creates a fake repo object for preview and testing purpose
         */
        fun fake(description: String? = "This is temporary fake description") = Repo(
            name = "Fake",
            description = description,
            forks = 20,
            updatedAt = null,
            owner = User.fake()
        )
    }
}