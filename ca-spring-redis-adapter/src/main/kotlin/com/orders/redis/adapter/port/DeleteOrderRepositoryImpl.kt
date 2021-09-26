package com.orders.redis.adapter.port

import com.orders.redis.adapter.repository.OrderEntityRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.exception.UseCaseExecutionException
import com.orders.usecase.port.DeleteOrderRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeleteOrderRepositoryImpl(private val repository: OrderEntityRepository) : DeleteOrderRepository {

    private val logger = LoggerFactory.getLogger(DeleteOrderRepositoryImpl::class.java)

    override fun delete(owner: Owner, uniqueID: UUID): Order {
        return try {
            val orderEntity = this.repository
                .findByOwnerTypeAndOwnerIDAndUniqueID(owner.type, owner.uniqueID, uniqueID)
                .orElseThrow {
                    logger.error("Order with unique ID [$uniqueID] not found!")
                    OrderNotFoundException(uniqueID.toString())
                }
            this.repository.delete(orderEntity)
            EntityDomainAdapter.toOrder(orderEntity)
        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception removing order with id $uniqueID: $ex")
            throw UseCaseExecutionException("Unexpected exception removing order with id $uniqueID", ex)
        }
    }
}