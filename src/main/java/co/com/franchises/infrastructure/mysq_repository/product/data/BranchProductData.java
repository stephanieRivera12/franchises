package co.com.franchises.infrastructure.mysq_repository.product.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("branch_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchProductData {
    @Column("branch_id")
    private String branchId;
    @Column("product_id")
    private String productId;
    private Integer stock;
}
