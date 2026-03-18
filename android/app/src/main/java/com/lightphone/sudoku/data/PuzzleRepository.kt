package com.lightphone.sudoku.data

import android.content.Context
import com.lightphone.sudoku.R
import com.lightphone.sudoku.model.Difficulty
import org.json.JSONObject

class PuzzleRepository(private val context: Context) {

    private var puzzles: Map<Difficulty, List<String>>? = null

    fun loadPuzzles() {
        if (puzzles != null) return

        val json = context.resources.openRawResource(R.raw.puzzles)
            .bufferedReader().use { it.readText() }
        val obj = JSONObject(json)

        puzzles = mapOf(
            Difficulty.EASY to obj.getJSONArray("easy").let { arr ->
                (0 until arr.length()).map { arr.getString(it) }
            },
            Difficulty.MEDIUM to obj.getJSONArray("medium").let { arr ->
                (0 until arr.length()).map { arr.getString(it) }
            },
            Difficulty.HARD to obj.getJSONArray("hard").let { arr ->
                (0 until arr.length()).map { arr.getString(it) }
            }
        )
    }

    fun getPuzzle(difficulty: Difficulty, level: Int): String {
        return puzzles?.get(difficulty)?.get(level - 1)
            ?: throw IllegalStateException("Puzzles not loaded")
    }

    fun parsePuzzle(puzzle: String): Pair<IntArray, BooleanArray> {
        val cells = IntArray(81) { puzzle[it].digitToInt() }
        val initialMask = BooleanArray(81) { cells[it] != 0 }
        return Pair(cells, initialMask)
    }
}
