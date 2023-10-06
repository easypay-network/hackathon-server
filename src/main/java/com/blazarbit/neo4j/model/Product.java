package com.blazarbit.neo4j.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("product")
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long identity;

    @Property("title")
    private String title;

    @Property("description")
    private String description;

    @Property("tokenAmount")
    private Double tokenAmount;

    @Property("imageUrl")
    private String imageUrl;

    @Property("recipientAddress")
    private String recipientAddress;

    @Property("contractAddress")
    private String contractAddress;

    @Property("tokenId")
    private String tokenId;

    @Property("listingDatetime")
    private String listingDatetime;

    @Property("listingUntilDatetime")
    private String listingUntilDatetime;
}
