package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.domain.ProductCategory

interface DeleteProductCategoryUseCase {
    fun command(command: DeleteProductCategoryCommand): DeleteProductCategoryInfo

    data class DeleteProductCategoryCommand(
        val id: String
    )

    data class DeleteProductCategoryInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(productCategory: ProductCategory) = DeleteProductCategoryInfo(
                id = productCategory.getId(),
                name = productCategory.name
            )
        }
    }
}