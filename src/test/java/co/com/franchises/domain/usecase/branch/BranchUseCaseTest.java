package co.com.franchises.domain.usecase.branch;

import co.com.franchises.domain.model.branch.entities.Branch;
import co.com.franchises.domain.model.branch.gateway.BranchRepository;
import co.com.franchises.domain.model.franchise.entities.Franchise;
import co.com.franchises.domain.model.franchise.gateway.FranchiseRepository;
import co.com.franchises.infrastructure.config.exception.BusinessException;
import co.com.franchises.infrastructure.config.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private BranchUseCase branchUseCase;

    @Test
    void addBranchToFranchise_shouldSaveBranch_whenFranchiseExists() {
        var nameBranch = "Branch1";
        var franchiseId = "franchiseId";
        var expectedFranchise = new Franchise("franchiseId", "Franchise1");
        var expectedBranch = new Branch("branchId", nameBranch, franchiseId);

        Branch branch = new Branch();
        branch.setName(nameBranch);
        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(expectedFranchise));
        when(branchRepository.save(branch)).thenReturn(Mono.just(expectedBranch));

        StepVerifier.create(branchUseCase.addBranchToFranchise("franchiseId", branch))
                .expectNext(expectedBranch)
                .expectComplete()
                .verify();

        verify(franchiseRepository, times(1)).findById(franchiseId);
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    void addBranchToFranchise_shouldThrowNotFoundException_whenFranchiseDoesNotExist() {
        var franchiseId = "franchiseId";
        Branch branch = new Branch();
        branch.setName("Branch1");
        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.addBranchToFranchise(franchiseId, branch))
                .expectErrorMatches(e -> e instanceof NotFoundException && e.getMessage().contains("Franchise not found"))
                .verify();

        verify(franchiseRepository, times(1)).findById(franchiseId);
        verify(branchRepository, never()).save(branch);
    }

    @Test
    void addBranchToFranchise_shouldThrowBusinessException_whenDuplicateKey() {
        var franchiseId = "franchiseId";
        Branch branch = new Branch();
        branch.setName("Branch1");
        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(new Franchise(franchiseId, "Franchise1")));
        when(branchRepository.save(branch)).thenReturn(Mono.error(new org.springframework.dao.DuplicateKeyException("Duplicate")));

        StepVerifier.create(branchUseCase.addBranchToFranchise(franchiseId, branch))
                .expectErrorMatches(e -> e instanceof BusinessException && e.getMessage().contains("already exists"))
                .verify();

        verify(franchiseRepository, times(1)).findById(franchiseId);
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    void addBranchToFranchise_shouldThrowBusinessException_whenOtherError() {
        var franchiseId = "franchiseId";
        Branch branch = new Branch();
        branch.setName("Branch1");
        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(new Franchise(franchiseId, "Franchise1")));
        when(branchRepository.save(any(Branch.class))).thenReturn(Mono.error(new RuntimeException("Other error")));

        StepVerifier.create(branchUseCase.addBranchToFranchise(franchiseId, branch))
                .expectErrorMatches(e -> e instanceof BusinessException && e.getMessage().contains("An error occurred"))
                .verify();

        verify(franchiseRepository, times(1)).findById(franchiseId);
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    void updateBranchName_shouldUpdateName_whenBranchExists() {
        var branchId = "branchId";
        var newName = "NewName";
        Branch branch = new Branch();
        branch.setName(newName);
        var expectedBranch = new Branch(branchId, newName, "franchiseId");

        when(branchRepository.updateName(branchId, newName)).thenReturn(Mono.just(expectedBranch));

        StepVerifier.create(branchUseCase.updateBranchName(branchId, newName))
                .expectNext(expectedBranch)
                .expectComplete()
                .verify();

        verify(branchRepository, times(1)).updateName(branchId, newName);
    }

    @Test
    void updateBranchName_shouldThrowNotFoundException_whenBranchDoesNotExist() {
        var branchId = "branchId";
        var newName = "NewName";
        when(branchRepository.updateName(branchId, newName)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateBranchName(branchId, newName))
                .expectErrorMatches(e -> e instanceof NotFoundException && e.getMessage().contains("Branch not found"))
                .verify();

        verify(branchRepository, times(1)).updateName(branchId, newName);
    }

    @Test
    void updateBranchName_shouldThrowBusinessException_whenDuplicateKey() {
        var branchId = "branchId";
        var newName = "NewName";
        when(branchRepository.updateName(branchId, newName)).thenReturn(Mono.error(new org.springframework.dao.DuplicateKeyException("Duplicate")));

        StepVerifier.create(branchUseCase.updateBranchName(branchId, newName))
                .expectErrorMatches(e -> e instanceof BusinessException && e.getMessage().contains("already exists"))
                .verify();

        verify(branchRepository, times(1)).updateName(branchId, newName);
    }

    @Test
    void updateBranchName_shouldThrowBusinessException_whenOtherError() {
        var branchId = "branchId";
        var newName = "NewName";
        when(branchRepository.updateName(branchId, newName)).thenReturn(Mono.error(new RuntimeException("Other error")));

        StepVerifier.create(branchUseCase.updateBranchName(branchId, newName))
                .expectErrorMatches(e -> e instanceof BusinessException && e.getMessage().contains("An error occurred"))
                .verify();
        verify(branchRepository, times(1)).updateName(branchId, newName);
    }
}
