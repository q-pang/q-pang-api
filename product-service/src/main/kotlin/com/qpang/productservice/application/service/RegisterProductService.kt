package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.RegisterProductUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductCategoryIdNotFoundException
import com.qpang.productservice.domain.Product
import org.springframework.stereotype.Service

@Service
class RegisterProductService(
    private val productPersistencePort: ProductPersistencePort
) : RegisterProductUseCase {
    override fun command(command: RegisterProductUseCase.RegisterProductCommand): RegisterProductUseCase.RegisterProductInfo {
        val savedProductCategory = productPersistencePort.findProductCategoryById(command.categoryId)
        savedProductCategory ?: throw ProductCategoryIdNotFoundException(command.categoryId)
        val newProduct = Product(name = command.name, stock = command.stock, category = savedProductCategory)
        val savedProduct = productPersistencePort.saveProduct(newProduct)
        return RegisterProductUseCase.RegisterProductInfo.from(productPersistencePort.findProductById(savedProduct.getId())!!)
    }
}