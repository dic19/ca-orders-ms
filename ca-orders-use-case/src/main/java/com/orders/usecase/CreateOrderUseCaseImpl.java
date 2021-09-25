package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Currency;
import com.orders.domain.Order;
import com.orders.domain.OrderEventType;
import com.orders.usecase.exception.FieldsValidationException;
import com.orders.usecase.exception.InvalidCredentialsException;
import com.orders.usecase.port.AuthenticationGateway;
import com.orders.usecase.port.CreateOrderRepository;
import com.orders.usecase.port.OrderEventSender;

import java.util.Objects;

import static com.orders.usecase.validation.Validators.*;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final AuthenticationGateway authenticationGateway;
    private final CreateOrderRepository orderRepository;
    private final OrderEventSender eventSender;

    public CreateOrderUseCaseImpl(AuthenticationGateway authenticationGateway, CreateOrderRepository orderRepository, OrderEventSender eventSender) {
        this.authenticationGateway = authenticationGateway;
        this.orderRepository = orderRepository;
        this.eventSender = eventSender;
    }

    @Override
    public Order execute(final Credentials credentials, final Order order) {
        if (Objects.isNull(credentials) || notBlankValidator("credentials", credentials.getValue()).rejected()) {
            throw new InvalidCredentialsException(credentials);
        }

        final var orderValidator = notNullValidator("order", order);
        if (orderValidator.rejected()) {
            throw new FieldsValidationException(orderValidator.validate());
        }

        final var referenceIDValidator = notBlankValidator("referenceID", order.getReferenceID());
        if (referenceIDValidator.rejected()) {
            throw new FieldsValidationException(referenceIDValidator.validate());
        }

        final var owner = this.authenticationGateway.validateAndGet(credentials);

        final var currency = Objects.equals(Currency.NOOP, order.getCurrency())
            ? owner.getCountry().getDefaultCurrency()
            : order.getCurrency();
        final var currencyValidator = inListValidator("currency", currency, owner.getCountry().getAllowedCurrencies());
        if (currencyValidator.rejected()) {
            throw new FieldsValidationException(currencyValidator.validate());
        }

        final var orderToSave = Order.builder(order)
            .withCountry(owner.getCountry())
            .withCurrency(currency)
            .withOwner(owner)
            .build();

        final var newOrder = this.orderRepository.create(owner, orderToSave);
        this.eventSender.send(OrderEventType.CREATED, newOrder);
        return newOrder;
    }
}
