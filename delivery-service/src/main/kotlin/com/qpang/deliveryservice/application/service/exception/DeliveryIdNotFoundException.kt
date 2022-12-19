package com.qpang.deliveryservice.application.service.exception

import com.qpang.deliveryservice.common.exception.BusinessException

class DeliveryIdNotFoundException(id: String) : BusinessException(id) {
    override val message = "배송 id가 존재하지 않습니다. : $id"
}