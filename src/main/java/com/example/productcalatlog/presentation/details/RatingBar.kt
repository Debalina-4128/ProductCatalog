package com.example.productcalatlog.presentation.details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.productcalatlog.R

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starSize: Dp = 24.dp
) {
    Row(modifier) {
        repeat(stars) { index ->
            val starValue = index + 1
            Icon(
                painter = painterResource(
                    id = if (rating >= starValue) R.drawable.star_filled
                    else R.drawable.star_outline
                ),
                contentDescription = "Rating",
                modifier = Modifier.size(starSize),
                tint = if (rating >= starValue) Color.Yellow else Color.Gray
            )
        }
    }
}