package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;
import com.orders.usecase.exception.FieldsValidationException;
import com.orders.usecase.exception.InvalidCredentialsException;
import com.orders.usecase.port.AuthenticationGateway;
import com.orders.usecase.port.FindOrderRepository;

import java.util.Objects;

import static com.orders.usecase.validation.Validators.notBlankValidator;

public class FindOrderUseCaseImpl implements FindOrderUseCase {

    private final AuthenticationGateway authenticationGateway;
    private final FindOrderRepository orderRepository;

    public FindOrderUseCaseImpl(AuthenticationGateway authenticationGateway, FindOrderRepository orderRepository) {
        this.authenticationGateway = authenticationGateway;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(final Credentials credentials, final String referenceID) {
        if (Objects.isNull(credentials) || notBlankValidator("credentials", credentials.getValue()).rejected()) {
            throw new InvalidCredentialsException(credentials);
        }

        final var referenceIDValidator = notBlankValidator("referenceID", referenceID);
        if (referenceIDValidator.rejected()) {
            throw new FieldsValidationException(referenceIDValidator.validate());
        }

        final var owner = this.authenticationGateway.validateAndGet(credentials);
        return this.orderRepository.findByReferenceID(owner, referenceID);
    }
}
