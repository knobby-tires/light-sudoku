package com.lightphone.sudoku.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lightphone.sudoku.model.Screen
import com.lightphone.sudoku.ui.components.ConfirmDialog
import com.lightphone.sudoku.ui.screens.BoardScreen
import com.lightphone.sudoku.ui.screens.CompleteScreen
import com.lightphone.sudoku.ui.screens.DifficultyScreen
import com.lightphone.sudoku.ui.screens.HomeScreen
import com.lightphone.sudoku.ui.screens.LevelSelectScreen
import com.lightphone.sudoku.ui.screens.NotesScreen
import com.lightphone.sudoku.ui.theme.Black
import com.lightphone.sudoku.ui.theme.SudokuTypography

@Composable
fun SudokuApp(viewModel: SudokuViewModel) {
    MaterialTheme(typography = SudokuTypography) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black)
        ) {
            when (viewModel.screen) {
                Screen.HOME -> HomeScreen(
                    onLevels = { viewModel.navigateTo(Screen.DIFFICULTY) }
                )

                Screen.DIFFICULTY -> DifficultyScreen(
                    onSelect = viewModel::selectDifficulty,
                    onBack = { viewModel.navigateTo(Screen.HOME) }
                )

                Screen.LEVEL_SELECT -> LevelSelectScreen(
                    difficulty = viewModel.selectedDifficulty,
                    completedLevels = viewModel.getCompletedLevels(),
                    inProgressLevels = viewModel.getInProgressLevels(),
                    onSelectLevel = viewModel::startLevel,
                    onBack = { viewModel.navigateTo(Screen.DIFFICULTY) }
                )

                Screen.BOARD -> {
                    val state = viewModel.gameState
                    BoardScreen(
                        cells = state.cells,
                        initialMask = state.initialMask,
                        selectedCell = state.selectedCell,
                        difficulty = state.difficulty,
                        level = state.level,
                        notes = state.notes,
                        hintedCell = state.hintedCell,
                        errorCell = state.errorCell,
                        onCellClick = viewModel::onCellClick,
                        onNumberSelect = viewModel::onNumberSelect,
                        onBack = { viewModel.navigateTo(Screen.LEVEL_SELECT) },
                        onRestart = { viewModel.navigateTo(Screen.CONFIRM_RESTART) },
                        onUndo = viewModel::undo,
                        onNotes = viewModel::openNotes,
                        onHint = viewModel::requestHint,
                        onHintAnimationComplete = viewModel::clearHintAnimation,
                        onErrorAnimationComplete = viewModel::clearErrorAnimation
                    )
                }

                Screen.CONFIRM_RESTART -> ConfirmDialog(
                    message = "RESTART GAME?",
                    onConfirm = viewModel::restartGame,
                    onCancel = { viewModel.navigateTo(Screen.BOARD) }
                )

                Screen.COMPLETE -> {
                    val state = viewModel.gameState
                    CompleteScreen(
                        difficulty = state.difficulty,
                        level = state.level,
                        hasNextLevel = state.level < 40,
                        onNextLevel = viewModel::goToNextLevel
                    )
                }

                Screen.NOTES -> {
                    val state = viewModel.gameState
                    NotesScreen(
                        cellIndex = state.selectedCell,
                        currentNotes = state.notes[state.selectedCell] ?: emptySet(),
                        onToggleNote = viewModel::toggleNote,
                        onBack = viewModel::closeNotes
                    )
                }
            }
        }
    }
}
