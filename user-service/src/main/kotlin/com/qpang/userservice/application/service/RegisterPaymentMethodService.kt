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
        val savedUser = userPersistencePort.findUserByUsername(command.username)
        savedUser ?: throw UsernameNotFoundException(command.username)

        val newPaymentMethod = PaymentMethod(
            type = command.type,
            company = command.company,
            number = command.number,
            user = savedUser
        )
        savedUser.registerPaymentMethod(newPaymentMethod)

        return RegisterPaymentMethodUseCase.RegisterPaymentMethodInfo(
            id = savedUser.getId(),
            username = savedUser.username,
            type = newPaymentMethod.type,
            company = newPaymentMethod.company,
            number = newPaymentMethod.number
        )
    }
}