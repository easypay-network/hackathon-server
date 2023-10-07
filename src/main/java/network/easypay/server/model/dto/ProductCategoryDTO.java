package network.easypay.server.model.dto;

import network.easypay.server.model.Category;
import network.easypay.server.model.Product;
import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Product product;
    private Category category;
}
