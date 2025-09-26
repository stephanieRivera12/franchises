package co.com.franchises.infrastructure.mysq_repository.product.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductData {
    @Id
    private String id;
    private String name;
}
