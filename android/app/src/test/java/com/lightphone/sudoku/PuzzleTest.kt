package com.lightphone.sudoku

import org.junit.Test
import org.junit.Assert.*
import java.io.File

/**
 * Validates puzzle data format.
 */
class PuzzleTest {

    @Test
    fun testPuzzlesFileExists() {
        val file = File("src/main/res/raw/puzzles.json")
        assertTrue("puzzles.json should exist", file.exists())
    }

    @Test
    fun testPuzzlesFileHasContent() {
        val content = File("src/main/res/raw/puzzles.json").readText()
        assertTrue("puzzles.json should have content", content.length > 1000)
        assertTrue("Should contain easy array", content.contains("\"easy\""))
        assertTrue("Should contain medium array", content.contains("\"medium\""))
        assertTrue("Should contain hard array", content.contains("\"hard\""))
    }
}
