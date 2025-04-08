import com.example.productcalatlog.data.api.ProductApi
import com.example.productcalatlog.domain.repository.ProductRepository

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {
    annotation class Inject

    override suspend fun getProducts(): List<Product> {
        val response = api.getProducts()
        return response.products.map { it.toDomain() }
    }

    override suspend fun getProductById(id: Int): Product {
        val response = api.getProductById(id)
        return response.toDomain()
    }
}