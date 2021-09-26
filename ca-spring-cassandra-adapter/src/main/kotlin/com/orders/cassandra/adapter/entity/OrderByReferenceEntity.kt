package com.orders.cassandra.adapter.entity

import com.orders.domain.OwnerType
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table("orders_by_reference_id")
data class OrderByReferenceEntity(
    @PrimaryKeyColumn(name = "owner_type", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val ownerType: OwnerType,
    @PrimaryKeyColumn(name = "owner_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    val ownerID: UUID,
    @PrimaryKeyColumn(name = "reference_id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    val referenceID: String,
    val uniqueID: UUID
) {

    override fun hashCode(): Int {
        return uniqueID.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return Objects.equals(this.hashCode(), other?.hashCode())
    }
}
