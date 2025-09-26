package co.com.franchises.infrastructure.reactive_web.branch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateDto {
    @NotBlank(message = "stock cannot be empty")
    private Integer stock;
}
