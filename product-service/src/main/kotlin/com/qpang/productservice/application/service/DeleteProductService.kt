package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.DeleteProductUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductIdNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteProductService(
    private val productPersistencePort: ProductPersistencePort
) : DeleteProductUseCase {
    @Transactional
    override fun command(command: DeleteProductUseCase.DeleteProductCommand): DeleteProductUseCase.DeleteProductInfo {
        val savedProduct = productPersistencePort.findProductById(command.id)
        savedProduct ?: throw ProductIdNotFoundException(command.id)
        productPersistencePort.deleteProduct(savedProduct)

        return DeleteProductUseCase.DeleteProductInfo.from(savedProduct)
    }
}