package com.qpang.userservice.domain

import com.qpang.userservice.application.port.`in`.usecase.UpdateUserInfoUseCase
import com.qpang.userservice.common.entity.JpaAuditEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    username: String,
    password: String,
    name: String
) : JpaAuditEntity() {
    @Column(name = "username", unique = true, nullable = false)
    var username: String = username
        protected set

    @Column(name = "password", nullable = false)
    var password: String = password
        protected set

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var paymentMethods: MutableList<PaymentMethod> = mutableListOf()

    fun isCorrectPassword(passwordEncoder: BCryptPasswordEncoder, password: String): Boolean =
        passwordEncoder.matches(password, this.password)

    fun updateInfo(command: UpdateUserInfoUseCase.UpdateUserCommand) {
        name = command.name
    }

    fun registerPaymentMethod(newPaymentMethod: PaymentMethod) {
        paymentMethods.add(newPaymentMethod)
    }

    fun deletePaymentMethod(paymentMethodId: String) {
        paymentMethods.removeIf { it.getId() == paymentMethodId }
    }
}