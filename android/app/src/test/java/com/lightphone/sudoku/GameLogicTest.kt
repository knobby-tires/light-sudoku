package com.lightphone.sudoku

import com.lightphone.sudoku.domain.SudokuSolver
import org.junit.Test
import org.junit.Assert.*

/**
 * Quick unit tests - no solver tests (too slow for CI).
 */
class GameLogicTest {

    @Test
    fun testValidator_rejectsIncomplete() {
        val cells = IntArray(81) { 0 }
        cells[0] = 1
        assertFalse(SudokuSolver.isValidSolution(cells))
    }

    @Test
    fun testValidator_rejectsRowDuplicate() {
        // Fill with valid-looking data but duplicate in row 0
        val cells = IntArray(81) { ((it % 9) + 1) }
        cells[0] = cells[1]  // Duplicate
        assertFalse(SudokuSolver.isValidSolution(cells))
    }

    @Test
    fun testConflict_detectsRowConflict() {
        val cells = IntArray(81) { 0 }
        cells[0] = 5
        cells[8] = 5
        assertTrue(SudokuSolver.hasConflict(cells, 0, 5))
    }

    @Test
    fun testConflict_detectsColConflict() {
        val cells = IntArray(81) { 0 }
        cells[0] = 5
        cells[72] = 5
        assertTrue(SudokuSolver.hasConflict(cells, 0, 5))
    }

    @Test
    fun testConflict_detectsBoxConflict() {
        val cells = IntArray(81) { 0 }
        cells[0] = 5
        cells[20] = 5
        assertTrue(SudokuSolver.hasConflict(cells, 0, 5))
    }

    @Test
    fun testConflict_noFalsePositive() {
        val cells = IntArray(81) { 0 }
        cells[0] = 5
        assertFalse(SudokuSolver.hasConflict(cells, 40, 5))
    }

    @Test
    fun testConflict_zeroNeverConflicts() {
        val cells = IntArray(81) { 0 }
        cells[0] = 5
        assertFalse(SudokuSolver.hasConflict(cells, 1, 0))
    }
}
