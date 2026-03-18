package com.lightphone.sudoku.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lightphone.sudoku.ui.theme.Black
import com.lightphone.sudoku.ui.theme.DimWhite
import com.lightphone.sudoku.ui.theme.Gray
import com.lightphone.sudoku.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SudokuBoard(
    cells: IntArray,
    initialMask: BooleanArray,
    selectedCell: Int,
    hintedCell: Int,
    errorCell: Int,
    notes: Map<Int, Set<Int>>,
    onCellClick: (Int) -> Unit,
    onHintAnimationComplete: () -> Unit,
    onErrorAnimationComplete: () -> Unit
) {
    val selectedNumber = if (selectedCell >= 0) cells[selectedCell] else 0

    // Blinking animations - only run when needed
    val blinkAlpha = if (hintedCell >= 0) {
        val infiniteTransition = rememberInfiniteTransition(label = "hint")
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 0.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "blink"
        ).value
    } else 1f

    val errorBlinkAlpha = if (errorCell >= 0) {
        val infiniteTransition = rememberInfiniteTransition(label = "error")
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 0.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(100, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "errorBlink"
        ).value
    } else 1f

    LaunchedEffect(hintedCell) {
        if (hintedCell >= 0) {
            delay(1200)
            onHintAnimationComplete()
        }
    }

    LaunchedEffect(errorCell) {
        if (errorCell >= 0) {
            delay(2000)
            onErrorAnimationComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(2.dp, White)
    ) {
        for (row in 0 until 9) {
            Row(modifier = Modifier.weight(1f)) {
                for (col in 0 until 9) {
                    val index = row * 9 + col
                    val value = cells[index]
                    val isInitial = initialMask[index]
                    val isSelected = index == selectedCell
                    val isHinted = index == hintedCell
                    val isError = index == errorCell
                    val isMatchingNumber = selectedNumber > 0 && value == selectedNumber && index != selectedCell
                    val hasNotes = notes[index]?.isNotEmpty() == true

                    val rightBorder = if (col % 3 == 2 && col != 8) 2.dp else 0.5.dp
                    val bottomBorder = if (row % 3 == 2 && row != 8) 2.dp else 0.5.dp

                    val bgColor = when {
                        isError -> White.copy(alpha = errorBlinkAlpha)
                        isHinted -> White.copy(alpha = blinkAlpha)
                        isMatchingNumber -> Gray
                        isSelected -> Gray
                        else -> Black
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(bgColor)
                            .drawBorders(rightBorder, bottomBorder)
                            .clickable { onCellClick(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (value != 0) {
                            Text(
                                text = value.toString(),
                                fontSize = 14.sp,
                                fontWeight = if (isInitial) FontWeight.Medium else FontWeight.Light,
                                color = if (isError || isHinted) Black else if (isInitial) White else DimWhite
                            )
                        }
                        if (hasNotes) {
                            Text(
                                text = "*",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isError || isHinted) Black else White,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 1.dp, end = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Modifier.drawBorders(right: Dp, bottom: Dp): Modifier {
    return this.drawWithContent {
        drawContent()
        drawRect(
            color = Color.White,
            topLeft = Offset(size.width - right.toPx(), 0f),
            size = Size(right.toPx(), size.height)
        )
        drawRect(
            color = Color.White,
            topLeft = Offset(0f, size.height - bottom.toPx()),
            size = Size(size.width, bottom.toPx())
        )
    }
}
