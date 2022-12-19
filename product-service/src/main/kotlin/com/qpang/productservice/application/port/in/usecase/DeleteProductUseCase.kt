package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.domain.Product

interface DeleteProductUseCase {
    fun command(command: DeleteProductCommand): DeleteProductInfo

    data class DeleteProductCommand(
        val id: String
    )

    data class DeleteProductInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(product: Product) = DeleteProductInfo(
                id = product.getId(),
                name = product.name
            )
        }
    }
}