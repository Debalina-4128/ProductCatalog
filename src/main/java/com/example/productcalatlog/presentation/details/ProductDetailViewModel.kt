package com.example.productcalatlog.presentation.details

import Product
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcalatlog.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailViewModel @ProductRepositoryImpl.Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _state = mutableStateOf(ProductDetailState())

    // 3. Expose immutable state
    val state: State<ProductDetailState> = _state

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val product = repository.getProductById(productId)
                _state.value = ProductDetailState(product = product)
            } catch (e: Exception) {
                _state.value = ProductDetailState(error = e.localizedMessage)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}

data class ProductDetailState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
