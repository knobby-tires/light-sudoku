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
import com.lightphone.sudoku.ui.components.MenuButton
import com.lightphone.sudoku.ui.theme.White

@Composable
fun HomeScreen(onLevels: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SUDOKU",
            fontSize = 32.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 10.sp,
            color = White
        )

        Spacer(modifier = Modifier.height(64.dp))

        MenuButton("LEVELS") { onLevels() }
    }
}
