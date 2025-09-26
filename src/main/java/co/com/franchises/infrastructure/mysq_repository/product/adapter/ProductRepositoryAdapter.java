package co.com.franchises.infrastructure.mysq_repository.product.adapter;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.domain.model.product.gateway.ProductRepository;
import co.com.franchises.infrastructure.mysq_repository.product.ProductMapper;
import co.com.franchises.infrastructure.mysq_repository.product.data.BranchProductData;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductDataRepository productDataRepository;
    private final ProductMapper productMapper;
    private final R2dbcEntityTemplate template;

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

    @Override
    @Transactional
    public Mono<Product> saveProductAndBranchProduct(ProductData productData, BranchProductData branchProductData) {
        return template.insert(productData)
                .flatMap(savedProduct -> template.insert(branchProductData)
                        .map(savedBranchProduct -> productMapper.toEntity(savedProduct, savedBranchProduct)));
    }

}