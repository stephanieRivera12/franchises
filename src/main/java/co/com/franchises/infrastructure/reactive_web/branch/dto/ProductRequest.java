package co.com.franchises.infrastructure.reactive_web.branch.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull
    @Min(value = 0, message = "Stock must be zero or positive")
    private Integer stock;
}
