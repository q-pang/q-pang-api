package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.RegisterProductCategoryUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.DuplicateProductCategoryNameException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class RegisterProductCategoryServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val registerProductCategoryService = RegisterProductCategoryService(mockProductPersistencePort)

    describe("registerProductCategory") {
        context("등록되지 않은 name을 가진 Command가 주어지면") {
            every { mockProductPersistencePort.existsByName(notDuplicatedNameCommand.name) } answers { false }
            every { mockProductPersistencePort.save(any()) } answers { notDuplicatedNameCommand.toEntity() }
            it("상품 카테고리 등록에 성공하고 RegisterProductCategoryInfo 응답") {
                val resultInfo = registerProductCategoryService.command(notDuplicatedNameCommand)

                resultInfo.name shouldBe notDuplicatedNameCommand.name
            }
        }

        context("이미 등록된 name을 가진 Command가 주어지면") {
            every { mockProductPersistencePort.existsByName(duplicatedNameCommand.name) } answers { true }
            it("상품 카테고리 등록에 실패하고 DuplicateProductCategoryNameException 발생") {
                shouldThrow<DuplicateProductCategoryNameException> {
                    registerProductCategoryService.command(duplicatedNameCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val duplicatedNameCommand = RegisterProductCategoryUseCase.RegisterProductCategoryCommand(
            name = "name"
        )

        private val notDuplicatedNameCommand = RegisterProductCategoryUseCase.RegisterProductCategoryCommand(
            name = "name"
        )
    }
}