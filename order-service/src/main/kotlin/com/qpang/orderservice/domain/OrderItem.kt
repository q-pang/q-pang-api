package com.qpang.orderservice.domain

import com.qpang.orderservice.common.entity.JpaAuditEntity
import javax.persistence.*

@Entity
@Table(name = "order_items")
class OrderItem(
    name: String,
    price: Long,
    count: Long,
    productId: String,
    order: Order
) : JpaAuditEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "price", nullable = false)
    var price: Long = price
        protected set

    @Column(name = "count", nullable = false)
    var count: Long = count
        protected set

    @Column(name = "product_id", nullable = false)
    var productId: String = productId
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    var order: Order = order
        protected set
}