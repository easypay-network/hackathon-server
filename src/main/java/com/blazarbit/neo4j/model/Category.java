package com.blazarbit.neo4j.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("category")
@Data
public class Category {
    @Id
    @GeneratedValue
    private Long identity;

    @Property("name")
    private String name;

    @Property("featured")
    private String featured;

    @Property("imageUrl")
    private String imageUrl;
}
