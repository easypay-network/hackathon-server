package com.blazarbit.neo4j.model.graph.results;

import lombok.Data;

@Data
public class RelationshipResult {
    private String type;
    private Long startNodeId;
    private Long endNodeId;
    private EdgePropertiesResult properties;
}
