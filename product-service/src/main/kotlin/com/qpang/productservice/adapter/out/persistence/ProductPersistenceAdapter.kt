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
}