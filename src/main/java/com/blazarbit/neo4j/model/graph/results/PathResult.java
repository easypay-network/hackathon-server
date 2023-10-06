package com.blazarbit.neo4j.model.graph.results;

import lombok.Data;

@Data
public class PathResult {
    private NodeResult startNode;
    private RelationshipResult edge;
    private NodeResult endNode;
    private Double edgeCost;
}
