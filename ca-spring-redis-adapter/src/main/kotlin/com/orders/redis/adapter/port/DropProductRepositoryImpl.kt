package com.orders.redis.adapter.port

import com.orders.redis.adapter.repository.OrderEntityRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.exception.ProductNotFoundException
import com.orders.usecase.exception.UseCaseExecutionException
import com.orders.usecase.port.DropProductRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class DropProductRepositoryImpl(private val repository: OrderEntityRepository) : DropProductRepository {

    private val logger = LoggerFactory.getLogger(DropProductRepositoryImpl::class.java)

    override fun dropProduct(owner: Owner, uniqueID: UUID, productCode: String): Order {
        return try {
            val orderEntity = this.repository.findByOwnerTypeAndOwnerIDAndUniqueID(owner.type, owner.uniqueID, uniqueID).orElseThrow {
                logger.error("Order with unique ID [$uniqueID] not found!")
                OrderNotFoundException(uniqueID.toString())
            }

            if (orderEntity.products.removeIf { it.code == productCode }.not()) {
                logger.error("Product with code [$productCode] not found in order with unique ID [$uniqueID]!")
                throw ProductNotFoundException(uniqueID, productCode)
            }

            orderEntity.updatedAt = LocalDateTime.now()

            EntityDomainAdapter.toOrder(this.repository.save(orderEntity))

        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception removing product $productCode from order with id $uniqueID: $ex")
            throw UseCaseExecutionException(
                "Unexpected exception removing product $productCode from order with id $uniqueID", ex
            )
        }
    }
}