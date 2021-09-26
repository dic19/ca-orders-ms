package com.orders.cassandra.adapter.repository

import com.orders.cassandra.adapter.entity.OrderByReferenceEntity
import com.orders.domain.OwnerType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderByReferenceEntityRepository : CrudRepository<OrderByReferenceEntity, String> {

    fun findByOwnerTypeAndOwnerIDAndReferenceID(ownerType: OwnerType, ownerID: UUID, referenceID: String): Optional<OrderByReferenceEntity>
}