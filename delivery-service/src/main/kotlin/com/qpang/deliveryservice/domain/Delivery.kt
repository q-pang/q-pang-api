package com.qpang.deliveryservice.domain

import com.qpang.deliveryservice.common.entity.JpaAuditEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "deliveries")
class Delivery(
    userId: String,
    orderId: String
) : JpaAuditEntity() {

    @Column(name = "user_id", nullable = false)
    var userId: String = userId
        protected set

    @Column(name = "order_id", nullable = false)
    var orderId: String = orderId
        protected set

    @Column(name = "arrival_at")
    var arrivalAt: LocalDateTime? = null
        protected set

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus = DeliveryStatus.AWAITING
        protected set

    enum class DeliveryStatus(
        val statusName: String
    ) {
        AWAITING("AWAITING"), INPROGRESS("INPROGRESS"), COMPLETED("COMPLETED"), CANCELLED("CANCELLED")
    }
}