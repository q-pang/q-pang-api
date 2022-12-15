package com.qpang.userservice.domain

import com.qpang.userservice.common.entity.JpaAuditEntity
import javax.persistence.*

@Entity
@Table(name = "payment_methods")
class PaymentMethod(
    type: PaymentMethodType,
    company: CardCompany,
    user: User
) : JpaAuditEntity() {

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: PaymentMethodType = type
        protected set

    @Column(name = "company", nullable = false)
    @Enumerated(EnumType.STRING)
    var company: CardCompany = company
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    var user: User = user
        protected set

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