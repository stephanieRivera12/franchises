package co.com.franchises.infrastructure.mysq_repository.franchise.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("franchise")
public class FranchiseData {
    @Id
    private String id;
    private String name;
}
