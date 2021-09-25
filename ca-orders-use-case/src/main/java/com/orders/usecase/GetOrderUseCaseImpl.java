package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;
import com.orders.usecase.exception.FieldsValidationException;
import com.orders.usecase.exception.InvalidCredentialsException;
import com.orders.usecase.port.AuthenticationGateway;
import com.orders.usecase.port.GetOrderRepository;

import java.util.Objects;
import java.util.UUID;

import static com.orders.usecase.validation.Validators.notBlankValidator;
import static com.orders.usecase.validation.Validators.notNullValidator;

public class GetOrderUseCaseImpl implements GetOrderUseCase {

    private final AuthenticationGateway authenticationGateway;
    private final GetOrderRepository orderRepository;

    public GetOrderUseCaseImpl(AuthenticationGateway authenticationGateway, GetOrderRepository orderRepository) {
        this.authenticationGateway = authenticationGateway;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(final Credentials credentials, final UUID uniqueID) {
        if (Objects.isNull(credentials) || notBlankValidator("credentials", credentials.getValue()).rejected()) {
            throw new InvalidCredentialsException(credentials);
        }

        final var uniqueIDValidator = notNullValidator("uniqueID", uniqueID);
        if (uniqueIDValidator.rejected()) {
            throw new FieldsValidationException(uniqueIDValidator.validate());
        }

        final var owner = this.authenticationGateway.validateAndGet(credentials);
        return this.orderRepository.get(owner, uniqueID);
    }
}
