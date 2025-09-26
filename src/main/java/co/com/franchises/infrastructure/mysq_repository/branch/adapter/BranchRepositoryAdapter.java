package co.com.franchises.infrastructure.mysq_repository.branch.adapter;

import co.com.franchises.domain.model.branch.entities.Branch;
import co.com.franchises.domain.model.branch.gateway.BranchRepository;
import co.com.franchises.infrastructure.mysq_repository.branch.BranchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
public class BranchRepositoryAdapter implements BranchRepository {
    private final R2dbcEntityTemplate template;
    private final BranchMapper branchMapper;
    private final BranchDataRepository branchDataRepository;

    @Override
    public Mono<Branch> save(Branch branch) {
        var data = branchMapper.toData(branch);
        return template.insert(data)
                .map(branchMapper::toEntity);
    }

    @Override
    public Mono<Branch> updateName(String branchId, String newName) {
        return branchDataRepository.updateBranchName(branchId, newName)
                .flatMap(updatedRows -> {
                    if (updatedRows > 0) {
                        return branchDataRepository.findById(branchId)
                                .map(branchMapper::toEntity);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
