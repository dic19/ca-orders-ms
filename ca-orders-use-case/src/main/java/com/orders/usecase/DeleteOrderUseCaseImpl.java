package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.OrderEventType;
import com.orders.usecase.exception.FieldsValidationException;
import com.orders.usecase.exception.InvalidCredentialsException;
import com.orders.usecase.port.AuthenticationGateway;
import com.orders.usecase.port.DeleteOrderRepository;
import com.orders.usecase.port.OrderEventSender;

import java.util.Objects;
import java.util.UUID;

import static com.orders.usecase.validation.Validators.notBlankValidator;
import static com.orders.usecase.validation.Validators.notNullValidator;

public class DeleteOrderUseCaseImpl implements DeleteOrderUseCase {

    private final AuthenticationGateway authenticationGateway;
    private final DeleteOrderRepository orderRepository;
    private final OrderEventSender eventSender;

    public DeleteOrderUseCaseImpl(AuthenticationGateway authenticationGateway, DeleteOrderRepository orderRepository, OrderEventSender eventSender) {
        this.authenticationGateway = authenticationGateway;
        this.orderRepository = orderRepository;
        this.eventSender = eventSender;
    }

    @Override
    public void execute(final Credentials credentials, final UUID uniqueID) {
        if (Objects.isNull(credentials) || notBlankValidator("credentials", credentials.getValue()).rejected()) {
            throw new InvalidCredentialsException(credentials);
        }

        final var uniqueIDValidator = notNullValidator("uniqueID", uniqueID);
        if (uniqueIDValidator.rejected()) {
            throw new FieldsValidationException(uniqueIDValidator.validate());
        }

        final var owner = this.authenticationGateway.validateAndGet(credentials);
        final var order = this.orderRepository.delete(owner, uniqueID);
        this.eventSender.send(OrderEventType.DELETED, order);
    }
}
