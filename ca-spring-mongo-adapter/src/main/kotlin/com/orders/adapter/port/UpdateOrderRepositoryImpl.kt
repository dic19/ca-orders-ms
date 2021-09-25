package com.orders.adapter.port

import com.orders.adapter.repository.OrderEntityRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.OrderNotFoundException
import com.orders.usecase.exception.UseCaseExecutionException
import com.orders.usecase.port.UpdateOrderRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

@Service
class UpdateOrderRepositoryImpl(private val repository: OrderEntityRepository) : UpdateOrderRepository {

    private val logger = LoggerFactory.getLogger(UpdateOrderRepositoryImpl::class.java)

    override fun update(owner: Owner, uniqueID: UUID, order: Order): Order {
        return try {
            val orderEntity = this.repository.findByOwnerAndUniqueID(owner.type, owner.uniqueID, uniqueID).orElseThrow {
                logger.error("Order with unique ID [$uniqueID] not found!")
                OrderNotFoundException(uniqueID.toString())
            }

            val products = order.products.stream()
                .map(DomainEntityAdapter::toProductEntity)
                .collect(Collectors.toSet())

            orderEntity.products.clear()
            orderEntity.products.addAll(products)
            orderEntity.shippingRequired = order.shippingRequired

            if (order.shipping.isEmpty) {
                orderEntity.shipping = null
            }

            if (order.shipping.isPresent && Objects.nonNull(orderEntity.shipping)) {
                orderEntity.shipping = DomainEntityAdapter.mapShippingData(order.shipping.get(), orderEntity.shipping!!)
            }

            if (order.shipping.isPresent && Objects.isNull(orderEntity.shipping)) {
                orderEntity.shipping = DomainEntityAdapter.toShippingEntity(order.shipping.get())
            }

            orderEntity.updatedAt = LocalDateTime.now()

            EntityDomainAdapter.toOrder(this.repository.save(orderEntity))

        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception updating order with id $uniqueID: $ex")
            throw UseCaseExecutionException("Unexpected exception updating order with id $uniqueID", ex)
        }
    }
}