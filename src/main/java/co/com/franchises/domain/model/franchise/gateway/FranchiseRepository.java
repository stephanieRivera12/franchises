package co.com.franchises.domain.model.franchise.gateway;

import co.com.franchises.domain.model.franchise.entities.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);

    Mono<Franchise> updateName(String franchiseId, String newName);

    Mono<Franchise> findById(String franchiseId);
}