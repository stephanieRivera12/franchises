package co.com.franchises.infrastructure.reactive_web.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameUpdateDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
}