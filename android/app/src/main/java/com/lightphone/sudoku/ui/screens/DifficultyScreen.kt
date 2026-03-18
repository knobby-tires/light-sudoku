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
import com.lightphone.sudoku.ui.components.ActionButton
import com.lightphone.sudoku.ui.components.MenuButton
import com.lightphone.sudoku.ui.theme.DimWhite

@Composable
fun DifficultyScreen(
    onSelect: (Difficulty) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "DIFFICULTY",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 4.sp,
            color = DimWhite
        )

        Spacer(modifier = Modifier.height(32.dp))

        MenuButton("EASY") { onSelect(Difficulty.EASY) }
        Spacer(modifier = Modifier.height(16.dp))
        MenuButton("MEDIUM") { onSelect(Difficulty.MEDIUM) }
        Spacer(modifier = Modifier.height(16.dp))
        MenuButton("HARD") { onSelect(Difficulty.HARD) }

        Spacer(modifier = Modifier.height(32.dp))

        ActionButton("BACK") { onBack() }
    }
}
