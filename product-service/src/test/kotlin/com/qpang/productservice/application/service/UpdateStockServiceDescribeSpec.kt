package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.UpdateStockUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductIdNotFoundException
import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UpdateStockServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val updateStockService = UpdateStockService(mockProductPersistencePort)

    describe("updateStock") {
        context("등록된 productId를 가진 Command가 주어지면") {
            val expectedProduct =
                Product(name = "name", stock = OLD_STOCK, price = 100, category = ProductCategory("의류"))
            every { mockProductPersistencePort.findProductById(registeredProductIdCommand.id) } answers { expectedProduct }
            it("재고 변경에 성공하고 UpdateStockInfo 응답") {
                val resultInfo = updateStockService.command(registeredProductIdCommand)

                resultInfo.stock shouldBe OLD_STOCK + registeredProductIdCommand.updatedStock
            }
        }

        context("등록되지 않은 productId를 가진 Command가 주어지면") {
            every { mockProductPersistencePort.findProductById(notRegisteredProductIdCommand.id) } answers { null }
            it("재고 변경에 실패하고 ProductIdNotFoundException 발생") {
                shouldThrow<ProductIdNotFoundException> {
                    updateStockService.command(notRegisteredProductIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredProductIdCommand = UpdateStockUseCase.UpdateStockCommand(
            id = "registeredId",
            updatedStock = -1
        )
        private val notRegisteredProductIdCommand = UpdateStockUseCase.UpdateStockCommand(
            id = "notRegisteredId",
            updatedStock = -1
        )
        private const val OLD_STOCK = 10L
    }
}