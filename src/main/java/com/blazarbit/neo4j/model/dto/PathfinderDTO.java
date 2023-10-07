package com.blazarbit.neo4j.model.dto;

import com.blazarbit.neo4j.model.graph.results.PathResult;
import lombok.Data;

import java.util.List;

@Data
public class PathfinderDTO {
    public enum TransactionType {
        TRANSFER,
        IBC_TRANSFER,
        CONTRACT_CALL
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
