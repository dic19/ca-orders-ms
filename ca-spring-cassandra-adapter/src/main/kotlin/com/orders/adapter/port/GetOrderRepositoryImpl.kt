package com.orders.adapter.port

import com.orders.adapter.repository.OrderEntityRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.exception.UseCaseExecutionException
import com.orders.usecase.port.GetOrderRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetOrderRepositoryImpl(private val orderRepository: OrderEntityRepository) : GetOrderRepository {

    private val logger = LoggerFactory.getLogger(GetOrderRepositoryImpl::class.java)

    override fun get(owner: Owner, uniqueID: UUID): Order {
        return try {
            this.orderRepository.findByOwnerTypeAndOwnerIDAndUniqueID(owner.type, owner.uniqueID, uniqueID)
                .map(EntityDomainAdapter::toOrder)
                .orElseThrow {
                    logger.error("Order with unique ID [$uniqueID] not found!")
                    OrderNotFoundException(uniqueID.toString())
                }
        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception retrieving order with id $uniqueID: $ex", ex)
            throw UseCaseExecutionException("Unexpected exception retrieving order with id $uniqueID", ex)
        }
    }
}