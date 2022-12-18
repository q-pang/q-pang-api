package com.qpang.userservice.infrastructure.repository

import com.qpang.userservice.domain.QPaymentMethod
import com.qpang.userservice.domain.QUser
import com.qpang.userservice.domain.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserRepositorySupport(
    private val queryFactory: JPAQueryFactory
) {
    fun findByUsername(username: String): User? {
        return queryFactory.selectFrom(QUser.user)
            .where(QUser.user.username.eq(username))
            .leftJoin(QUser.user.paymentMethods, QPaymentMethod.paymentMethod)
            .fetchJoin()
            .fetchOne()
    }
}