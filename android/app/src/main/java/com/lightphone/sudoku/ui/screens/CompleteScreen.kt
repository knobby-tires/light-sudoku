package com.lightphone.sudoku.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.lightphone.sudoku.ui.components.MenuButton
import com.lightphone.sudoku.ui.theme.DimWhite
import com.lightphone.sudoku.ui.theme.White

@Composable
fun CompleteScreen(
    difficulty: Difficulty,
    level: Int,
    hasNextLevel: Boolean,
    onNextLevel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "✓",
            fontSize = 48.sp,
            fontWeight = FontWeight.Light,
            color = White
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${difficulty.displayName} $level",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 3.sp,
            color = White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "COMPLETE",
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 2.sp,
            color = DimWhite
        )

        Spacer(modifier = Modifier.height(48.dp))

        MenuButton(if (hasNextLevel) "NEXT LEVEL" else "DONE") { onNextLevel() }
    }
}
