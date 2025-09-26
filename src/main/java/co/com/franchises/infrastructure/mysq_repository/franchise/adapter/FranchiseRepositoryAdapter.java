package co.com.franchises.infrastructure.mysq_repository.franchise.adapter;

import co.com.franchises.domain.model.franchise.entities.Franchise;
import co.com.franchises.domain.model.franchise.gateway.FranchiseRepository;
import co.com.franchises.infrastructure.mysq_repository.franchise.FranchiseMapper;
import co.com.franchises.infrastructure.mysq_repository.franchise.data.FranchiseData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepository {
    private final FranchiseDataRepository franchiseDataRepository;
    private final FranchiseMapper franchiseMapper;
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        var data = franchiseMapper.toData(franchise);
        return template.insert(FranchiseData.class)
                .using(data)
                .map(franchiseMapper::toEntity);
    }

    @Override
    public Mono<Franchise> updateName(String franchiseId, String newName) {
        return franchiseDataRepository.updateFranchiseName(franchiseId, newName)
                .flatMap(updatedRows -> {
                    if (updatedRows > 0) {
                        return franchiseDataRepository.findById(franchiseId)
                                .map(franchiseMapper::toEntity);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    public Mono<Franchise> findById(String franchiseId) {
        return franchiseDataRepository.findById(franchiseId)
                .map(franchiseMapper::toEntity);
    }

}
