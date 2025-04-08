package com.example.productcalatlog.presentation.products

import Product
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.viewmodel.compose.viewModel
import app.cash.turbine.test
import com.example.productcalatlog.domain.model.Product
import com.example.productcalatlog.domain.repository.ProductRepository
import com.example.productcalatlog.domain.usecase.GetProductsUseCase
import com.example.productcalatlog.domain.usecases.GetProductsUseCase
import com.example.productcalatlog.presentation.details.ProductListState
import com.example.productcalatlog.presentation.details.ProductListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {
    private val mockRepository = mockk<ProductRepository>()
    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = ProductListViewModel(GetProductsUseCase(mockRepository))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialLoadShowsLoadingState() = runTest {
        coEvery { mockRepository.getProducts() } returns flowOf(emptyList())

        viewModel.state.test {
            viewModel.loadProducts()

            // Loading state
            assertTrue(awaitItem().isLoading)

            // Loaded state
            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertTrue(loadedState.products.isEmpty())

            cancelAndConsumeRemainingEvents()
        }
    }
}

class ProductListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displayProductList() {
        composeTestRule.setContent {
            ProductListScreen(
                viewModel = FakeProductListViewModel(testProducts),
                onProductClick = {}
            )
        }

        composeTestRule.onNodeWithText("Test Product").assertExists()
        composeTestRule.onNodeWithText("$99.99").assertExists()
    }

    private val testProducts = listOf(
        Product(
            id = 1,
            title = "Test Product",
            price = 99.99,
            description = "Test Description",
            rating = 4.5,
            category = "Test Category",
            images = emptyList(),
            discountPercentage = 10.0,
            stock = 100,
            brand = "Test Brand",
            thumbnail = ""
        )
    )
}

// Test Doubles
class FakeProductListViewModel(
    products: List<Product>
) : ProductListViewModel(GetProductsUseCase(mockk(relaxed = true))) {
    init {
        state = ProductListState(products = products)
    }
}