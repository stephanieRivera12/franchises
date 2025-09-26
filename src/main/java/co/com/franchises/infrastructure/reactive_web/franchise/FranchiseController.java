package co.com.franchises.infrastructure.reactive_web.franchise;

import co.com.franchises.domain.model.franchise.entities.Franchise;
import co.com.franchises.domain.usecase.franchise.FranchiseUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseUseCase franchiseUseCase;

    @PostMapping
    public Mono<Franchise> createFranchise(@Valid @RequestBody Franchise franchise) {
        return franchiseUseCase.saveFranchise(franchise);
    }

    @PatchMapping("/{franchiseId}")
    public Mono<Franchise> updateFranchise(@PathVariable String franchiseId, @RequestBody Map<String, String> updateFields) {
        String newName = updateFields.get("name");
        return franchiseUseCase.updateFranchiseName(franchiseId, newName);
    }
}
