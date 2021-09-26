package com.orders.dummy.adapter

import com.orders.dummy.adapter.port.*
import com.orders.dummy.adapter.repository.OrderDummyRepository
import com.orders.usecase.port.*
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
class SpringDummyConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun authenticationGateway(): AuthenticationGateway {
        return AuthenticationGatewayImpl()
    }

    @Bean
    @ConditionalOnMissingBean
    fun orderEventSender(): OrderEventSender {
        return OrderEventSenderImpl()
    }

    @Bean
    fun orderDummyRepository(): OrderDummyRepository {
        return OrderDummyRepository()
    }

    @Bean
    @ConditionalOnMissingBean
    fun addProductsRepository(repository: OrderDummyRepository): AddProductsRepository {
        return AddProductsRepositoryImpl(repository)
    }

    @Bean
    @ConditionalOnMissingBean
    fun createOrderRepository(repository: OrderDummyRepository): CreateOrderRepository {
        return CreateOrderRepositoryImpl(repository)
    }

    @Bean
    @ConditionalOnMissingBean
    fun deleteOrderRepository(repository: OrderDummyRepository): DeleteOrderRepository {
        return DeleteOrderRepositoryImpl(repository)
    }

    @Bean
    @ConditionalOnMissingBean
    fun dropProductRepository(repository: OrderDummyRepository): DropProductRepository {
        return DropProductRepositoryImpl(repository)
    }

    @Bean
    @ConditionalOnMissingBean
    fun findOrderRepository(repository: OrderDummyRepository): FindOrderRepository {
        return FindOrderRepositoryImpl(repository)
    }

    @Bean
    @ConditionalOnMissingBean
    fun getOrderRepository(repository: OrderDummyRepository): GetOrderRepository {
        return GetOrderRepositoryImpl(repository)
    }

    @Bean
    @ConditionalOnMissingBean
    fun updateOrderRepository(repository: OrderDummyRepository): UpdateOrderRepository {
        return UpdateOrderRepositoryImpl(repository)
    }
}