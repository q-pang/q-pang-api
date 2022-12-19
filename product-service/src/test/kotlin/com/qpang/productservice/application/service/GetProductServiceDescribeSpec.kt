package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.GetProductUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductIdNotFoundException
import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class GetProductServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val getProductService = GetProductService(mockProductPersistencePort)

    describe("getProduct") {
        context("등록된 productId를 가진 Command가 주어지면") {
            val expectedProduct = Product(name = "name", stock = 10, price = 100, category = ProductCategory("의류"))
            every { mockProductPersistencePort.findProductById(registeredProductIdCommand.id) } answers { expectedProduct }
            it("상품 조회에 성공하고 GetProductInfo 응답") {
                val resultInfo = getProductService.command(registeredProductIdCommand)
            }
        }

        context("등록되지 않은 productId를 가진 Command가 주어지면") {
            every { mockProductPersistencePort.findProductById(notRegisteredProductIdCommand.id) } answers { null }
            it("상품 조회에 실패하고 ProductIdNotFoundException 발생") {
                shouldThrow<ProductIdNotFoundException> {
                    getProductService.command(notRegisteredProductIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredProductIdCommand = GetProductUseCase.GetProductCommand(
            id = "registeredId",
        )
        private val notRegisteredProductIdCommand = GetProductUseCase.GetProductCommand(
            id = "notRegisteredId",
        )
    }
}