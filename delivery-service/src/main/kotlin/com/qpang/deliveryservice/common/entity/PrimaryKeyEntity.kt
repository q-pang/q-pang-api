package com.qpang.deliveryservice.common.entity

import com.github.f4b6a3.ulid.UlidCreator
import org.hibernate.proxy.HibernateProxy
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class PrimaryKeyEntity {
    @Id
    @Column(name = "id")
    private val id: String = UlidCreator.getMonotonicUlid().toUuid().toString()

    fun getId(): String = id

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }

        return id == getIdentifier(other)
    }

    private fun getIdentifier(obj: Any): Serializable {
        return if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier
        } else {
            (obj as PrimaryKeyEntity).id
        }
    }

    override fun hashCode() = Objects.hashCode(id)
}