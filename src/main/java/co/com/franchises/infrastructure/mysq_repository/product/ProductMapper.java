package co.com.franchises.infrastructure.mysq_repository.product;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductDataWithBranch;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductDataWithBranch productDataWithBranch) {
        return new Product(
                productDataWithBranch.getId(),
                productDataWithBranch.getName(),
                productDataWithBranch.getBranchId(),
                productDataWithBranch.getStock()
        );
    }
}
