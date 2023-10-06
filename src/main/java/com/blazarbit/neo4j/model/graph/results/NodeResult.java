package com.blazarbit.neo4j.model.graph.results;

import com.blazarbit.neo4j.model.Zone;
import lombok.Data;

import java.util.List;

@Data
public class NodeResult {
    private Long id;
    private List<String> labels;
    private NodePropertiesResult properties;
    private Zone zone;
}
