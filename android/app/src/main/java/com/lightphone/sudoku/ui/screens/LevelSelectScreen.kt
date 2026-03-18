package com.lightphone.sudoku.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lightphone.sudoku.model.Difficulty
import com.lightphone.sudoku.ui.components.ActionButton
import com.lightphone.sudoku.ui.theme.DimWhite
import com.lightphone.sudoku.ui.theme.White

@Composable
fun LevelSelectScreen(
    difficulty: Difficulty,
    completedLevels: Set<Int>,
    inProgressLevels: Set<Int>,
    onSelectLevel: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = difficulty.displayName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 3.sp,
            color = DimWhite
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 40 levels in a 5x8 grid
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (row in 0 until 8) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (col in 0 until 5) {
                        val level = row * 5 + col + 1
                        val isCompleted = level in completedLevels
                        val isInProgress = level in inProgressLevels && !isCompleted

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .border(1.dp, if (isCompleted) DimWhite else White)
                                .clickable { onSelectLevel(level) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isCompleted) "✓" else level.toString(),
                                fontSize = if (isCompleted) 18.sp else 14.sp,
                                fontWeight = FontWeight.Light,
                                color = if (isCompleted) DimWhite else White
                            )
                            if (isInProgress) {
                                Text(
                                    text = "*",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = White,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(top = 0.dp, end = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        ActionButton("BACK") { onBack() }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
