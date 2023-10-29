package network.easypay.server.model.dto;

import network.easypay.server.model.graph.results.PathResult;
import lombok.Data;

import java.util.List;

@Data
public class PathfinderDTO {
    public enum TransactionType {
        TRANSFER,
        IBC_TRANSFER,
        CONTRACT_CALL,
        DIRECT_PAYMENT,
        CANNOT_FIND
    }
    private TransactionType transactionType;
    private List<PathResult> pathResults;
    private String ibcMemo;
    private String txMemo;
    private String address;
    private String feeToken;
    private Long feeAmount;
    private Double destinationTokenAmount;
}
