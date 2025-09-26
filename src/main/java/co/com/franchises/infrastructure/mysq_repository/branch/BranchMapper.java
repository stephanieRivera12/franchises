package co.com.franchises.infrastructure.mysq_repository.branch;

import co.com.franchises.domain.model.branch.entities.Branch;
import co.com.franchises.infrastructure.mysq_repository.branch.data.BranchData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchData toData(Branch branch);

    Branch toEntity(BranchData branchData);

}
