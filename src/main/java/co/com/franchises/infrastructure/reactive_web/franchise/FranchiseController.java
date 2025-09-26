package co.com.franchises.infrastructure.reactive_web.franchise;

import co.com.franchises.domain.model.branch.entities.Branch;
import co.com.franchises.domain.model.franchise.entities.Franchise;
import co.com.franchises.domain.usecase.branch.BranchUseCase;
import co.com.franchises.domain.usecase.franchise.FranchiseUseCase;
import co.com.franchises.domain.usecase.product.ProductUseCase;
import co.com.franchises.infrastructure.reactive_web.common.dto.NameUpdateDto;
import co.com.franchises.infrastructure.reactive_web.franchise.dto.TopStockProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseUseCase franchiseUseCase;
    private final BranchUseCase branchUseCase;
    private final ProductUseCase productUseCase;

    @PostMapping
    public Mono<Franchise> createFranchise(@Valid @RequestBody Franchise franchise) {
        return franchiseUseCase.saveFranchise(franchise);
    }

    @PostMapping("/{franchiseId}/branches")
    public Mono<Branch> addBranchToFranchise(@PathVariable String franchiseId,  @Valid @RequestBody Branch branch) {
        return branchUseCase.addBranchToFranchise(franchiseId, branch);
    }

    @PatchMapping("/{franchiseId}/name")
    public Mono<Franchise> updateFranchiseName(@PathVariable String franchiseId, @Valid @RequestBody NameUpdateDto nameUpdateDto) {
        return franchiseUseCase.updateFranchiseName(franchiseId, nameUpdateDto.getName());
    }

    @GetMapping("/{franchiseId}/top-products")
    public Flux<TopStockProductDto> getTopSellingProducts(@PathVariable String franchiseId) {
        return productUseCase.getTopStockProducts(franchiseId);
    }

}
