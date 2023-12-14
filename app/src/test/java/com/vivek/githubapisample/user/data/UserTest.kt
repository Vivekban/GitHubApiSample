package com.vivek.githubapisample.user.data

import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {

    @Test
    fun `test that all properties are correctly set`() {
        val user = User.fake()
        assertEquals(1, user.id)
        assertEquals("Fake", user.name)
        assertEquals("https://avatars3.githubusercontent.com/u/583231?v=4", user.avatarUrl)
        assertEquals("Fake", user.login)
    }

    @Test
    fun `test that displayName return actual name`() {
        val user = User.fake()
        assertEquals("Fake", user.displayName)
    }

    @Test
    fun `test that displayName return Unknown when name is null`() {
        val user = User(id = 1, name = null, avatarUrl = null)
        assertEquals("Unknown", user.displayName)
    }
}