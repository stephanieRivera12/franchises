package co.com.franchises.domain.model.product.gateway;

import co.com.franchises.domain.model.product.entities.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> updateName(String productId, String productName);
}
