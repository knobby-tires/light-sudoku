package com.lightphone.sudoku.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.lightphone.sudoku.data.GameStorage
import com.lightphone.sudoku.data.PuzzleRepository
import com.lightphone.sudoku.domain.SudokuSolver
import com.lightphone.sudoku.model.Difficulty
import com.lightphone.sudoku.model.GameState
import com.lightphone.sudoku.model.Screen

class SudokuViewModel(
    private val puzzleRepository: PuzzleRepository,
    private val gameStorage: GameStorage
) {

    var screen by mutableStateOf(Screen.HOME)
        private set

    var gameState by mutableStateOf(GameState())
        private set

    var selectedDifficulty by mutableStateOf(Difficulty.EASY)
        private set

    init {
        puzzleRepository.loadPuzzles()
    }

    fun navigateTo(newScreen: Screen) {
        screen = newScreen
    }

    fun selectDifficulty(difficulty: Difficulty) {
        selectedDifficulty = difficulty
        screen = Screen.LEVEL_SELECT
    }

    fun getCompletedLevels(): Set<Int> = gameStorage.getCompletedLevels(selectedDifficulty)

    fun getInProgressLevels(): Set<Int> = gameStorage.getInProgressLevels(selectedDifficulty)

    fun startLevel(level: Int) {
        val puzzleStr = puzzleRepository.getPuzzle(selectedDifficulty, level)
        val (baseCells, initialMask) = puzzleRepository.parsePuzzle(puzzleStr)

        val savedCells = gameStorage.loadGame(selectedDifficulty, level)
        val savedNotes = if (savedCells != null) {
            gameStorage.loadNotes(selectedDifficulty, level)
        } else {
            emptyMap()
        }

        gameState = GameState(
            cells = savedCells ?: baseCells.copyOf(),
            initialMask = initialMask,
            originalCells = baseCells.copyOf(),
            selectedCell = -1,
            undoStack = emptyList(),
            notes = savedNotes,
            hintedCell = -1,
            errorCell = -1,
            difficulty = selectedDifficulty,
            level = level
        )
        screen = Screen.BOARD
    }

    fun onCellClick(index: Int) {
        gameState = gameState.copy(
            selectedCell = if (gameState.selectedCell == index) -1 else index
        )
    }

    fun onNumberSelect(number: Int) {
        val state = gameState
        if (state.selectedCell < 0 || state.initialMask[state.selectedCell]) return

        val oldValue = state.cells[state.selectedCell]
        val newCells = state.cells.copyOf().also { it[state.selectedCell] = number }
        val newNotes = if (number != 0) state.notes - state.selectedCell else state.notes
        val hasError = SudokuSolver.hasConflict(newCells, state.selectedCell, number)

        gameState = state.copy(
            cells = newCells,
            undoStack = state.undoStack + Pair(state.selectedCell, oldValue),
            notes = newNotes,
            errorCell = if (hasError) state.selectedCell else -1
        )

        saveCurrentGame()

        // Check completion
        if (newCells.all { it != 0 } && SudokuSolver.isValidSolution(newCells)) {
            gameStorage.markCompleted(state.difficulty, state.level)
            screen = Screen.COMPLETE
        }
    }

    fun undo() {
        val state = gameState
        if (state.undoStack.isEmpty()) return

        val (index, oldValue) = state.undoStack.last()
        val newCells = state.cells.copyOf().also { it[index] = oldValue }

        gameState = state.copy(
            cells = newCells,
            undoStack = state.undoStack.dropLast(1)
        )
        saveCurrentGame()
    }

    fun restartGame() {
        gameState = gameState.copy(
            cells = gameState.originalCells.copyOf(),
            selectedCell = -1,
            undoStack = emptyList(),
            notes = emptyMap(),
            errorCell = -1
        )
        saveCurrentGame()
        screen = Screen.BOARD
    }

    fun openNotes() {
        val state = gameState
        if (state.selectedCell >= 0 && !state.initialMask[state.selectedCell]) {
            screen = Screen.NOTES
        }
    }

    fun toggleNote(number: Int) {
        val state = gameState
        val cellIndex = state.selectedCell
        val currentNotes = state.notes[cellIndex] ?: emptySet()

        val newCellNotes = if (number in currentNotes) {
            currentNotes - number
        } else {
            currentNotes + number
        }

        gameState = state.copy(
            notes = state.notes + (cellIndex to newCellNotes)
        )
    }

    fun closeNotes() {
        gameStorage.saveNotes(gameState.difficulty, gameState.level, gameState.notes)
        screen = Screen.BOARD
    }

    fun requestHint() {
        val state = gameState
        val solution = SudokuSolver.solve(state.originalCells.copyOf()) ?: return

        val emptyCells = state.cells.indices.filter { state.cells[it] == 0 && solution[it] != 0 }

        if (emptyCells.isNotEmpty()) {
            val hintIndex = emptyCells.random()
            val newCells = state.cells.copyOf().also { it[hintIndex] = solution[hintIndex] }

            gameState = state.copy(
                cells = newCells,
                undoStack = state.undoStack + Pair(hintIndex, state.cells[hintIndex]),
                notes = state.notes - hintIndex,
                hintedCell = hintIndex
            )
            saveCurrentGame()

            // Check completion
            if (newCells.all { it != 0 } && SudokuSolver.isValidSolution(newCells)) {
                gameStorage.markCompleted(state.difficulty, state.level)
                screen = Screen.COMPLETE
            }
        } else if (state.cells.all { it != 0 }) {
            // Board full but has errors - highlight a wrong cell
            val wrongCell = state.cells.indices.firstOrNull {
                !state.initialMask[it] && state.cells[it] != solution[it]
            }
            if (wrongCell != null) {
                gameState = state.copy(errorCell = wrongCell)
            }
        }
    }

    fun clearHintAnimation() {
        gameState = gameState.copy(hintedCell = -1)
    }

    fun clearErrorAnimation() {
        gameState = gameState.copy(errorCell = -1)
    }

    fun goToNextLevel() {
        val state = gameState
        if (state.level < 40) {
            startLevel(state.level + 1)
        } else {
            screen = Screen.LEVEL_SELECT
        }
    }

    private fun saveCurrentGame() {
        val state = gameState
        gameStorage.saveGame(state.difficulty, state.level, state.cells)
        gameStorage.saveNotes(state.difficulty, state.level, state.notes)
    }
}
