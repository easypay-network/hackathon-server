package network.easypay.server.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("email")
@Data
public class Email {
    @Id
    @GeneratedValue
    private Long identity;

    @Property("address")
    private String address;
}
