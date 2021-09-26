package com.orders.cassandra.adapter.port

import com.orders.cassandra.adapter.repository.OrderByReferenceEntityRepository
import com.orders.cassandra.adapter.repository.OrderEntityRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.exception.UseCaseExecutionException
import com.orders.usecase.port.FindOrderRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service

@Service
class FindOrderRepositoryImpl(
    private val orderRepository: OrderEntityRepository,
    private val orderByReferenceRepository: OrderByReferenceEntityRepository
) : FindOrderRepository {

    private val logger = LoggerFactory.getLogger(FindOrderRepositoryImpl::class.java)

    override fun findByReferenceID(owner: Owner, referenceID: String): Order {
        return try {
            val orderByReferenceEntity = this.orderByReferenceRepository
                .findByOwnerTypeAndOwnerIDAndReferenceID(owner.type, owner.uniqueID, referenceID)
                .orElseThrow {
                    logger.error("Order with reference [$referenceID] not found!")
                    OrderNotFoundException(referenceID)
                }

            this.orderRepository
                .findByOwnerTypeAndOwnerIDAndUniqueID(owner.type, owner.uniqueID, orderByReferenceEntity.uniqueID)
                .map(EntityDomainAdapter::toOrder)
                .orElseThrow {
                    logger.error("Order with reference [$referenceID] not found!")
                    OrderNotFoundException(referenceID)
                }

        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception retrieving order with referenceID $referenceID: $ex")
            throw UseCaseExecutionException("Unexpected exception retrieving order with referenceID $referenceID", ex)
        }
    }
}