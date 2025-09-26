package co.com.franchises.infrastructure.mysq_repository.branch.adapter;

import co.com.franchises.infrastructure.mysq_repository.branch.data.BranchData;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface BranchDataRepository extends R2dbcRepository<BranchData, String> {
    @Modifying
    @Query("UPDATE branch SET name = :newName WHERE id = :branchId")
    Mono<Integer> updateBranchName(String branchId, String newName);

}
