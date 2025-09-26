package co.com.franchises.domain.usecase.product;

import co.com.franchises.domain.model.branch.gateway.BranchRepository;
import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.domain.model.product.gateway.ProductRepository;
import co.com.franchises.infrastructure.config.exception.BusinessException;
import co.com.franchises.infrastructure.config.exception.NotFoundException;
import co.com.franchises.infrastructure.mysq_repository.product.BranchProductMapper;
import co.com.franchises.infrastructure.mysq_repository.product.ProductMapper;
import co.com.franchises.infrastructure.mysq_repository.product.data.BranchProductData;
import co.com.franchises.infrastructure.mysq_repository.product.data.ProductData;
import co.com.franchises.infrastructure.reactive_web.branch.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ProductMapper productMapper;
    private final BranchProductMapper branchProductMapper;

    public Mono<Product> updateProductName(String productId, String newName) {
        return productRepository.updateName(productId, newName)
                .onErrorResume(ex -> handleProductError(ex, newName, "An error occurred while updating the product name"))
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found with id: " + productId)));
    }

    public Mono<Product> createProduct(String branchId, ProductRequest request) {
        String productId = UUID.randomUUID().toString();
        ProductData productData = productMapper.toData(productId, request);
        BranchProductData branchProductData = branchProductMapper.toData(branchId, productId, request.getStock());

        return branchRepository.findById(branchId)
                .flatMap(branch -> productRepository.saveProductAndBranchProduct(productData, branchProductData))
                .onErrorResume(ex -> handleProductError(ex, request.getName(), "An error occurred while creating the product"))
                .switchIfEmpty(Mono.error(new NotFoundException("Branch not found with id: " + branchId)));
    }

    public Mono<Void> removeProductFromBranch(String branchId, String productId) {
        return branchRepository.findById(branchId)
                .flatMap(branch -> productRepository.deleteBranchProduct(branchId, productId));
    }

    public Mono<Product> updateProductStockInBranch(String branchId, String productId, Integer newStock) {
        return branchRepository.findById(branchId)
                .flatMap(branch -> productRepository.updateStockInBranch(branchId, productId, newStock))
                .switchIfEmpty(Mono.defer(()-> Mono.error(new NotFoundException("Branch not found with id: " + branchId))));
    }

    private Mono<Product> handleProductError(Throwable ex, String name, String defaultErrorMessage) {
        if (ex instanceof org.springframework.dao.DuplicateKeyException) {
            return Mono.error(new BusinessException("Product name already exists: " + name));
        }
        return Mono.error(new BusinessException(defaultErrorMessage));
    }
}
