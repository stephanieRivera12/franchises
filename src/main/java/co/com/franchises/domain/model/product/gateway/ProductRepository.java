package co.com.franchises.domain.model.product.gateway;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.infrastructure.mysq_repository.product.data.BranchProductData;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductData;
import co.com.franchises.infrastructure.reactive_web.franchise.dto.TopStockProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> updateName(String productId, String productName);

    @org.springframework.transaction.annotation.Transactional
    Mono<Product> saveProductAndBranchProduct(ProductData productData, BranchProductData branchProductData);

    Mono<Void> deleteBranchProduct(String branchId, String productId);

    Mono<Product> updateStockInBranch(String branchId, String productId, Integer newStock);

    Flux<TopStockProductDto> findTopStockProductsByFranchiseId(String franchiseId);
}
