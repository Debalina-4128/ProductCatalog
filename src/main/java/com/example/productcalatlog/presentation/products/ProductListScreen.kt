package com.example.productcalatlog.presentation.products

import Product
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.productcalatlog.presentation.details.ErrorState
import com.example.productcalatlog.presentation.details.FullScreenLoader
import com.example.productcalatlog.presentation.details.ProductListViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> FullScreenLoader()
        state.error != null -> ErrorState(message = state.error!!, onRetry = viewModel::loadProducts)
        else -> ProductListContent(products = state.products, onProductClick = onProductClick)
    }
}

@Composable
private fun ProductListContent(products: List<Product>, onProductClick: (Int) -> Unit) {
    LazyColumn {
        items(products, key = { it.id }) { product ->
            ProductItem(product = product, onItemClick = onProductClick)
        }
    }
}

@Composable
fun ProductItem(product: Product, onItemClick: (Int) -> Unit) {
    Card(
        onClick = { onItemClick(product.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = product.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}