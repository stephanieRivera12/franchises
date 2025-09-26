package co.com.franchises.domain.usecase.product;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.domain.model.product.gateway.ProductRepository;
import co.com.franchises.infrastructure.config.exception.BusinessException;
import co.com.franchises.infrastructure.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductRepository productRepository;

    public Mono<Product> updateProductName(String productId, String newName) {
        return productRepository.updateName(productId, newName)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with id: " + productId)))
                .onErrorResume(ex -> handleProductError(ex, newName, "An error occurred while updating the product name"));
    }

    private Mono<Product> handleProductError(Throwable ex, String name, String defaultErrorMessage) {
        if (ex instanceof org.springframework.dao.DuplicateKeyException) {
            return Mono.error(new BusinessException("Product name already exists: " + name));
        }
        return Mono.error(new BusinessException(defaultErrorMessage));
    }
}
