package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.application.port.`in`.usecase.DeleteProductCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class DeleteProductCategoryRestAdapter(
    private val deleteProductCategoryUseCase: DeleteProductCategoryUseCase
) {
    @DeleteMapping("/product/category")
    fun deleteProductCategory(@RequestBody @Valid dto: DeleteProductCategoryRequestDto): ResponseEntity<DeleteProductCategoryResponseDto> =
        ResponseEntity.ok()
            .body(DeleteProductCategoryResponseDto.from(deleteProductCategoryUseCase.command(dto.toCommand())))

    data class DeleteProductCategoryRequestDto(
        val id: String
    ) {
        fun toCommand(): DeleteProductCategoryUseCase.DeleteProductCategoryCommand =
            DeleteProductCategoryUseCase.DeleteProductCategoryCommand(id = id)
    }

    data class DeleteProductCategoryResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: DeleteProductCategoryUseCase.DeleteProductCategoryInfo): DeleteProductCategoryResponseDto =
                DeleteProductCategoryResponseDto(
                    id = info.id,
                    name = info.name
                )
        }
    }
}