package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.UpdateStockUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductIdNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateStockService(
    private val productPersistencePort: ProductPersistencePort
) : UpdateStockUseCase {
    @Transactional
    override fun command(command: UpdateStockUseCase.UpdateStockCommand): UpdateStockUseCase.UpdateStockInfo {
        val savedProduct = productPersistencePort.findProductById(command.id)
        savedProduct ?: throw ProductIdNotFoundException(command.id)
        savedProduct.updateStock(command.updatedStock)

        return UpdateStockUseCase.UpdateStockInfo.from(savedProduct)
    }
}