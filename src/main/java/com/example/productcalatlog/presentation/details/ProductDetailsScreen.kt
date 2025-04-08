package com.example.productcalatlog.presentation.details

import Product
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun ProductDetailScreen(productId: Int) {
    val viewModel: ProductDetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> FullScreenLoader()
        state.error != null -> ErrorState(
            message = state.error!!,
            onRetry = { viewModel.loadProduct(productId) }
        )
        state.product != null -> ProductDetailContent(product = state.product!!)
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp))
                    Button(onClick = onRetry) {
                Text("Retry")
            }
    }
}

