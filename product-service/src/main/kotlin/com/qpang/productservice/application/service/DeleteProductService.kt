package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.DeleteProductUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductIdNotFoundException
import org.springframework.stereotype.Service

@Service
class DeleteProductService(
    private val productPersistencePort: ProductPersistencePort
) : DeleteProductUseCase {
    override fun command(command: DeleteProductUseCase.DeleteProductCommand): DeleteProductUseCase.DeleteProductInfo {
        val product = productPersistencePort.findProductById(command.id)
        product ?: throw ProductIdNotFoundException(command.id)
        productPersistencePort.deleteProduct(product)

        return DeleteProductUseCase.DeleteProductInfo.from(product)
    }
}