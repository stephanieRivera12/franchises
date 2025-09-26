package co.com.franchises.infrastructure.mysq_repository.product.adapter;

import co.com.franchises.infrastructure.mysq_repository.product.data.BranchProductData;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface BranchProductDataRepository extends R2dbcRepository<BranchProductData, String> {
    Mono<Void> deleteByBranchIdAndProductId(String branchId, String productId);
    Mono<Long> countByProductId(String productId);
    @Modifying
    @Query("UPDATE branch_product SET stock = :newStock WHERE branch_id = :branchId AND product_id = :productId")
    Mono<Integer> updateProductStockInBranch(String branchId, String productId, Integer newStock);
}
