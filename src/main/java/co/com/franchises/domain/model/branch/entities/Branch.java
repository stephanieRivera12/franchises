package co.com.franchises.domain.model.branch.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    private String id;
    private String franchiseId;
    private String name;
}