package com.qpang.productservice.infrastructure.repository

import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.QProduct
import com.qpang.productservice.domain.QProductCategory
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository


@Repository
class ProductRepositorySupport(
    private val queryFactory: JPAQueryFactory
) {
    fun findById(id: String): Product? =
        queryFactory.selectFrom(QProduct.product)
            .where(QProduct.product.id.eq(id))
            .leftJoin(QProduct.product.category, QProductCategory.productCategory)
            .fetchJoin()
            .fetchOne()

    fun findAll(categoryId: String?): List<Product> =
        queryFactory.selectFrom(QProduct.product)
            .where(eqCategoryId(categoryId))
            .leftJoin(QProduct.product.category, QProductCategory.productCategory)
            .fetchJoin()
            .fetch()

    private fun eqCategoryId(categoryId: String?): BooleanExpression? {
        if (categoryId == null) return null
        return QProduct.product.category.id.eq(categoryId)
    }
}