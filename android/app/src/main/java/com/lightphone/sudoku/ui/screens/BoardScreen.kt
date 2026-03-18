package com.lightphone.sudoku.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lightphone.sudoku.model.Difficulty
import com.lightphone.sudoku.ui.components.ActionButton
import com.lightphone.sudoku.ui.components.SudokuBoard
import com.lightphone.sudoku.ui.theme.DimWhite
import com.lightphone.sudoku.ui.theme.White

@Composable
fun BoardScreen(
    cells: IntArray,
    initialMask: BooleanArray,
    selectedCell: Int,
    difficulty: Difficulty,
    level: Int,
    notes: Map<Int, Set<Int>>,
    hintedCell: Int,
    errorCell: Int,
    onCellClick: (Int) -> Unit,
    onNumberSelect: (Int) -> Unit,
    onBack: () -> Unit,
    onRestart: () -> Unit,
    onUndo: () -> Unit,
    onNotes: () -> Unit,
    onHint: () -> Unit,
    onHintAnimationComplete: () -> Unit,
    onErrorAnimationComplete: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val boardSize = minOf(maxWidth, maxHeight - 80.dp)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.width(boardSize),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActionButton("BACK") { onBack() }
                    ActionButton("UNDO") { onUndo() }
                    ActionButton("NOTE") { onNotes() }
                    ActionButton("HINT") { onHint() }
                    ActionButton("RESET") { onRestart() }
                }

                Text(
                    text = "${difficulty.displayName} $level",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = DimWhite
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.size(boardSize)) {
                SudokuBoard(
                    cells = cells,
                    initialMask = initialMask,
                    selectedCell = selectedCell,
                    hintedCell = hintedCell,
                    errorCell = errorCell,
                    notes = notes,
                    onCellClick = onCellClick,
                    onHintAnimationComplete = onHintAnimationComplete,
                    onErrorAnimationComplete = onErrorAnimationComplete
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.width(boardSize),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (num in 1..9) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .border(1.dp, White)
                            .clickable { onNumberSelect(num) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = num.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
