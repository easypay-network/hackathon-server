package network.easypay.server.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

@Data
public class Asset {
    @Id
    @GeneratedValue
    private Long identity;

    @Property("ticker")
    private String ticker;

    @Property("logoUrl")
    private String logoUrl;

    @Property("denom")
    private String denom;

    @Property("denomTrace")
    private String denomTrace;

    @Property("originalTicker")
    private String originalTicker;

    @Property("localTicker")
    private String localTicker;

    private Zone locatedZone;
}
