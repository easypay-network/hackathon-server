package network.easypay.server.model.graph.results;

import network.easypay.server.model.Zone;
import lombok.Data;

import java.util.List;

@Data
public class NodeResult {
    private Long id;
    private List<String> labels;
    private NodePropertiesResult properties;
    private Zone zone;
}
