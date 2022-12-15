package com.qpang.userservice.domain

import com.qpang.userservice.common.entity.JpaAuditEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

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

    fun isCorrectPassword(passwordEncoder: BCryptPasswordEncoder, password: String): Boolean =
        passwordEncoder.matches(password, this.password)
}