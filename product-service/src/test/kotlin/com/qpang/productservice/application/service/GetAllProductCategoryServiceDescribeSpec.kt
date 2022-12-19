package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.domain.ProductCategory
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class GetAllProductCategoryServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val getAllProductCategoryService = GetAllProductCategoryService(mockProductPersistencePort)

    describe("getAllProductCategory") {
        val expectedProductCategoryList = listOf(ProductCategory(name = "name1"), ProductCategory(name = "name2"))
        every { mockProductPersistencePort.findAll() } answers { expectedProductCategoryList }
        it("상품 카테고리 전체 조회에 성공하고 GetProductCategoryInfoList 응답") {
            val infoList = getAllProductCategoryService.command()
            assertSoftly {
                infoList.size shouldBe expectedProductCategoryList.size
                infoList[0].name shouldBe expectedProductCategoryList[0].name
            }
        }
    }
})