package co.com.franchises.domain.usecase.franchise;

import co.com.franchises.domain.model.franchise.entities.Franchise;
import co.com.franchises.domain.model.franchise.gateway.FranchiseRepository;
import co.com.franchises.infrastructure.config.exception.BusinessException;
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
class FranchiseUseCaseTest {
    @Mock
    private FranchiseRepository franchiseRepository;
    @InjectMocks
    private FranchiseUseCase franchiseUseCase;


    @Test
    void saveFranchise_shouldSaveFranchiseWithRandomId() {
        var franchiseId = "franchiseId";
        var franchiseName = "Franchise1";

        var franchise = new Franchise();
        franchise.setName(franchiseName);
        var savedFranchise = new Franchise(franchiseId, franchiseName);
        when(franchiseRepository.save(franchise)).thenReturn(Mono.just(savedFranchise));

        StepVerifier.create(franchiseUseCase.saveFranchise(franchise))
                .expectNext(savedFranchise)
                .expectComplete()
                .verify();

        verify(franchiseRepository, times(1)).save(franchise);
    }

    @Test
    void saveFranchise_shouldThrowBusinessException_whenDuplicateKey() {
        Franchise franchise = new Franchise();
        franchise.setName("Franchise1");
        when(franchiseRepository.save(franchise)).thenReturn(Mono.error(new org.springframework.dao.DuplicateKeyException("Duplicate")));

        StepVerifier.create(franchiseUseCase.saveFranchise(franchise))
                .expectErrorMatches(e -> e instanceof BusinessException && e.getMessage().contains("already exists"))
                .verify();

        verify(franchiseRepository, times(1)).save(franchise);
    }

    @Test
    void saveFranchise_shouldThrowBusinessException_whenOtherError() {
        Franchise franchise = new Franchise();
        franchise.setName("Franchise1");
        when(franchiseRepository.save(franchise)).thenReturn(Mono.error(new RuntimeException("Other error")));

        StepVerifier.create(franchiseUseCase.saveFranchise(franchise))
                .expectErrorMatches(e -> e instanceof BusinessException && e.getMessage().contains("An error occurred"))
                .verify();

        verify(franchiseRepository, times(1)).save(any(Franchise.class));
    }

    @Test
    void updateFranchiseName_shouldUpdateName_whenFranchiseExists() {
        Franchise franchise = new Franchise();
        franchise.setId("franchiseId");
        franchise.setName("NewName");
        when(franchiseRepository.updateName("franchiseId", "NewName")).thenReturn(Mono.just(franchise));
        var expectedFranchise = new Franchise("franchiseId", "NewName");

        StepVerifier.create(franchiseUseCase.updateFranchiseName("franchiseId", "NewName"))
                .expectNext(expectedFranchise)
                .expectComplete()
                .verify();

        verify(franchiseRepository, times(1)).updateName("franchiseId", "NewName");
    }


    @Test
    void updateFranchiseName_shouldThrowBusinessException_whenDuplicateKey() {
        when(franchiseRepository.updateName("franchiseId", "NewName")).thenReturn(Mono.error(new org.springframework.dao.DuplicateKeyException("Duplicate")));

        StepVerifier.create(franchiseUseCase.updateFranchiseName("franchiseId", "NewName"))
                .expectErrorMatches(e -> e instanceof BusinessException && e.getMessage().contains("already exists"))
                .verify();

        verify(franchiseRepository, times(1)).updateName("franchiseId", "NewName");

    }

    @Test
    void updateFranchiseName_shouldThrowBusinessException_whenOtherError() {
        when(franchiseRepository.updateName("franchiseId", "NewName")).thenReturn(Mono.error(new RuntimeException("Other error")));

        StepVerifier.create(franchiseUseCase.updateFranchiseName("franchiseId", "NewName"))
                .expectErrorMatches(e -> e instanceof BusinessException && e.getMessage().contains("An error occurred"))
                .verify();

        verify(franchiseRepository, times(1)).updateName("franchiseId", "NewName");
    }
}

