package com.orders.adapter.repository

import com.orders.adapter.entity.OrderEntity
import com.orders.domain.OwnerType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderEntityRepository : CrudRepository<OrderEntity, String> {

    fun findByOwnerTypeAndOwnerIDAndUniqueID(ownerType: OwnerType, ownerID: UUID, uniqueID: UUID): Optional<OrderEntity>
}