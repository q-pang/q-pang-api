package com.qpang.orderservice.application.port.out.rest

import com.qpang.orderservice.application.port.out.rest.dto.DeliveryResponseDto

interface DeliveryServiceRestPort {
    fun getDelivery(deliveryId: String): DeliveryResponseDto
}