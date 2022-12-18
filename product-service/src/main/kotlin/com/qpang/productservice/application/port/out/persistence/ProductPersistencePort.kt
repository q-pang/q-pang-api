package com.qpang.productservice.application.port.out.persistence

import com.qpang.productservice.domain.ProductCategory

interface ProductPersistencePort {
    fun save(productCategory: ProductCategory): ProductCategory
    fun existsByName(name: String): Boolean
}