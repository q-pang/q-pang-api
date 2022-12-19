package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.adapter.`in`.rest.dto.ProductCategoryResponseDto
import com.qpang.productservice.application.port.`in`.usecase.RegisterProductUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class RegisterProductRestAdapter(
    private val registerProductUseCase: RegisterProductUseCase
) {
    @PostMapping("/product")
    fun registerProduct(@RequestBody @Valid dto: RegisterProductRequestDto): ResponseEntity<RegisterProductResponseDto> =
        ResponseEntity(
            RegisterProductResponseDto.from(registerProductUseCase.command(dto.toEntity())),
            HttpStatus.CREATED
        )

    data class RegisterProductRequestDto(
        val name: String,
        val stock: Long,
        val price: Long,
        val categoryId: String
    ) {
        fun toEntity(): RegisterProductUseCase.RegisterProductCommand = RegisterProductUseCase.RegisterProductCommand(
            name = name,
            stock = stock,
            price = price,
            categoryId = categoryId
        )
    }

    data class RegisterProductResponseDto(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryResponseDto
    ) {
        companion object {
            fun from(info: RegisterProductUseCase.RegisterProductInfo) = RegisterProductResponseDto(
                id = info.id,
                name = info.name,
                stock = info.stock,
                price = info.price,
                category = ProductCategoryResponseDto.from(info.category)
            )
        }
    }
}