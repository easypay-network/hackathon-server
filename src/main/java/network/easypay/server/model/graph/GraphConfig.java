package network.easypay.server.model.graph;

import lombok.Data;

@Data
public class GraphConfig {
    private int limit;
    private String relationshipWeightProperty;
    private String startNode;
    private String endNode;
    private GraphProjection graphConfig;
    private int communityNodeLimit;
    private String generatedName;
}
