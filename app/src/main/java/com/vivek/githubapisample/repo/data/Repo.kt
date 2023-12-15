package com.vivek.githubapisample.repo.data

import com.vivek.githubapisample.user.data.User

/**
 * Data object that represents a Repo
 */
data class Repo(

    val name: String,

    val owner: User,

    val description: String?,

    val forks: Int?,

    val updatedAt: String?
) {
    /** Safe description for UI returns empty if null */
    var displayDescription = description ?: ""

    companion object {
        fun fake(description: String? = "This is temporary fake description") = Repo(
            name = "Fake",
            description = description,
            forks = 20,
            updatedAt = null,
            owner = User.fake()
        )
    }
}