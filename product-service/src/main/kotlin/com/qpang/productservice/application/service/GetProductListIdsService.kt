package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.GetProductListIdsUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetProductListIdsService(
    private val productPersistencePort: ProductPersistencePort
) : GetProductListIdsUseCase {
    @Transactional(readOnly = true)
    override fun command(command: GetProductListIdsUseCase.GetProductListIdsCommand): List<GetProductListIdsUseCase.GetProductListIdsInfo> {
        val savedProductList = productPersistencePort.findProductsByIds(command.ids)

        return GetProductListIdsUseCase.GetProductListIdsInfo.from(savedProductList)
    }
}