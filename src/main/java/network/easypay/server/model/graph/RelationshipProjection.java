package network.easypay.server.model.graph;

import lombok.Data;

@Data
public class RelationshipProjection {
    private String type;
    private String orientation;
    private RelationshipProperties relationshipProperties;
}
