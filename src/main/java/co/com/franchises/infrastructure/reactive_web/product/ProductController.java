package co.com.franchises.infrastructure.reactive_web.product;

import co.com.franchises.domain.model.product.entities.Product;
import co.com.franchises.domain.usecase.product.ProductUseCase;
import co.com.franchises.infrastructure.reactive_web.common.dto.NameUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @PatchMapping("{productId}/name")
    public Mono<Product> updateProductName(@PathVariable String productId, @Valid @RequestBody NameUpdateDto dto) {
        return productUseCase.updateProductName(productId, dto.getName());
    }
}
