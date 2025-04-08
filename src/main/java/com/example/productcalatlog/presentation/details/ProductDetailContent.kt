package com.example.productcalatlog.presentation.details

import Product
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.productcalatlog.R

@Composable
fun ProductDetailContent(product: Product) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { product.images.size }
    )

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) { page ->
            AsyncImage(
                model = product.images.getOrNull(page) ?: "",
                contentDescription = "Product image ${page + 1}",
                modifier = Modifier.fillMaxSize(),
                error = painterResource(R.drawable.placeholder__2_),
                placeholder = painterResource(R.drawable.placeholder__2_)
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "$${"%.2f".format(product.price)}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            RatingBar(rating = product.rating)

            Text(
                text = "Category: ${product.category.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}