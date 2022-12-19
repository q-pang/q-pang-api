package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.RegisterPaymentMethodUseCase
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import com.qpang.userservice.domain.PaymentMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterPaymentMethodService(
    private val userPersistencePort: UserPersistencePort
) : RegisterPaymentMethodUseCase {
    @Transactional
    override fun command(command: RegisterPaymentMethodUseCase.RegisterPaymentMethodCommand): RegisterPaymentMethodUseCase.RegisterPaymentMethodInfo {
        val user = userPersistencePort.findUserByUsername(command.username)
        user ?: throw UsernameNotFoundException(command.username)

        val newPaymentMethod = PaymentMethod(
            type = command.type,
            company = command.company,
            number = command.number,
            user = user
        )
        user.registerPaymentMethod(newPaymentMethod)

        return RegisterPaymentMethodUseCase.RegisterPaymentMethodInfo(
            id = user.getId(),
            username = user.username,
            type = newPaymentMethod.type,
            company = newPaymentMethod.company,
            number = newPaymentMethod.number
        )
    }
}