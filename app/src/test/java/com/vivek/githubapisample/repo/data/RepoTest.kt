package com.vivek.githubapisample.repo.data

import com.vivek.githubapisample.user.data.User
import org.junit.Assert.assertEquals
import org.junit.Test

class RepoTest {

    @Test
    fun `test that all properties are correctly set`() {
        val repo = Repo.fake()
        assertEquals("Fake", repo.name)
        assertEquals("This is temporary fake description", repo.description)
        assertEquals(20, repo.forks)
        assertEquals(User.fake(), repo.owner)

    }

}