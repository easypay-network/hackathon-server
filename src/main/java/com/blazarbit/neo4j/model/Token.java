package com.blazarbit.neo4j.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("token")
@Data
public class Token {
    @Id
    @GeneratedValue
    private Long identity;

    @Property("denom")
    private String denom;

    @Property("ticker")
    private String ticker;

    @Property("logoUrl")
    private String logoUrl;
}
