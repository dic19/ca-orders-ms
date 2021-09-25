package com.orders.adapter.port

import com.orders.adapter.repository.OrderByReferenceEntityRepository
import com.orders.adapter.repository.OrderEntityRepository
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
class DeleteOrderRepositoryImpl(
    private val orderRepository: OrderEntityRepository,
    private val orderByReferenceRepository: OrderByReferenceEntityRepository
) : DeleteOrderRepository {

    private val logger = LoggerFactory.getLogger(DeleteOrderRepositoryImpl::class.java)

    override fun delete(owner: Owner, uniqueID: UUID): Order {
        return try {
            val orderEntity = this.orderRepository
                .findByOwnerTypeAndOwnerIDAndUniqueID(owner.type, owner.uniqueID, uniqueID)
                .orElseThrow {
                    logger.error("Order with unique ID [$uniqueID] not found!")
                    OrderNotFoundException(uniqueID.toString())
                }

            this.orderRepository.delete(orderEntity)

            this.orderByReferenceRepository
                .findByOwnerTypeAndOwnerIDAndReferenceID(owner.type, owner.uniqueID, orderEntity.referenceID)
                .ifPresent(this.orderByReferenceRepository::delete)

            EntityDomainAdapter.toOrder(orderEntity)

        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception removing order with id $uniqueID: $ex")
            throw UseCaseExecutionException("Unexpected exception removing order with id $uniqueID", ex)
        }
    }
}