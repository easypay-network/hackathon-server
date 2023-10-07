package com.blazarbit.neo4j.service;

import com.blazarbit.neo4j.model.Category;
import com.blazarbit.neo4j.model.Product;
import com.blazarbit.neo4j.model.dto.ProductCategoryDTO;
import com.blazarbit.neo4j.repository.CategoryRepository;
import com.blazarbit.neo4j.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class CatalogService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public List<Category> findAllCategories() {
        log.info("Search all catalog categories...");
        List<Category> categories = categoryRepository.findAll();
        log.info(categories);

        return categories;
    }

    public List<Product> findAllProducts() {
        log.info("Search all catalog products...");
        List<Product> products = productRepository.findAll();
        log.info(products);

        return products;
    }

    public Optional<List<Product>> findProductsByCategory(String categoryName) {
        log.info("Search products by category...");
        Optional<List<Product>> products = productRepository.findProductsByCategoryName(categoryName);
        log.info(products);

        return products;
    }

    public Product findProductById(Long id) {
        log.info("Search catalog product by id...");
        Optional<Product> product = productRepository.findById(id);
        log.info(product);

        return product.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public ProductCategoryDTO getProductWithCategory(Long productId) {
        return productRepository.findProductWithCategory(productId);
    }

    public List<ProductCategoryDTO> getAllProductsWithCategories() {
        return productRepository.findAllProductsWithCategories();
    }
}
