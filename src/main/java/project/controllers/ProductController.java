package project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.dto.responses.GetProductByIdResponse;
import project.dto.responses.GetProductsResponse;
import project.services.ProductService;

@RestController
@RequestMapping(path = "products")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<GetProductsResponse> getProducts(
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortDesc", defaultValue = "true") boolean sortDesc
    ) {
        try {
            GetProductsResponse getProductsResponse = productService.getProducts();
            return ResponseEntity.ok(getProductsResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(GetProductsResponse.builder()
                    .message(e.getMessage())
                    .products(null)
                    .build());
        }
    }

    @GetMapping(":id")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<GetProductByIdResponse> getProductById(
            @RequestParam(value="id") int id
    ) {
        try {
            GetProductByIdResponse getProductByIdResponse = productService.getProductById(id);
            return ResponseEntity.ok(getProductByIdResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(GetProductByIdResponse.builder()
                    .message(e.getMessage())
                    .product(null)
                    .build());
        }
    }
}
