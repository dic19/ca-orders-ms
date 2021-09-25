package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;
import com.orders.domain.OrderEventType;
import com.orders.usecase.exception.FieldsValidationException;
import com.orders.usecase.exception.InvalidCredentialsException;
import com.orders.usecase.port.AuthenticationGateway;
import com.orders.usecase.port.OrderEventSender;
import com.orders.usecase.port.UpdateOrderRepository;

import java.util.Objects;
import java.util.UUID;

import static com.orders.usecase.validation.Validators.notBlankValidator;
import static com.orders.usecase.validation.Validators.notNullValidator;

public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {

    private final AuthenticationGateway authenticationGateway;
    private final UpdateOrderRepository orderRepository;
    private final OrderEventSender eventSender;

    public UpdateOrderUseCaseImpl(AuthenticationGateway authenticationGateway, UpdateOrderRepository orderRepository, OrderEventSender eventSender) {
        this.authenticationGateway = authenticationGateway;
        this.orderRepository = orderRepository;
        this.eventSender = eventSender;
    }

    @Override
    public Order execute(final Credentials credentials, final UUID uniqueID, final Order order) {
        if (Objects.isNull(credentials) || notBlankValidator("credentials", credentials.getValue()).rejected()) {
            throw new InvalidCredentialsException(credentials);
        }

        final var orderValidator = notNullValidator("order", order);
        if (orderValidator.rejected()) {
            throw new FieldsValidationException(orderValidator.validate());
        }

        final var owner = this.authenticationGateway.validateAndGet(credentials);
        final var orderToUpdate = Order.builder(order)
            .withCountry(owner.getCountry())
            .withOwner(owner)
            .build();

        final var newOrder = this.orderRepository.update(owner, uniqueID, orderToUpdate);
        this.eventSender.send(OrderEventType.UPDATED, newOrder);
        return newOrder;
    }
}
