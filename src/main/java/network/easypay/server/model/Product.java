package network.easypay.server.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Node("product")
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long identity;

    @Property("title")
    private String title;

    @Property("description")
    private String description;

    @Property("imageUrl")
    private String imageUrl;

    @Property("requestedAmount")
    private Double requestedAmount;

    @Property("creationDate")
    private String creationDate;

    @Property("dueDate")
    private String dueDate;

    @Relationship(type = "receiver", direction = Relationship.Direction.OUTGOING)
    private Address receiver;

    @Relationship(type = "requester", direction = Relationship.Direction.OUTGOING)
    private Email requester;

    private Asset requestedAsset;
}
