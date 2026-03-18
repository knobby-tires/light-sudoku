package com.lightphone.sudoku.data

import android.content.Context
import com.lightphone.sudoku.model.Difficulty

class GameStorage(private val context: Context) {

    companion object {
        private const val PREFS = "sudoku_prefs"
        private const val COMPLETED_PREFS = "sudoku_completed"
        private const val NOTES_PREFS = "sudoku_notes"
    }

    private fun key(difficulty: Difficulty, level: Int) = "${difficulty.name}_$level"

    fun saveGame(difficulty: Difficulty, level: Int, cells: IntArray) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(key(difficulty, level), cells.joinToString(","))
            .apply()
    }

    fun loadGame(difficulty: Difficulty, level: Int): IntArray? {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val cellsStr = prefs.getString(key(difficulty, level), null) ?: return null
        val cells = cellsStr.split(",").map { it.toIntOrNull() ?: 0 }.toIntArray()
        if (cells.size != 81) return null
        return cells
    }

    fun clearGame(difficulty: Difficulty, level: Int) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().remove(key(difficulty, level)).apply()
    }

    fun markCompleted(difficulty: Difficulty, level: Int) {
        val prefs = context.getSharedPreferences(COMPLETED_PREFS, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(key(difficulty, level), true).apply()
    }

    fun isCompleted(difficulty: Difficulty, level: Int): Boolean {
        val prefs = context.getSharedPreferences(COMPLETED_PREFS, Context.MODE_PRIVATE)
        return prefs.getBoolean(key(difficulty, level), false)
    }

    fun getCompletedLevels(difficulty: Difficulty): Set<Int> {
        val prefs = context.getSharedPreferences(COMPLETED_PREFS, Context.MODE_PRIVATE)
        return (1..40).filter { prefs.getBoolean(key(difficulty, it), false) }.toSet()
    }

    fun getInProgressLevels(difficulty: Difficulty): Set<Int> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return (1..40).filter { prefs.contains(key(difficulty, it)) }.toSet()
    }

    fun saveNotes(difficulty: Difficulty, level: Int, notes: Map<Int, Set<Int>>) {
        val prefs = context.getSharedPreferences(NOTES_PREFS, Context.MODE_PRIVATE)
        val notesStr = notes.entries
            .filter { it.value.isNotEmpty() }
            .joinToString(";") { (cell, nums) -> "$cell:${nums.joinToString(",")}" }
        prefs.edit().putString(key(difficulty, level), notesStr).apply()
    }

    fun loadNotes(difficulty: Difficulty, level: Int): Map<Int, Set<Int>> {
        val prefs = context.getSharedPreferences(NOTES_PREFS, Context.MODE_PRIVATE)
        val notesStr = prefs.getString(key(difficulty, level), null) ?: return emptyMap()
        if (notesStr.isEmpty()) return emptyMap()
        return notesStr.split(";").associate { entry ->
            val (cell, nums) = entry.split(":")
            cell.toInt() to nums.split(",").map { it.toInt() }.toSet()
        }
    }

    fun clearNotes(difficulty: Difficulty, level: Int) {
        val prefs = context.getSharedPreferences(NOTES_PREFS, Context.MODE_PRIVATE)
        prefs.edit().remove(key(difficulty, level)).apply()
    }
}
