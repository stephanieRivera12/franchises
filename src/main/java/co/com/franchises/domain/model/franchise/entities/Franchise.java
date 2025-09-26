package co.com.franchises.domain.model.franchise.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Franchise {
    private String id;
    @NotBlank(message = "The name is required")
    private String name;
}
