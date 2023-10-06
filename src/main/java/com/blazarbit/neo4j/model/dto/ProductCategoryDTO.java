package com.blazarbit.neo4j.model.dto;

import com.blazarbit.neo4j.model.Category;
import com.blazarbit.neo4j.model.Product;

public class ProductCategoryDTO {
    private Product product;
    private Category category;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
