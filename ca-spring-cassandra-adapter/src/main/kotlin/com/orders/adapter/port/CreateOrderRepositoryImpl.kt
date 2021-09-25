package com.orders.adapter.port

import com.orders.adapter.entity.OrderByReferenceEntity
import com.orders.adapter.entity.OrderEntity
import com.orders.adapter.repository.OrderByReferenceEntityRepository
import com.orders.adapter.repository.OrderEntityRepository
import com.orders.domain.Order
import com.orders.domain.Owner
import com.orders.usecase.exception.UseCaseExecutionException
import com.orders.usecase.port.CreateOrderRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CreateOrderRepositoryImpl(
    private val orderRepository: OrderEntityRepository,
    private val orderByReferenceRepository: OrderByReferenceEntityRepository
) : CreateOrderRepository {

    private val logger = LoggerFactory.getLogger(CreateOrderRepositoryImpl::class.java)

    override fun create(owner: Owner, order: Order): Order {
        return try {
            val orderByReferenceEntity = OrderByReferenceEntity(
                ownerType = owner.type,
                ownerID = owner.uniqueID,
                referenceID = order.referenceID,
                uniqueID = order.uniqueID
            )

            this.orderByReferenceRepository.save(orderByReferenceEntity)

            val orderEntity = OrderEntity(
                country = order.country,
                currency = order.currency,
                ownerType = owner.type,
                ownerID = owner.uniqueID,
                products = order.products.stream().map(DomainEntityAdapter::toProductType).collect(Collectors.toSet()),
                referenceID = order.referenceID,
                shippingRequired = order.shippingRequired,
                status = order.status,
                uniqueID = order.uniqueID
            )

            order.shipping.ifPresent { shipping ->
                orderEntity.shipping = DomainEntityAdapter.toShippingType(shipping)
            }

            EntityDomainAdapter.toOrder(this.orderRepository.save(orderEntity))

        } catch (ex: DataAccessException) {
            logger.error("Unexpected exception saving new order with id ${order.uniqueID}: $ex")
            throw UseCaseExecutionException("Unexpected exception saving new order with id ${order.uniqueID}", ex)
        }
    }
}