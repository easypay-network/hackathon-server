package network.easypay.server.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Node("invoice")
@Data
public class Invoice {
    public enum Direction {
        OUTGOING,
        INCOMING,
        NEUTRAL
    }

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

    @Property("payedAmount")
    private Double payedAmount;

    @Property("status")
    private String status;

    @Property("creationDate")
    private String creationDate;

    @Property("dueDate")
    private String dueDate;

    @Property("type")
    private String type;

    private Direction direction;

    @Relationship(type = "receiver", direction = Relationship.Direction.OUTGOING)
    private Address receiver;

    @Relationship(type = "requester", direction = Relationship.Direction.OUTGOING)
    private Email requester;

    private Asset requestedAsset;

    @Relationship(type = "payerWallet", direction = Relationship.Direction.OUTGOING)
    private Address payerWallet;

    @Relationship(type = "payerEmail", direction = Relationship.Direction.OUTGOING)
    private Email payerEmail;
}
