package com.orders.redis.adapter.repository

import com.orders.redis.adapter.entity.OrderEntity
import com.orders.domain.OwnerType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderEntityRepository : CrudRepository<OrderEntity, String> {

    fun findByOwnerTypeAndOwnerIDAndUniqueID(ownerType: OwnerType, ownerID: UUID, uniqueID: UUID): Optional<OrderEntity>

    fun findByOwnerTypeAndOwnerIDAndReferenceID(ownerType: OwnerType, ownerID: UUID, referenceID: String): Optional<OrderEntity>
}