package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Product;
import com.blazarbit.neo4j.model.dto.ProductCategoryDTO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends Neo4jRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    @Query("MATCH (p:product)-[:relatedCategory]->(c:category) " +
            "WHERE toLower(c.name) = toLower($categoryName) " +
            "RETURN p AS product")
    Optional<List<Product>> findProductsByCategoryName(@Param("categoryName")String categoryId);

    @Query("MATCH (p:product)-[:relatedCategory]->(c:category)" +
            "WHERE id(p) = $productId " +
            "RETURN p AS product, c AS category")
    ProductCategoryDTO findProductWithCategory(@Param("productId") Long productId);

    @Query("MATCH (p:product)-[:relatedCategory]->(c:category)" +
            "RETURN p AS product, c AS category")
    List<ProductCategoryDTO> findAllProductsWithCategories();
}
