package com.orders.jpa.adapter.repository

import com.orders.jpa.adapter.entity.OrderEntity
import com.orders.domain.OwnerType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigInteger
import java.util.*

@Repository
interface OrderEntityRepository : JpaRepository<OrderEntity, BigInteger> {

    @Query(value = "select o from OrderEntity o where o.ownerType = :ownerType and o.ownerID = :ownerID and o.uniqueID = :uniqueID")
    fun findByOwnerAndUniqueID(ownerType: OwnerType, ownerID: UUID, uniqueID: UUID): Optional<OrderEntity>

    @Query(value = "select o from OrderEntity o where o.ownerType = :ownerType and o.ownerID = :ownerID and o.referenceID = :referenceID")
    fun findByOwnerAndReferenceID(ownerType: OwnerType, ownerID: UUID, referenceID: String): Optional<OrderEntity>
}