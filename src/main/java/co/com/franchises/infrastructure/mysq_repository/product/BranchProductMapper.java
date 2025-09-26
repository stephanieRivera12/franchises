package co.com.franchises.infrastructure.mysq_repository.product;

import co.com.franchises.infrastructure.mysq_repository.product.data.BranchProductData;
import org.springframework.stereotype.Component;

@Component
public class BranchProductMapper {
    public BranchProductData toData(String branchId, String productId, Integer stock) {
        return new BranchProductData(
                branchId,
                productId,
                stock
        );
    }
}
