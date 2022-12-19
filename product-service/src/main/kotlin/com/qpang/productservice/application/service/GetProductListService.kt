package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.GetProductListUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetProductListService(
    private val productPersistencePort: ProductPersistencePort
) : GetProductListUseCase {
    @Transactional(readOnly = true)
    override fun command(command: GetProductListUseCase.GetProductListCommand): List<GetProductListUseCase.GetProductListInfo> {
        val savedProductList = productPersistencePort.findAllProduct(command.categoryId, command.name)

        return GetProductListUseCase.GetProductListInfo.from(savedProductList)
    }
}