package com.themrfill.chiptest.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsTests {
    // no tests for empty strings, as we know the data shouldn't contain that
    @Test
    fun caps() {
        assertEquals("Spaniel", "spaniel".caps())
        assertEquals("Spaniel", "Spaniel".caps())
        assertEquals("S", "s".caps())
        assertEquals("TERRIER", "TERRIER".caps())
    }

    @Test
    fun toBreedName() {
        assertEquals("spaniel", "Cocker Spaniel".toBreedName())
        assertEquals("spaniel", "Spaniel".toBreedName())
    }

    @Test
    fun toSubBreedName() {
        assertEquals("cocker", "Cocker Spaniel".toSubBreedName())
        assertEquals("", "Spaniel".toSubBreedName())
    }
}