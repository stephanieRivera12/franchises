package co.com.franchises.domain.model.product.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String branchId;
    private Integer stock;
}
