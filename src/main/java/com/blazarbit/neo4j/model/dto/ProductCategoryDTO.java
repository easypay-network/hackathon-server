package com.blazarbit.neo4j.model.dto;

import com.blazarbit.neo4j.model.Category;
import com.blazarbit.neo4j.model.Product;
import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Product product;
    private Category category;
}
