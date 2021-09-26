package com.orders.redis.adapter.port

import com.orders.redis.adapter.repository.OrderEntityRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.domain.Product
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.exception.UseCaseExecutionException
import com.orders.usecase.port.AddProductsRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class AddProductsRepositoryImpl(private val repository: OrderEntityRepository) : AddProductsRepository {

    private val logger = LoggerFactory.getLogger(AddProductsRepositoryImpl::class.java)

    override fun addProducts(owner: Owner, uniqueID: UUID, products: MutableSet<Product>): Order {
        return try {
            val orderEntity =
                this.repository.findByOwnerTypeAndOwnerIDAndUniqueID(owner.type, owner.uniqueID, uniqueID).orElseThrow {
                    logger.error("Order with unique ID [$uniqueID] not found!")
                    OrderNotFoundException(uniqueID.toString())
                }

            products.forEach { product ->
                orderEntity.products.add(DomainEntityAdapter.toProductEntity(product))
            }

            orderEntity.updatedAt = LocalDateTime.now()

            EntityDomainAdapter.toOrder(this.repository.save(orderEntity))

        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception adding products to order with id $uniqueID: $ex")
            throw UseCaseExecutionException("Unexpected exception adding products to order with id $uniqueID", ex)
        }
    }
}