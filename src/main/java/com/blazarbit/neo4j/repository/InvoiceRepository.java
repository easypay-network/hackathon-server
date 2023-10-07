package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Invoice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends Neo4jRepository<Invoice, Long> {
    List<Invoice> findAll();

    Optional<Invoice> findByIdentity(Long id);

    @Query("MATCH (a), (b) WHERE id(a) = $nodeId1 AND id(b) = $nodeId2 CREATE (a)-[:requested]->(b)")
    void createRelationship(@Param("nodeId1") Long invoice, @Param("nodeId2") Long asset);
}
