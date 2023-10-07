package com.blazarbit.neo4j.service;

import com.blazarbit.neo4j.model.*;
import com.blazarbit.neo4j.model.dto.ProductCategoryDTO;
import com.blazarbit.neo4j.repository.AssetRepository;
import com.blazarbit.neo4j.repository.CategoryRepository;
import com.blazarbit.neo4j.repository.ProductRepository;
import com.blazarbit.neo4j.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class CatalogService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private AssetRepository assetRepository;
    private ZoneRepository zoneRepository;

    public List<Category> findAllCategories() {
        log.info("Search all catalog categories...");
        List<Category> categories = categoryRepository.findAll();
        log.info(categories);

        return categories;
    }

    public List<Product> findAllProducts() {
        log.info("Search all catalog products...");
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            List<Map<String, Object>> assetResponse = assetRepository.findAssetByProductId(product.getIdentity());
            Asset asset = new Asset();

            if (!assetResponse.isEmpty()) {
                asset.setTicker((String) assetResponse.get(0).get("ticker"));
                asset.setLogoUrl((String) assetResponse.get(0).get("logoUrl"));
                asset.setDenom((String) assetResponse.get(0).get("denom"));
                asset.setDenomTrace((String) assetResponse.get(0).get("denomTrace"));
                asset.setOriginalTicker((String) assetResponse.get(0).get("originalTicker"));
                asset.setLocalTicker((String) assetResponse.get(0).get("localTicker"));
            }

            Zone zone = zoneRepository.findZoneByLocatedNodeDenom(asset.getDenom());
            asset.setLocatedZone(zone);

            product.setRequestedAsset(asset);
        }

        log.info(products);

        return products;
    }

    public Optional<List<Product>> findProductsByCategory(String categoryName) {
        log.info("Search products by category...");
        Optional<List<Product>> products = productRepository.findProductsByCategoryName(categoryName);

        if (products.isPresent()) {
            for (Product product : products.get()) {
                List<Map<String, Object>> assetResponse = assetRepository.findAssetByProductId(product.getIdentity());
                Asset asset = new Asset();

                if (!assetResponse.isEmpty()) {
                    asset.setTicker((String) assetResponse.get(0).get("ticker"));
                    asset.setLogoUrl((String) assetResponse.get(0).get("logoUrl"));
                    asset.setDenom((String) assetResponse.get(0).get("denom"));
                    asset.setDenomTrace((String) assetResponse.get(0).get("denomTrace"));
                    asset.setOriginalTicker((String) assetResponse.get(0).get("originalTicker"));
                    asset.setLocalTicker((String) assetResponse.get(0).get("localTicker"));
                }

                Zone zone = zoneRepository.findZoneByLocatedNodeDenom(asset.getDenom());
                asset.setLocatedZone(zone);

                product.setRequestedAsset(asset);
            }
        }

        log.info(products);

        return products;
    }

    public Product findProductById(Long id) {
        log.info("Search catalog product by id...");
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            List<Map<String, Object>> assetResponse = assetRepository.findAssetByProductId(product.get().getIdentity());
            Asset asset = new Asset();

            if (!assetResponse.isEmpty()) {
                asset.setTicker((String) assetResponse.get(0).get("ticker"));
                asset.setLogoUrl((String) assetResponse.get(0).get("logoUrl"));
                asset.setDenom((String) assetResponse.get(0).get("denom"));
                asset.setDenomTrace((String) assetResponse.get(0).get("denomTrace"));
                asset.setOriginalTicker((String) assetResponse.get(0).get("originalTicker"));
                asset.setLocalTicker((String) assetResponse.get(0).get("localTicker"));
            }

            Zone zone = zoneRepository.findZoneByLocatedNodeDenom(asset.getDenom());
            asset.setLocatedZone(zone);
            product.get().setRequestedAsset(asset);
        }

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
