package com.qpang.orderservice.application.service.exception

import com.qpang.orderservice.common.exception.BusinessException

class UncancellableException(id: String) : BusinessException(id) {
    override val message = "배송 상태가 대기중이 아니기 때문에 취소가 불가능 합니다. : $id"
}