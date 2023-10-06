package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Zone;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends Neo4jRepository<Zone, Long> {
    @Query("MATCH (otherNode)-[:located]->(zone:zone) WHERE ID(otherNode) = $assetId RETURN zone")
    Zone findZoneByLocatedNode(@Param("assetId") Long assetId);
}
