package com.blazarbit.neo4j.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Node("address")
@Data
public class Address {
    @Id
    @GeneratedValue
    private Long identity;

    @Property("address")
    private String address;

    @Relationship(type = "located", direction = Relationship.Direction.OUTGOING)
    private Zone relatedZone;
}
