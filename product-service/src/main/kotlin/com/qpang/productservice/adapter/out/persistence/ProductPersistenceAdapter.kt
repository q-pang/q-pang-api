package com.qpang.productservice.adapter.out.persistence

import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.domain.ProductCategory
import com.qpang.productservice.infrastructure.repository.ProductCategoryRepository
import org.springframework.stereotype.Component

@Component
class ProductPersistenceAdapter(
    private val productCategoryRepository: ProductCategoryRepository
) : ProductPersistencePort {
    override fun save(productCategory: ProductCategory): ProductCategory =
        productCategoryRepository.save(productCategory)

    override fun existsByName(name: String): Boolean = productCategoryRepository.existsByName(name)
    override fun findById(id: String): ProductCategory? = productCategoryRepository.findProductCategoryById(id)
    override fun findAll(): List<ProductCategory> = productCategoryRepository.findAll()
    override fun delete(productCategory: ProductCategory) = productCategoryRepository.delete(productCategory)
}