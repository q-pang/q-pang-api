package com.qpang.productservice.application.port.out.persistence

import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory

interface ProductPersistencePort {
    fun saveProductCategory(productCategory: ProductCategory): ProductCategory
    fun existsProductCategoryByName(name: String): Boolean
    fun findProductCategoryById(id: String): ProductCategory?
    fun findAllProductCategory(): List<ProductCategory>
    fun deleteProductCategory(productCategory: ProductCategory)
}