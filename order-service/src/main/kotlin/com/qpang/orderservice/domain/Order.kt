package com.qpang.orderservice.domain

import com.qpang.orderservice.common.entity.JpaAuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "orders")
class Order(
    totalPrice: Long,
    consumerId: String,
) : JpaAuditEntity() {
    @Column(name = "total_price", nullable = false)
    var totalPrice: Long = totalPrice
        protected set

    @Column(name = "consumer_id", nullable = false)
    var consumerId: String = consumerId
        protected set
}