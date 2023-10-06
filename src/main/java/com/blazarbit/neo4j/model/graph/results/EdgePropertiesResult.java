package com.blazarbit.neo4j.model.graph.results;

import lombok.Data;

@Data
public class EdgePropertiesResult {
    private String cost;
    private String outputChannel;
    private String inputChannel;
}
