package co.com.franchises.infrastructure.reactive_web.branch;

import co.com.franchises.domain.model.branch.entities.Branch;
import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.domain.usecase.branch.BranchUseCase;
import co.com.franchises.domain.usecase.product.ProductUseCase;
import co.com.franchises.infrastructure.reactive_web.branch.dto.ProductRequest;
import co.com.franchises.infrastructure.reactive_web.branch.dto.StockUpdateDto;
import co.com.franchises.infrastructure.reactive_web.common.dto.NameUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchUseCase branchUseCase;
    private final ProductUseCase productUseCase;

    @PatchMapping("/{branchId}/name")
    public Mono<Branch> updateBranchName(@PathVariable String branchId, @RequestBody NameUpdateDto dto) {
        return branchUseCase.updateBranchName(branchId, dto.getName());
    }

    @PostMapping("/{branchId}/products")
    public Mono<Product> addProductToBranch(@PathVariable String branchId, @RequestBody ProductRequest productRequest) {
        return productUseCase.createProduct(branchId, productRequest);
    }

    @DeleteMapping("/{branchId}/products/{productId}")
    public Mono<Void> removeProductFromBranch(@PathVariable String branchId, @PathVariable String productId) {
        return productUseCase.removeProductFromBranch(branchId, productId);
    }

    @PatchMapping("/{branchId}/products/{productId}/stock")
    public Mono<Product> updateProductStockInBranch(@PathVariable String branchId, @PathVariable String productId, @RequestBody StockUpdateDto dto) {
        return productUseCase.updateProductStockInBranch(branchId, productId, dto.getStock());
    }


}
