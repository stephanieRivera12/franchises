package co.com.franchises.infrastructure.mysq_repository.franchise.adapter;

import co.com.franchises.infrastructure.mysq_repository.franchise.data.FranchiseData;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface FranchiseDataRepository extends R2dbcRepository<FranchiseData, String> {

    @Modifying
    @Query("UPDATE franchise SET name = :newName WHERE id = :franchiseId")
    Mono<Integer> updateFranchiseName(String franchiseId, String newName);
}
