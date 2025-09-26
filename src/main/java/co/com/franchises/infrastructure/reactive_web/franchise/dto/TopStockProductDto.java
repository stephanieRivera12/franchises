package co.com.franchises.infrastructure.reactive_web.franchise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopStockProductDto {
    private String productId;
    private String productName;
    private String branchId;
    private String branchName;
    private Integer totalStock;
}