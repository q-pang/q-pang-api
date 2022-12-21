package com.qpang.orderservice.adapter.out.rest

import com.qpang.orderservice.application.port.out.rest.DeliveryServiceRestPort
import com.qpang.orderservice.application.port.out.rest.dto.DeliveryResponseDto
import com.qpang.orderservice.infrastructure.feignclient.DeliveryServiceClient
import org.springframework.stereotype.Component

@Component
class DeliveryServiceRestAdapter(
    private val deliveryServiceClient: DeliveryServiceClient
) : DeliveryServiceRestPort {
    override fun getDelivery(deliveryId: String): DeliveryResponseDto = deliveryServiceClient.getDelivery(deliveryId)
}