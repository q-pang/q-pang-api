package com.qpang.userservice.application.service

import com.qpang.userservice.application.port.`in`.usecase.MyInfoUseCase
import com.qpang.userservice.application.port.`in`.usecase.info.PaymentMethodInfo
import com.qpang.userservice.application.port.out.persistence.UserPersistencePort
import com.qpang.userservice.application.service.exception.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MyInfoService(
    private val userPersistencePort: UserPersistencePort
) : MyInfoUseCase {
    @Transactional(readOnly = true)
    override fun command(command: MyInfoUseCase.MyInfoCommand): MyInfoUseCase.MyInfoInfo {
        val savedUser = userPersistencePort.findUserByUsername(command.username)
        savedUser ?: throw UsernameNotFoundException(command.username)

        return MyInfoUseCase.MyInfoInfo(
            id = savedUser.getId(),
            username = savedUser.username,
            name = savedUser.name,
            paymentMethods = savedUser.paymentMethods.map {
                PaymentMethodInfo(
                    id = it.getId(),
                    type = it.type,
                    company = it.company,
                    number = it.number
                )
            }
        )
    }
}