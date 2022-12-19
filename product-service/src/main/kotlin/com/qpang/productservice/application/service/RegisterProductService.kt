package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.RegisterProductUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductCategoryIdNotFoundException
import com.qpang.productservice.domain.Product
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterProductService(
    private val productPersistencePort: ProductPersistencePort
) : RegisterProductUseCase {
    @Transactional
    override fun command(command: RegisterProductUseCase.RegisterProductCommand): RegisterProductUseCase.RegisterProductInfo {
        val savedProductCategory = productPersistencePort.findProductCategoryById(command.categoryId)
        savedProductCategory ?: throw ProductCategoryIdNotFoundException(command.categoryId)
        val newProduct =
            Product(name = command.name, stock = command.stock, price = command.price, category = savedProductCategory)
        val savedProduct = productPersistencePort.saveProduct(newProduct)
        val savedFetchingProduct = productPersistencePort.findProductById(savedProduct.getId())!!

        return RegisterProductUseCase.RegisterProductInfo.from(savedFetchingProduct)
    }
}