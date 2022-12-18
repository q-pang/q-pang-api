package com.qpang.productservice.infrastructure.repository

import com.qpang.productservice.domain.ProductCategory
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCategoryRepository : JpaRepository<ProductCategory, String> {
    fun existsByName(name: String): Boolean
    fun findProductCategoryById(id: String): ProductCategory?
}