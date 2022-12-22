package com.qpang.deliveryservice.adapter.`in`.rest

import com.qpang.deliveryservice.application.port.`in`.GetDeliveryByOrderIdUseCase
import com.qpang.deliveryservice.domain.Delivery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class GetDeliveryByOrderIdRestAdapter(
    private val getDeliveryByOrderIdUseCase: GetDeliveryByOrderIdUseCase
) {
    @GetMapping("/delivery/by-orderId/{orderId}")
    fun getDelivery(@PathVariable orderId: String): ResponseEntity<GetDeliveryByOrderIdResponseDto> =
        ResponseEntity.ok().body(
            GetDeliveryByOrderIdResponseDto.from(getDeliveryByOrderIdUseCase.command(GetDeliveryByOrderIdUseCase.GetDeliveryByOrderIdCommand(orderId))))

    data class GetDeliveryByOrderIdResponseDto(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(info: GetDeliveryByOrderIdUseCase.GetDeliveryByOrderIdInfo): GetDeliveryByOrderIdResponseDto =
                GetDeliveryByOrderIdResponseDto(
                    id = info.id,
                    orderId = info.orderId,
                    status = info.status,
                    arrivalAt = info.arrivalAt
                )
        }
    }
}