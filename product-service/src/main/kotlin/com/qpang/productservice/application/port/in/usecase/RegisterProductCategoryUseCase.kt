package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.domain.ProductCategory

interface RegisterProductCategoryUseCase {
    fun command(command: RegisterProductCategoryCommand): RegisterProductCategoryInfo

    data class RegisterProductCategoryCommand(
        val name: String
    ) {
        fun toEntity() = ProductCategory(name = name)
    }

    data class RegisterProductCategoryInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(productCategory: ProductCategory) = RegisterProductCategoryInfo(
                id = productCategory.getId(),
                name = productCategory.name
            )
        }
    }
}