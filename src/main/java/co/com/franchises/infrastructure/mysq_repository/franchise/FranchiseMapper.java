package co.com.franchises.infrastructure.mysq_repository.franchise;

import co.com.franchises.domain.model.franchise.entities.Franchise;
import co.com.franchises.infrastructure.mysq_repository.franchise.data.FranchiseData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {
    FranchiseData toData(Franchise franchise);

    Franchise toEntity(FranchiseData franchiseData);
}
