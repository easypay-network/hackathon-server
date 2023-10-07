package network.easypay.server.model.graph.results;

import lombok.Data;

@Data
public class NodePropertiesResult {
    private String denomTrace;
    private String originalTicker;
    private String denom;
    private String localTicker;
    private String ticker;
    private String logoUrl;
}
