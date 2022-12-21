package com.qpang.deliveryservice.adapter.`in`.rest

import com.qpang.deliveryservice.application.port.`in`.BeginDeliveryUseCase
import com.qpang.deliveryservice.domain.Delivery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
class BeginDeliveryRestAdapter(
    private val beginDeliveryUseCase: BeginDeliveryUseCase
) {
    @PatchMapping("/delivery/begin")
    fun beginDelivery(@RequestBody @Valid dto: BeginDeliveryRequestDto): ResponseEntity<BeginDeliveryResponseDto> =
        ResponseEntity.ok().body(BeginDeliveryResponseDto.from(beginDeliveryUseCase.command(dto.toCommand())))

    data class BeginDeliveryRequestDto(
        val id: String
    ) {
        fun toCommand(): BeginDeliveryUseCase.BeginDeliveryCommand =
            BeginDeliveryUseCase.BeginDeliveryCommand(id)
    }

    data class BeginDeliveryResponseDto(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(info: BeginDeliveryUseCase.BeginDeliveryInfo): BeginDeliveryResponseDto =
                BeginDeliveryResponseDto(
                    id = info.id,
                    orderId = info.orderId,
                    status = info.status,
                    arrivalAt = info.arrivalAt
                )
        }
    }
}