package co.com.franchises.infrastructure.mysq_repository.branch.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("branch")
public class BranchData {
    @Id
    private String id;
    @Column("franchise_id")
    private String franchiseId;
    private String name;
}
