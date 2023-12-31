package network.easypay.server.controller;

import network.easypay.server.model.Category;
import network.easypay.server.model.Product;
import network.easypay.server.model.dto.ProductCategoryDTO;
import network.easypay.server.service.CatalogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/catalog")
@AllArgsConstructor
public class CatalogController {
    private CatalogService catalogService;

    @GetMapping("/categories")
    private List<Category> findAllCategories(){
        return catalogService.findAllCategories();
    }

    @GetMapping("/products")
    private List<Product> findAllProducts(){
        return catalogService.findAllProducts();
    }

    @GetMapping("/products/{id}")
    private ResponseEntity<?> findProductByID(@PathVariable Long id){
        Product product = catalogService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/categories/{category}/products")
    private ResponseEntity<?> findProductsByCategory(@PathVariable(name = "category") String category){
        Optional<List<Product>> products = catalogService.findProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products-categories/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductWithCategory(@PathVariable Long id) {
        ProductCategoryDTO productCategoryDTO = catalogService.getProductWithCategory(id);
        if (productCategoryDTO != null) {
            return ResponseEntity.ok(productCategoryDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products-categories")
    public ResponseEntity<List<ProductCategoryDTO>> getProductWithCategory() {
        List<ProductCategoryDTO> productsCategoriesDTO = catalogService.getAllProductsWithCategories();
        if (productsCategoriesDTO != null) {
            return ResponseEntity.ok(productsCategoriesDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
