package co.com.franchises.infrastructure.reactive_web.franchise;

import co.com.franchises.domain.model.branch.entities.Branch;
import co.com.franchises.domain.model.franchise.entities.Franchise;
import co.com.franchises.domain.usecase.branch.BranchUseCase;
import co.com.franchises.domain.usecase.franchise.FranchiseUseCase;
import co.com.franchises.infrastructure.reactive_web.common.dto.NameUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseUseCase franchiseUseCase;
    private final BranchUseCase branchUseCase;

    @PostMapping
    public Mono<Franchise> createFranchise(@Valid @RequestBody Franchise franchise) {
        return franchiseUseCase.saveFranchise(franchise);
    }

    @PostMapping("/{franchiseId}/branches")
    public Mono<Branch> addBranchToFranchise(@PathVariable String franchiseId, @RequestBody Branch branch) {
        return branchUseCase.addBranchToFranchise(franchiseId, branch);
    }

    @PatchMapping("/{franchiseId}/name")
    public Mono<Franchise> updateFranchiseName(@PathVariable String franchiseId, @RequestBody NameUpdateDto nameUpdateDto) {
        return franchiseUseCase.updateFranchiseName(franchiseId, nameUpdateDto.getName());
    }
}
