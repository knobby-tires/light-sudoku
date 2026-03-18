package com.lightphone.sudoku.model

data class GameState(
    val cells: IntArray = IntArray(81),
    val initialMask: BooleanArray = BooleanArray(81),
    val originalCells: IntArray = IntArray(81),
    val selectedCell: Int = -1,
    val undoStack: List<Pair<Int, Int>> = emptyList(),
    val notes: Map<Int, Set<Int>> = emptyMap(),
    val hintedCell: Int = -1,
    val errorCell: Int = -1,
    val difficulty: Difficulty = Difficulty.EASY,
    val level: Int = 1
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GameState) return false
        return cells.contentEquals(other.cells) &&
                initialMask.contentEquals(other.initialMask) &&
                originalCells.contentEquals(other.originalCells) &&
                selectedCell == other.selectedCell &&
                undoStack == other.undoStack &&
                notes == other.notes &&
                hintedCell == other.hintedCell &&
                errorCell == other.errorCell &&
                difficulty == other.difficulty &&
                level == other.level
    }

    override fun hashCode(): Int {
        var result = cells.contentHashCode()
        result = 31 * result + initialMask.contentHashCode()
        result = 31 * result + originalCells.contentHashCode()
        result = 31 * result + selectedCell
        result = 31 * result + undoStack.hashCode()
        result = 31 * result + notes.hashCode()
        result = 31 * result + hintedCell
        result = 31 * result + errorCell
        result = 31 * result + difficulty.hashCode()
        result = 31 * result + level
        return result
    }
}
