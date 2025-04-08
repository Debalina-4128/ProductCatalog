package com.example.productcalatlog.data.api.model

import ApiProduct

data class ApiProductsResponse(
    val products: List<ApiProduct>,
    val total: Int,
    val skip: Int,
    val limit: Int
)