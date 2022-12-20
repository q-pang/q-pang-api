package com.qpang.productservice.adapter.out.persistence

import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory
import com.qpang.productservice.infrastructure.repository.ProductCategoryRepository
import com.qpang.productservice.infrastructure.repository.ProductRepository
import com.qpang.productservice.infrastructure.repository.ProductRepositorySupport
import org.springframework.stereotype.Component

@Component
class ProductPersistenceAdapter(
    private val productCategoryRepository: ProductCategoryRepository,
    private val productRepository: ProductRepository,
    private val productRepositorySupport: ProductRepositorySupport
) : ProductPersistencePort {
    override fun saveProductCategory(productCategory: ProductCategory): ProductCategory =
        productCategoryRepository.save(productCategory)

    override fun saveProduct(product: Product): Product = productRepository.save(product)
    override fun existsProductCategoryByName(name: String): Boolean = productCategoryRepository.existsByName(name)
    override fun findProductCategoryById(id: String): ProductCategory? =
        productCategoryRepository.findProductCategoryById(id)

    override fun findAllProductCategory(): List<ProductCategory> = productCategoryRepository.findAll()
    override fun findAllProduct(categoryId: String?, name: String?): List<Product> =
        productRepositorySupport.findAll(categoryId, name)

    override fun findProductsByIds(ids: List<String>): List<Product> = productRepositorySupport.findProductsByIds(ids)
    override fun deleteProductCategory(productCategory: ProductCategory) =
        productCategoryRepository.delete(productCategory)

    override fun findProductById(id: String): Product? = productRepositorySupport.findById(id)
    override fun deleteProduct(product: Product) = productRepository.delete(product)
}