package co.com.franchises.domain.model.branch.gateway;

import co.com.franchises.domain.model.branch.entities.Branch;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);

    Mono<Branch> updateName(String branchId, String newName);
}
