package co.com.franchises.infrastructure.mysq_repository.product;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.infrastructure.mysq_repository.product.data.BranchProductData;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductData;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductDataWithBranch;
import co.com.franchises.infrastructure.reactive_web.branch.dto.ProductRequest;
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

    public Product toEntity(ProductData productData, BranchProductData branchProductData) {
        return new Product(
                productData.getId(),
                productData.getName(),
                branchProductData.getBranchId(),
                branchProductData.getStock()
        );
    }

    public ProductData toData(String productId, ProductRequest request) {
        return new ProductData(
                productId,
                request.getName()
        );
    }
}
