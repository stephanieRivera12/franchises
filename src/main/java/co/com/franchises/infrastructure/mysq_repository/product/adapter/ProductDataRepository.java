package co.com.franchises.infrastructure.mysq_repository.product.adapter;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductDataWithBranch;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ProductDataRepository extends R2dbcRepository<Product, String> {
    @Modifying
    @Query("UPDATE product SET name = :newName WHERE id = :productId")
    Mono<Integer> updateProductName(String productId, String newName);

    @Query("SELECT p.id, p.name, bp.branch_id, bp.stock " +
            "FROM product p " +
            "JOIN branch_product bp ON p.id = bp.product_id " +
            "WHERE p.id = :productId")
    Mono<ProductDataWithBranch> findProductWithBranchInfo(String productId);
}
