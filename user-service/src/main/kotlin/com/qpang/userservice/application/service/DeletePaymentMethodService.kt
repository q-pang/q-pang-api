package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.DeletePaymentMethodUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeletePaymentMethodService(
    private val userPersistencePort: UserPersistencePort
) : DeletePaymentMethodUseCase {
    @Transactional
    override fun command(command: DeletePaymentMethodUseCase.DeletePaymentMethodCommand): DeletePaymentMethodUseCase.DeletePaymentMethodInfo {
        val user = userPersistencePort.findUserByUsername(command.username)
        user ?: throw UsernameNotFoundException(command.username)
        user.deletePaymentMethod(command.paymentMethodId)

        return DeletePaymentMethodUseCase.DeletePaymentMethodInfo(
            id = user.getId(),
            username = user.username
        )
    }
}