package com.qpang.deliveryservice.adapter.`in`.rest

import com.qpang.deliveryservice.application.port.`in`.CompleteDeliveryUseCase
import com.qpang.deliveryservice.domain.Delivery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
class CompleteDeliveryRestAdapter(
    private val completeDeliveryUseCase: CompleteDeliveryUseCase
) {
    @PatchMapping("/delivery/complete")
    fun completeDelivery(@RequestBody @Valid dto: CompleteDeliveryRequestDto): ResponseEntity<CompleteDeliveryResponseDto> =
        ResponseEntity.ok().body(CompleteDeliveryResponseDto.from(completeDeliveryUseCase.command(dto.toCommand())))

    data class CompleteDeliveryRequestDto(
        val id: String
    ) {
        fun toCommand(): CompleteDeliveryUseCase.CompleteDeliveryCommand =
            CompleteDeliveryUseCase.CompleteDeliveryCommand(id)
    }

    data class CompleteDeliveryResponseDto(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(info: CompleteDeliveryUseCase.CompleteDeliveryInfo): CompleteDeliveryResponseDto =
                CompleteDeliveryResponseDto(
                    id = info.id,
                    orderId = info.orderId,
                    status = info.status,
                    arrivalAt = info.arrivalAt
                )
        }
    }
}