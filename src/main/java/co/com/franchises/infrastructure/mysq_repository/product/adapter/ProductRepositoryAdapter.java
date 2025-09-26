package co.com.franchises.infrastructure.mysq_repository.product.adapter;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.domain.model.product.gateway.ProductRepository;
import co.com.franchises.infrastructure.mysq_repository.product.ProductMapper;
import co.com.franchises.infrastructure.mysq_repository.product.data.BranchProductData;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductData;
import co.com.franchises.infrastructure.reactive_web.franchise.dto.TopStockProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductDataRepository productDataRepository;
    private final BranchProductDataRepository branchProductDataRepository;
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

    @Override
    @Transactional
    public Mono<Void> deleteBranchProduct(String branchId, String productId) {
        return branchProductDataRepository.deleteByBranchIdAndProductId(branchId, productId)
                .then(branchProductDataRepository.countByProductId(productId))
                .flatMap(count -> {
                    if (count == 0) {
                        return productDataRepository.deleteById(productId);
                    }
                    return Mono.empty();
                })
                .then();
    }

    @Override
    public Mono<Product> updateStockInBranch(String branchId, String productId, Integer newStock) {
        return branchProductDataRepository.updateProductStockInBranch(branchId, productId, newStock)
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
    public Flux<TopStockProductDto> findTopStockProductsByFranchiseId(String franchiseId) {
        String sql = String.format("""
        SELECT branch_id, branch_name, product_id, product_name, stock
        FROM (
            SELECT
                b.id AS branch_id,
                b.name AS branch_name,
                p.id AS product_id,
                p.name AS product_name,
                bp.stock,
                ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY bp.stock DESC) as rn
            FROM branch b
            JOIN branch_product bp ON b.id = bp.branch_id
            JOIN product p ON bp.product_id = p.id
            WHERE b.franchise_id = '%s'
        ) ranked
        WHERE ranked.rn = 1
        """, franchiseId);

        return template.getDatabaseClient()
                .sql(sql)
                .map((row, metadata) -> new TopStockProductDto(
                        row.get("branch_id", String.class),
                        row.get("branch_name", String.class),
                        row.get("product_id", String.class),
                        row.get("product_name", String.class),
                        row.get("stock", Integer.class)
                ))
                .all();
    }

}