package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Zone;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends Neo4jRepository<Zone, Long> {
    @Query("MATCH (otherNode)-[:located]->(zone:zone) WHERE ID(otherNode) = $assetId RETURN zone")
    Zone findZoneByLocatedNodeId(@Param("assetId") Long assetId);

    @Query("MATCH (otherNode)-[:located]->(zone:zone) WHERE otherNode.denom = $assetDenom RETURN zone")
    Zone findZoneByLocatedNodeDenom(@Param("assetDenom") String assetDenom);

    List<Zone> findAll();
}
