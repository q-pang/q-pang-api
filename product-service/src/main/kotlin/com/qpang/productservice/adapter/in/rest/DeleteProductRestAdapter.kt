package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.application.port.`in`.usecase.DeleteProductUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class DeleteProductRestAdapter(
    private val deleteProductUseCase: DeleteProductUseCase
) {
    @DeleteMapping("/product")
    fun deleteProduct(@RequestBody @Valid dto: DeleteProductRequestDto): ResponseEntity<DeleteProductResponseDto> =
        ResponseEntity.ok().body(DeleteProductResponseDto.from(deleteProductUseCase.command(dto.toCommand())))

    data class DeleteProductRequestDto(
        val id: String
    ) {
        fun toCommand(): DeleteProductUseCase.DeleteProductCommand =
            DeleteProductUseCase.DeleteProductCommand(id = id)
    }

    data class DeleteProductResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: DeleteProductUseCase.DeleteProductInfo): DeleteProductResponseDto =
                DeleteProductResponseDto(
                    id = info.id,
                    name = info.name
                )
        }
    }
}