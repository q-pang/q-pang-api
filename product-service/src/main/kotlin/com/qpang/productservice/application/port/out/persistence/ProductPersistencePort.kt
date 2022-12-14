package com.qpang.productservice.application.port.out.persistence

import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory

interface ProductPersistencePort {
    fun saveProductCategory(productCategory: ProductCategory): ProductCategory
    fun saveProduct(product: Product): Product
    fun existsProductCategoryByName(name: String): Boolean
    fun findProductCategoryById(id: String): ProductCategory?
    fun findAllProductCategory(): List<ProductCategory>
    fun findAllProduct(categoryId: String?, name: String?): List<Product>
    fun findProductsByIds(ids: List<String>): List<Product>
    fun deleteProductCategory(productCategory: ProductCategory)
    fun findProductById(id: String): Product?
    fun deleteProduct(product: Product)
}