package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;
import com.orders.domain.OrderEventType;
import com.orders.domain.Product;
import com.orders.usecase.exception.FieldsValidationException;
import com.orders.usecase.exception.InvalidCredentialsException;
import com.orders.usecase.port.AddProductsRepository;
import com.orders.usecase.port.AuthenticationGateway;
import com.orders.usecase.port.OrderEventSender;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.orders.usecase.validation.Validators.*;

public class AddProductsUseCaseImpl implements AddProductsUseCase {

    private final AuthenticationGateway authenticationGateway;
    private final AddProductsRepository productRepository;
    private final OrderEventSender eventSender;

    public AddProductsUseCaseImpl(AuthenticationGateway authenticationGateway, AddProductsRepository productRepository, OrderEventSender eventSender) {
        this.authenticationGateway = authenticationGateway;
        this.productRepository = productRepository;
        this.eventSender = eventSender;
    }

    @Override
    public Order execute(final Credentials credentials, final UUID uniqueID, final Set<Product> products) {
        if (Objects.isNull(credentials) || notBlankValidator("credentials", credentials.getValue()).rejected()) {
            throw new InvalidCredentialsException(credentials);
        }

        final var validator = compose(notNullValidator("uniqueID", uniqueID)
            , notEmptyValidator("products", products));

        if (validator.rejected()) {
            throw new FieldsValidationException(validator.validate());
        }

        final var owner = this.authenticationGateway.validateAndGet(credentials);
        final var order = this.productRepository.addProducts(owner, uniqueID, products);
        this.eventSender.send(OrderEventType.UPDATED, order);
        return order;
    }
}
