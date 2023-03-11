package com.app.retrogrid.ui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.retrogrid.viewmodel.RetrogridViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MainView(viewModel:RetrogridViewModel = viewModel()){
    Text(text = "Hello")
}