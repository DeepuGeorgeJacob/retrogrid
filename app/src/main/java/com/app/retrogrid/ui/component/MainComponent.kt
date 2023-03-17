package com.app.retrogrid.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.retrogrid.viewmodel.RetrogridViewModel

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MainView(viewModel: RetrogridViewModel = viewModel()) {
    val listItems by viewModel.newsOverview.observeAsState(emptyList())
    LazyColumn {
        items(listItems) { article ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(text = article.title.orEmpty())
                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                }
                
            }
        }
    }
}