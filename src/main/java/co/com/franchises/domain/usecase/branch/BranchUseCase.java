package co.com.franchises.domain.usecase.branch;

import co.com.franchises.domain.model.branch.entities.Branch;
import co.com.franchises.domain.model.branch.gateway.BranchRepository;
import co.com.franchises.domain.model.franchise.gateway.FranchiseRepository;
import co.com.franchises.infrastructure.config.exception.BusinessException;
import co.com.franchises.infrastructure.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchUseCase {
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    public Mono<Branch> addBranchToFranchise(String franchiseId, Branch branch) {
        branch.setId(UUID.randomUUID().toString());
        branch.setFranchiseId(franchiseId);

        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> branchRepository.save(branch))
                .onErrorResume(ex -> handleBranchError(ex, branch.getName()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("Franchise not found with id: " + franchiseId))));
    }

    public Mono<Branch> updateBranchName(String branchId, String newName) {
        return branchRepository.updateName(branchId, newName)
                .onErrorResume(ex -> handleBranchError(ex, newName))
                .switchIfEmpty(Mono.error(new NotFoundException("Branch not found with id: " + branchId)));
    }

    private Mono<Branch> handleBranchError(Throwable ex, String branchName) {
        if (ex instanceof org.springframework.dao.DuplicateKeyException) {
            return Mono.error(new BusinessException("Branch name already exists in this franchise: " + branchName));
        }
        return Mono.error(new BusinessException("An error occurred while saving the branch"));
    }
}
