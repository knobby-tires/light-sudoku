package com.lightphone.sudoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lightphone.sudoku.data.GameStorage
import com.lightphone.sudoku.data.PuzzleRepository
import com.lightphone.sudoku.ui.SudokuApp
import com.lightphone.sudoku.ui.SudokuViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: SudokuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val puzzleRepository = PuzzleRepository(applicationContext)
        val gameStorage = GameStorage(applicationContext)
        viewModel = SudokuViewModel(puzzleRepository, gameStorage)

        setContent {
            SudokuApp(viewModel)
        }
    }
}
