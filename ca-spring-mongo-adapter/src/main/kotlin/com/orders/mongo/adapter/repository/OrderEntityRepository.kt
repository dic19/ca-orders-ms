package com.orders.mongo.adapter.repository

import com.orders.mongo.adapter.entity.OrderEntity
import com.orders.domain.OwnerType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderEntityRepository : MongoRepository<OrderEntity, String> {

    @Query("{ 'owner_type': ?0, 'owner_id': ?1, 'unique_id': ?2 }")
    fun findByOwnerAndUniqueID(ownerType: OwnerType, ownerID: UUID, uniqueID: UUID): Optional<OrderEntity>

    @Query("{ 'owner_type': ?0, 'owner_id': ?1, 'reference_id': ?2 }")
    fun findByOwnerAndReferenceID(ownerType: OwnerType, ownerID: UUID, referenceID: String): Optional<OrderEntity>
}