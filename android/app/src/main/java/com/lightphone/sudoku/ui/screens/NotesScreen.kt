package com.lightphone.sudoku.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lightphone.sudoku.ui.components.ActionButton
import com.lightphone.sudoku.ui.theme.Black
import com.lightphone.sudoku.ui.theme.DimWhite
import com.lightphone.sudoku.ui.theme.White

@Composable
fun NotesScreen(
    cellIndex: Int,
    currentNotes: Set<Int>,
    onToggleNote: (Int) -> Unit,
    onBack: () -> Unit
) {
    val row = cellIndex / 9 + 1
    val col = cellIndex % 9 + 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "NOTES FOR CELL",
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 2.sp,
            color = DimWhite
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "ROW $row, COL $col",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = DimWhite
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (gridRow in 0 until 3) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (gridCol in 0 until 3) {
                        val num = gridRow * 3 + gridCol + 1
                        val isSelected = num in currentNotes

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(if (isSelected) White else Black)
                                .border(1.dp, White)
                                .clickable { onToggleNote(num) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = num.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light,
                                color = if (isSelected) Black else White
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        ActionButton("DONE") { onBack() }
    }
}
