package com.blazarbit.neo4j.model.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GraphProjection {
    private List<String> nodeProjection;
    @JsonProperty("relType1")
    private RelationshipProjection relationshipProjection1;
    @JsonProperty("relType2")
    private RelationshipProjection relationshipProjection2;
}
