package com.blazarbit.neo4j.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("zone")
@Data
public class Zone {
    @Id
    @GeneratedValue
    private Long identity;

    @Property("logoUrl")
    private String logoUrl;

    @Property("networkId")
    private String networkId;

    @Property("name")
    private String name;
}
