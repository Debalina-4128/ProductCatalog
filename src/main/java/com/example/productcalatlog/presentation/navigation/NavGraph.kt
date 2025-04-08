package com.example.productcalatlog.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.productcalatlog.presentation.details.ProductDetailScreen
import com.example.productcalatlog.presentation.products.ProductListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "products") {
        composable("products") {
            ProductListScreen(onProductClick = { productId ->
                navController.navigate("product/$productId")
            })
        }
        composable(
            route = "product/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            ProductDetailScreen(productId = it.arguments?.getInt("productId")!!)
        }
    }
}