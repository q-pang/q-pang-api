package com.qpang.orderservice.domain

import com.qpang.orderservice.common.entity.JpaAuditEntity
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    totalPrice: Long,
    consumerId: String,
    orderItems: MutableList<OrderItem>,
    payment: Payment
) : JpaAuditEntity() {
    @Column(name = "total_price", nullable = false)
    var totalPrice: Long = totalPrice
        protected set

    @Column(name = "consumer_id", nullable = false)
    var consumerId: String = consumerId
        protected set

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var orderItems: MutableList<OrderItem> = orderItems
        protected set

    @OneToOne(mappedBy = "order", optional = false, cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var payment: Payment = payment
        protected set
}