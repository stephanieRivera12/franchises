package co.com.franchises.domain.model.product.gateway;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.infrastructure.mysq_repository.product.data.BranchProductData;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductData;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> updateName(String productId, String productName);

    @org.springframework.transaction.annotation.Transactional
    Mono<Product> saveProductAndBranchProduct(ProductData productData, BranchProductData branchProductData);
}
