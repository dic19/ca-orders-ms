package com.orders.jpa.adapter.port

import com.orders.jpa.adapter.repository.OrderEntityRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.exception.ProductNotFoundException
import com.orders.usecase.exception.UseCaseExecutionException
import com.orders.usecase.port.DropProductRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DropProductRepositoryImpl(private val repository: OrderEntityRepository) : DropProductRepository {

    private val logger = LoggerFactory.getLogger(DropProductRepositoryImpl::class.java)

    @Transactional
    override fun dropProduct(owner: Owner, uniqueID: UUID, productCode: String): Order {
        return try {
            val orderEntity = this.repository.findByOwnerAndUniqueID(owner.type, owner.uniqueID, uniqueID).orElseThrow {
                logger.error("Order with unique ID [$uniqueID] not found!")
                OrderNotFoundException(uniqueID.toString())
            }

            val productEntity = orderEntity.products.stream()
                .filter { it.code == productCode }
                .findFirst()
                .orElseThrow {
                    logger.error("Product with code [$productCode] not found in order with unique ID [$uniqueID]!")
                    ProductNotFoundException(uniqueID, productCode)
                }

            productEntity.order = null
            orderEntity.products.remove(productEntity)

            EntityDomainAdapter.toOrder(this.repository.save(orderEntity))

        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception removing product $productCode from order with id $uniqueID: $ex")
            throw UseCaseExecutionException(
                "Unexpected exception removing product $productCode from order with id $uniqueID", ex
            )
        }
    }
}