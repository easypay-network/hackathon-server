package network.easypay.server.service;

import network.easypay.server.model.Zone;
import network.easypay.server.model.dto.PathfinderDTO;
import network.easypay.server.model.graph.results.*;
import network.easypay.server.model.graph.GraphConfig;
import network.easypay.server.model.graph.GraphProjection;
import network.easypay.server.model.graph.RelationshipProjection;
import network.easypay.server.model.graph.RelationshipProperties;
import network.easypay.server.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalRelationship;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Log4j2
@AllArgsConstructor
public class ShortestPathService {
    private Neo4jClient neo4jClient;
    private ZoneRepository zoneRepository;

    public PathfinderDTO getPathByDenoms(String sourceDenom, String destinationDenom, String address) {
        String graphName = "in-memory-graph-12345";
        dropGraphConfiguration(graphName);

        List<PathResult> shortestPath = getPathFromDB(sourceDenom, destinationDenom, graphName);

        for (PathResult item : shortestPath) {
            Zone startZone = zoneRepository.findZoneByLocatedNodeId(item.getStartNode().getId());
            item.getStartNode().setZone(startZone);
            Zone endZone = zoneRepository.findZoneByLocatedNodeId(item.getEndNode().getId());
            item.getEndNode().setZone(endZone);
        }

        PathfinderDTO pathfinderDTO = new PathfinderDTO() {{
            setTxMemo("Helloooo!");
            setPathResults(shortestPath);
            setIbcMemo("");
            if (shortestPath.size() == 0) {
                setTransactionType(TransactionType.TRANSFER);
                setAddress(address);
            } else if (shortestPath.size() == 1 && shortestPath.get(0).getEdge().getType().equalsIgnoreCase("ibcTransfer")) {
                setTransactionType(TransactionType.IBC_TRANSFER);
                setAddress(address);
            } else {
                //need to implement later
                setAddress(address);//stub
            }
        }};

        return pathfinderDTO;
    }

    @Transactional
    List<PathResult> getPathFromDB(String sourceDenom, String destinationDenom, String graphName) {
        GraphConfig graphConfigParams = getGraphConfigParams(graphName, sourceDenom, destinationDenom);
        createGraphConfiguration(graphConfigParams);
        projectGraph(graphConfigParams);
        List<PathResult> shortestPath = findShortestPath(graphConfigParams);
        dropGraphConfiguration(graphName);
        return shortestPath;
    }

    private GraphConfig getGraphConfigParams(String graphName, String sourceDenom, String destinationDenom) {
        GraphConfig config = new GraphConfig();
        config.setLimit(42);

        config.setRelationshipWeightProperty("cost");

        //  ibc%2F223%2E%2E%2EHP3
        config.setStartNode(sourceDenom); // "ibc/223...HP3" == ibc%2F223...HP3 == ibc%2F223%2E%2E%2EHP3
        config.setEndNode(destinationDenom); // "ujuno"

        GraphProjection graphProjection = new GraphProjection();
        List<String> nodeProjection = Arrays.asList("derivative", "token");
        graphProjection.setNodeProjection(nodeProjection);

        RelationshipProjection relType1 = new RelationshipProjection();
        relType1.setType("ibcTransfer");
        relType1.setOrientation("NATURAL");

        RelationshipProperties relationshipProperties1 = new RelationshipProperties() {{
            setCost("cost");
        }};
        relType1.setRelationshipProperties(relationshipProperties1);

        RelationshipProjection relType2 = new RelationshipProjection();
        relType2.setType("exchange");
        relType2.setOrientation("NATURAL");

        RelationshipProperties relationshipProperties2 = new RelationshipProperties() {{
            setCost("cost");
        }};
        relType2.setRelationshipProperties(relationshipProperties2);

        graphProjection.setRelationshipProjection1(relType1);
        graphProjection.setRelationshipProjection2(relType2);
        config.setGraphConfig(graphProjection);

        config.setCommunityNodeLimit(10);
        config.setGeneratedName(graphName);
        return config;
    }

    @Transactional
    void createGraphConfiguration(GraphConfig config) {
        neo4jClient.query("CREATE (config:GraphConfig " +
                        "{ limit: $limit, " +
                        "relationshipWeightProperty: $relationshipWeightProperty, " +
                        "startNode: $startNode, " +
                        "endNode: $endNode, " +
                        "nodeProjection: $nodeProjection, " +
                        "relType1Type: $relType1Type, " +
                        "relType1Orientation: $relType1Orientation, " +
                        "relType1CostProperty: $relType1CostProperty, " +
                        "relType2Type: $relType2Type, " +
                        "relType2Orientation: $relType2Orientation, " +
                        "relType2CostProperty: $relType2CostProperty, " +
                        "communityNodeLimit: $communityNodeLimit, " +
                        "generatedName: $generatedName })")
                .bind(config.getLimit()).to("limit")
                .bind(config.getRelationshipWeightProperty()).to("relationshipWeightProperty")
                .bind(config.getStartNode()).to("startNode")
                .bind(config.getEndNode()).to("endNode")
                .bind(config.getGraphConfig().getNodeProjection()).to("nodeProjection")
                .bind(config.getGraphConfig().getRelationshipProjection1().getType()).to("relType1Type")
                .bind(config.getGraphConfig().getRelationshipProjection1().getOrientation()).to("relType1Orientation")
                .bind(config.getGraphConfig().getRelationshipProjection1().getRelationshipProperties().getCost()).to("relType1CostProperty")
                .bind(config.getGraphConfig().getRelationshipProjection2().getType()).to("relType2Type")
                .bind(config.getGraphConfig().getRelationshipProjection2().getOrientation()).to("relType2Orientation")
                .bind(config.getGraphConfig().getRelationshipProjection2().getRelationshipProperties().getCost()).to("relType2CostProperty")
                .bind(config.getCommunityNodeLimit()).to("communityNodeLimit")
                .bind(config.getGeneratedName()).to("generatedName")
                .fetch().all();
    }

    @Transactional
    void projectGraph(GraphConfig config) {
        neo4jClient.query("CALL gds.graph.project($graphName, $nodeProjection, $relationshipProjection, {})")
                .bind(config.getGeneratedName()).to("graphName")
                .bind(config.getGraphConfig().getNodeProjection()).to("nodeProjection")
                .bind(Map.of(
                        "relType1", Map.of(
                                "type", config.getGraphConfig().getRelationshipProjection1().getType(),
                                "orientation", config.getGraphConfig().getRelationshipProjection1().getOrientation(),
                                "properties", Map.of("cost", config.getGraphConfig().getRelationshipProjection1().getRelationshipProperties().getCost())
                        ),
                        "relType2", Map.of(
                                "type", config.getGraphConfig().getRelationshipProjection2().getType(),
                                "orientation", config.getGraphConfig().getRelationshipProjection2().getOrientation(),
                                "properties", Map.of("cost", config.getGraphConfig().getRelationshipProjection2().getRelationshipProperties().getCost())
                        )
                )).to("relationshipProjection")
                .fetch().all();
    }

    @Transactional
    List<PathResult> findShortestPath(GraphConfig config) {
        Collection<Map<String, Object>> all = neo4jClient.query("CALL db.propertyKeys() YIELD propertyKey MATCH (start) WHERE start[propertyKey] CONTAINS $startNode WITH start LIMIT 1 CALL db.propertyKeys() YIELD propertyKey MATCH (end) WHERE end[propertyKey] CONTAINS $endNode WITH start, end LIMIT 1 WITH $config AS config, start, end WITH config { .*, sourceNode: id(start), targetNode: id(end)} AS config CALL gds.shortestPath.dijkstra.stream($generatedName, config) YIELD sourceNode, targetNode, nodeIds, costs, path UNWIND range(0, size(nodeIds)-2) AS index WITH gds.util.asNode(nodeIds[index]) AS startNode, gds.util.asNode(nodeIds[index+1]) AS endNode, costs[index] AS edgeCost MATCH (startNode)-[edge]->(endNode) RETURN startNode, edge, endNode, edgeCost")
                .bind(config.getStartNode()).to("startNode")
                .bind(config.getEndNode()).to("endNode")
                .bind(config.getGeneratedName()).to("generatedName")
                .bind(createSearchGraphConfigMap(config)).to("config")
                .fetch()
                .all();

        List<PathResult> pathResults = new ArrayList<>();
        for (Map<String, Object> row : all) {
            NodeResult startNode = internalNodeToNodeResult((InternalNode) row.get("startNode"));
            RelationshipResult edge = internalRelationshipToRelationshipResult((InternalRelationship) row.get("edge"));
            NodeResult endNode = internalNodeToNodeResult((InternalNode) row.get("endNode"));
            Double edgeCost = (Double) row.get("edgeCost");

            PathResult pathResult = new PathResult();
            pathResult.setStartNode(startNode);
            pathResult.setEdge(edge);
            pathResult.setEndNode(endNode);
            pathResult.setEdgeCost(edgeCost);

            pathResults.add(pathResult);
        }

        return pathResults;
    }

    private NodeResult internalNodeToNodeResult(InternalNode nodeMap) {
        NodeResult nodeResult = new NodeResult();
        nodeResult.setId(nodeMap.id());
        nodeResult.setLabels((List<String>) nodeMap.labels());

        NodePropertiesResult properties = new NodePropertiesResult();

        String denom = nodeMap.get("denom").toString();
        if (denom.startsWith("\"") && denom.endsWith("\"")) {
            denom = denom.substring(1, denom.length() - 1);
        }
        properties.setDenom(denom);

        String denomTrace = nodeMap.get("denomTrace").toString();
        if (denomTrace.startsWith("\"") && denomTrace.endsWith("\"")) {
            denomTrace = denomTrace.substring(1, denomTrace.length() - 1);
        }
        properties.setDenomTrace(denomTrace);

        String localTicker = nodeMap.get("localTicker").toString();
        if (localTicker.startsWith("\"") && localTicker.endsWith("\"")) {
            localTicker = localTicker.substring(1, localTicker.length() - 1);
        }
        properties.setLocalTicker(localTicker);

        String originalTicker = nodeMap.get("originalTicker").toString();
        if (originalTicker.startsWith("\"") && originalTicker.endsWith("\"")) {
            originalTicker = originalTicker.substring(1, originalTicker.length() - 1);
        }
        properties.setOriginalTicker(originalTicker);

        String ticker = nodeMap.get("ticker").toString();
        if (ticker.startsWith("\"") && ticker.endsWith("\"")) {
            ticker = ticker.substring(1, ticker.length() - 1);
        }
        properties.setTicker(ticker);

        String logoUrl = nodeMap.get("logoUrl").toString();
        if (logoUrl.startsWith("\"") && logoUrl.endsWith("\"")) {
            logoUrl = logoUrl.substring(1, logoUrl.length() - 1);
        }
        properties.setLogoUrl(logoUrl);

        nodeResult.setProperties(properties);

        return nodeResult;
    }

    private RelationshipResult internalRelationshipToRelationshipResult(InternalRelationship relationshipMap) {
        RelationshipResult relationshipResult = new RelationshipResult();
        relationshipResult.setType(relationshipMap.type());
        relationshipResult.setStartNodeId(relationshipMap.startNodeId());
        relationshipResult.setEndNodeId(relationshipMap.endNodeId());

        EdgePropertiesResult properties = new EdgePropertiesResult();
        properties.setCost(relationshipMap.get("cost").toString());

        String outputChannel = relationshipMap.get("outputChannel").toString();
        if (outputChannel.startsWith("\"") && outputChannel.endsWith("\"")) {
            outputChannel = outputChannel.substring(1, outputChannel.length() - 1);
        }
        properties.setOutputChannel(outputChannel);

        String inputChannel = relationshipMap.get("inputChannel").toString();
        if (inputChannel.startsWith("\"") && inputChannel.endsWith("\"")) {
            inputChannel = inputChannel.substring(1, inputChannel.length() - 1);
        }
        properties.setInputChannel(inputChannel);

        relationshipResult.setProperties(properties);

        return relationshipResult;
    }

    private Map<String, Object> createSearchGraphConfigMap(GraphConfig config) {
        Map<String, Object> graphConfigMap = new HashMap<>();

        graphConfigMap.put("relationshipWeightProperty", config.getRelationshipWeightProperty());
        graphConfigMap.put("sourceNode", "id(start)");
        graphConfigMap.put("targetNode", "id(end)");

        return graphConfigMap;
    }

    void dropGraphConfiguration(String graphName) {
        try {
            neo4jClient.query("CALL gds.graph.drop($graphName)")
                    .bind(graphName).to("graphName")
                    .fetch()
                    .all();
        } catch (Exception e) {
            // nothing to delete
        }
        try {
            neo4jClient.query("MATCH (config:GraphConfig {generatedName: $generatedName}) DETACH DELETE config")
                    .bind(graphName).to("generatedName")
                    .fetch()
                    .all();
        } catch (Exception e) {
            // nothing to delete
        }
    }
}
