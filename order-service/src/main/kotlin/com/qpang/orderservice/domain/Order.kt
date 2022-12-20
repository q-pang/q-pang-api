package com.qpang.orderservice.domain

import com.qpang.orderservice.common.entity.JpaAuditEntity
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    consumerId: String,
) : JpaAuditEntity() {
    @Column(name = "total_price", nullable = false)
    var totalPrice: Long = 0
        protected set

    @Column(name = "consumer_id", nullable = false)
    var consumerId: String = consumerId
        protected set

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var orderItems: MutableList<OrderItem> = mutableListOf()
        protected set

    @OneToOne(mappedBy = "order", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var payment: Payment? = null
        protected set

    fun addOrderItems(newOrderItems: List<OrderItem>) {
        orderItems = newOrderItems.toMutableList()
    }

    fun addPayment(newPayment: Payment) {
        payment = newPayment
    }

    fun addTotalPrice(newTotalPrice: Long) {
        totalPrice = newTotalPrice
    }
}