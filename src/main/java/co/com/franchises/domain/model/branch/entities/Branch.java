package co.com.franchises.domain.model.branch.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    private String id;
    private String franchiseId;
    @NotBlank
    private String name;
}