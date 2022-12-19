package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.application.port.`in`.usecase.info.ProductCategoryInfo
import com.qpang.productservice.domain.Product

interface RegisterProductUseCase {
    fun command(command: RegisterProductCommand): RegisterProductInfo

    data class RegisterProductCommand(
        val name: String,
        val stock: Long,
        val price: Long,
        val categoryId: String
    )

    data class RegisterProductInfo(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryInfo
    ) {
        companion object {
            fun from(product: Product) = RegisterProductInfo(
                id = product.getId(),
                name = product.name,
                stock = product.stock,
                price = product.price,
                category = ProductCategoryInfo.from(product.category)
            )
        }
    }
}