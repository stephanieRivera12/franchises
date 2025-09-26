package co.com.franchises.infrastructure.mysq_repository.product.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDataWithBranch {
    private String id;
    private String name;
    private String branchId;
    private Integer stock;
}