package com.qpang.deliveryservice.adapter.`in`.rest

import com.qpang.deliveryservice.application.port.`in`.GetDeliveryUseCase
import com.qpang.deliveryservice.domain.Delivery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class GetDeliveryRestAdapter(
    private val getDeliveryUseCase: GetDeliveryUseCase
) {
    @GetMapping("/delivery/{id}")
    fun getDelivery(@PathVariable id: String): ResponseEntity<GetDeliveryResponseDto> =
        ResponseEntity.ok()
            .body(GetDeliveryResponseDto.from(getDeliveryUseCase.command(GetDeliveryUseCase.GetDeliveryCommand(id))))

    data class GetDeliveryResponseDto(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(info: GetDeliveryUseCase.GetDeliveryInfo): GetDeliveryResponseDto = GetDeliveryResponseDto(
                id = info.id,
                orderId = info.orderId,
                status = info.status,
                arrivalAt = info.arrivalAt
            )
        }
    }
}