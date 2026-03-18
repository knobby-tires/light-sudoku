package com.lightphone.sudoku.domain

object SudokuSolver {

    fun solve(cells: IntArray): IntArray? {
        val copy = cells.copyOf()
        return if (solveRecursive(copy)) copy else null
    }

    private fun solveRecursive(cells: IntArray): Boolean {
        val empty = cells.indexOfFirst { it == 0 }
        if (empty == -1) return true

        val row = empty / 9
        val col = empty % 9

        for (num in 1..9) {
            if (isValidPlacement(cells, row, col, num)) {
                cells[empty] = num
                if (solveRecursive(cells)) return true
                cells[empty] = 0
            }
        }
        return false
    }

    private fun isValidPlacement(cells: IntArray, row: Int, col: Int, num: Int): Boolean {
        // Check row
        for (c in 0 until 9) {
            if (cells[row * 9 + c] == num) return false
        }
        // Check column
        for (r in 0 until 9) {
            if (cells[r * 9 + col] == num) return false
        }
        // Check 3x3 box
        val boxRow = (row / 3) * 3
        val boxCol = (col / 3) * 3
        for (r in boxRow until boxRow + 3) {
            for (c in boxCol until boxCol + 3) {
                if (cells[r * 9 + c] == num) return false
            }
        }
        return true
    }

    fun isValidSolution(cells: IntArray): Boolean {
        // Check rows
        for (row in 0 until 9) {
            val seen = mutableSetOf<Int>()
            for (col in 0 until 9) {
                val v = cells[row * 9 + col]
                if (v == 0 || v in seen) return false
                seen.add(v)
            }
        }
        // Check columns
        for (col in 0 until 9) {
            val seen = mutableSetOf<Int>()
            for (row in 0 until 9) {
                val v = cells[row * 9 + col]
                if (v == 0 || v in seen) return false
                seen.add(v)
            }
        }
        // Check boxes
        for (boxRow in 0 until 3) {
            for (boxCol in 0 until 3) {
                val seen = mutableSetOf<Int>()
                for (r in 0 until 3) {
                    for (c in 0 until 3) {
                        val v = cells[(boxRow * 3 + r) * 9 + (boxCol * 3 + c)]
                        if (v == 0 || v in seen) return false
                        seen.add(v)
                    }
                }
            }
        }
        return true
    }

    fun hasConflict(cells: IntArray, index: Int, number: Int): Boolean {
        if (number == 0) return false

        val row = index / 9
        val col = index % 9

        // Check row
        for (c in 0 until 9) {
            if (c != col && cells[row * 9 + c] == number) return true
        }
        // Check column
        for (r in 0 until 9) {
            if (r != row && cells[r * 9 + col] == number) return true
        }
        // Check box
        val boxRow = (row / 3) * 3
        val boxCol = (col / 3) * 3
        for (r in boxRow until boxRow + 3) {
            for (c in boxCol until boxCol + 3) {
                if ((r != row || c != col) && cells[r * 9 + c] == number) return true
            }
        }
        return false
    }
}
