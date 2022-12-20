package com.qpang.orderservice.domain

import com.qpang.orderservice.common.entity.JpaAuditEntity
import javax.persistence.*

@Entity
@Table(name = "payments")
class Payment(
    type: PaymentMethodType,
    company: CardCompany,
    number: String,
    username: String,
    order: Order
) : JpaAuditEntity() {
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: PaymentMethodType = type
        protected set

    @Column(name = "company", nullable = false)
    @Enumerated(EnumType.STRING)
    var company: CardCompany = company
        protected set

    @Column(name = "number", nullable = false)
    var number: String = number
        protected set

    @Column(name = "username", nullable = false)
    var username: String = username
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    var order: Order = order

    enum class PaymentMethodType(
        val typeName: String
    ) {
        CREDITCARD("CREDITCARD"), DEBITCARD("DEBITCARD")
    }

    enum class CardCompany(
        val companyName: String
    ) {
        SAMSUNG("SAMSUNG"), HYUNDAI("HYUNDAI")
    }
}