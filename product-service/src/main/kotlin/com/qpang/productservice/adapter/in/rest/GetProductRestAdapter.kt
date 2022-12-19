package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.adapter.`in`.rest.dto.ProductCategoryResponseDto
import com.qpang.productservice.application.port.`in`.usecase.GetProductUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetProductRestAdapter(
    private val getProductUseCase: GetProductUseCase
) {
    @GetMapping("/product/{id}")
    fun getProduct(@PathVariable id: String): ResponseEntity<GetProductResponseDto> =
        ResponseEntity.ok()
            .body(GetProductResponseDto.from(getProductUseCase.command(GetProductUseCase.GetProductCommand(id))))

    data class GetProductRequestDto(
        val id: String,
    ) {
        fun toCommand(): GetProductUseCase.GetProductCommand = GetProductUseCase.GetProductCommand(
            id = id,
        )
    }

    data class GetProductResponseDto(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryResponseDto
    ) {
        companion object {
            fun from(info: GetProductUseCase.GetProductInfo) = GetProductResponseDto(
                id = info.id,
                name = info.name,
                stock = info.stock,
                price = info.price,
                category = ProductCategoryResponseDto.from(info.category)
            )
        }
    }
}