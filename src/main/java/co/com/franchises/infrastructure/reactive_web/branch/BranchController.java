package co.com.franchises.infrastructure.reactive_web.branch;

import co.com.franchises.domain.model.branch.entities.Branch;
import co.com.franchises.domain.usecase.branch.BranchUseCase;
import co.com.franchises.infrastructure.reactive_web.common.dto.NameUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchUseCase branchUseCase;

    @PatchMapping("/{branchId}/name")
    public Mono<Branch> updateBranchName(@PathVariable String branchId, @RequestBody NameUpdateDto dto) {
        return branchUseCase.updateBranchName(branchId, dto.getName());
    }


}
