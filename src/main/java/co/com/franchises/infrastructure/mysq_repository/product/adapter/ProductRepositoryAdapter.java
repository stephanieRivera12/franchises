package co.com.franchises.infrastructure.mysq_repository.product.adapter;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.domain.model.product.gateway.ProductRepository;
import co.com.franchises.infrastructure.mysq_repository.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductDataRepository productDataRepository;
    private final ProductMapper productMapper;

    @Override
    public Mono<Product> updateName(String productId, String newName) {
        return productDataRepository.updateProductName(productId, newName)
                .flatMap(updatedRows -> {
                    if (updatedRows > 0) {
                        return productDataRepository.findProductWithBranchInfo(productId)
                                .map(productMapper::toEntity);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}