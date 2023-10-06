package com.blazarbit.neo4j.model.graph;

import lombok.Data;

@Data
public class RelationshipProjection {
    private String type;
    private String orientation;
    private RelationshipProperties relationshipProperties;
}
