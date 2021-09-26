package com.orders.cassandra.adapter.port

import com.orders.cassandra.adapter.repository.OrderEntityRepository
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
class AddProductsRepositoryImpl(private val orderRepository: OrderEntityRepository) : AddProductsRepository {

    private val logger = LoggerFactory.getLogger(AddProductsRepositoryImpl::class.java)

    override fun addProducts(owner: Owner, uniqueID: UUID, products: MutableSet<Product>): Order {
        return try {
            val orderEntity = this.orderRepository
                .findByOwnerTypeAndOwnerIDAndUniqueID(owner.type, owner.uniqueID, uniqueID)
                .orElseThrow {
                    logger.error("Order with unique ID [$uniqueID] not found!")
                    OrderNotFoundException(uniqueID.toString())
                }

            products.forEach { product ->
                orderEntity.products.add(DomainEntityAdapter.toProductType(product))
            }

            orderEntity.updatedAt = LocalDateTime.now()

            EntityDomainAdapter.toOrder(this.orderRepository.save(orderEntity))

        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception adding products to order with id $uniqueID: $ex")
            throw UseCaseExecutionException("Unexpected exception adding products to order with id $uniqueID", ex)
        }
    }
}