package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.adapter.`in`.rest.dto.ProductCategoryResponseDto
import com.qpang.productservice.application.port.`in`.usecase.UpdateStockUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UpdateStockRestAdapter(
    private val updateStockUseCase: UpdateStockUseCase
) {
    @PatchMapping("/product/stock")
    fun updateStock(@RequestBody @Valid dto: UpdateStockRequestDto): ResponseEntity<UpdateStockResponseDto> =
        ResponseEntity.ok().body(UpdateStockResponseDto.from(updateStockUseCase.command(dto.toCommand())))

    data class UpdateStockRequestDto(
        val id: String,
        val updatedStock: Long
    ) {
        fun toCommand(): UpdateStockUseCase.UpdateStockCommand = UpdateStockUseCase.UpdateStockCommand(
            id = id,
            updatedStock = updatedStock
        )
    }

    data class UpdateStockResponseDto(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryResponseDto
    ) {
        companion object {
            fun from(info: UpdateStockUseCase.UpdateStockInfo) = UpdateStockResponseDto(
                id = info.id,
                name = info.name,
                stock = info.stock,
                price = info.price,
                category = ProductCategoryResponseDto.from(info.category)
            )
        }
    }
}