package co.com.franchises.domain.usecase.franchise;

import co.com.franchises.domain.model.franchise.entities.Franchise;
import co.com.franchises.domain.model.franchise.gateway.FranchiseRepository;
import co.com.franchises.infrastructure.config.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> saveFranchise(Franchise franchise) {
        franchise.setId(UUID.randomUUID().toString());
        return franchiseRepository.save(franchise)
                .onErrorResume(ex -> handleFranchiseError(ex, franchise.getName(), "An error occurred while saving the franchise"));
    }

    public Mono<Franchise> updateFranchiseName(String franchiseId, String newName) {
        return franchiseRepository.updateName(franchiseId, newName)
                .switchIfEmpty(Mono.error(new BusinessException("Franchise not found with id: " + franchiseId)))
                .onErrorResume(ex -> handleFranchiseError(ex, newName, "An error occurred while updating the franchise name"));
    }

    private Mono<Franchise> handleFranchiseError(Throwable ex, String franchiseName, String defaultErrorMessage) {
        if (ex instanceof org.springframework.dao.DuplicateKeyException) {
            return Mono.error(new BusinessException("Franchise name already exists: " + franchiseName));
        }
        return Mono.error(new BusinessException(defaultErrorMessage));
    }
}