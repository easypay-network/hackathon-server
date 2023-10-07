package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Asset;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AssetRepository extends Neo4jRepository<Asset, Long> {
    @Query("MATCH (n) WHERE n:token OR n:derivative RETURN n")
    List<Asset> findAll();

    @Query("MATCH (n) WHERE n:token OR n:derivative RETURN n")
    List<Map<String, Object>> findAll2();

    @Query("MATCH (invoice:invoice)-[:requested]->(asset) " +
            "WHERE id(invoice) = $invoiceId AND (asset:token OR asset:derivative) " +
            "RETURN asset " +
            "LIMIT 1")
    List<Map<String, Object>> findAssetByInvoiceId(@Param("invoiceId")Long invoiceId);

    @Query("MATCH (n) WHERE (n:token OR n:derivative) AND n.denom = $denom RETURN n LIMIT 1")
    List<Map<String, Object>> findOneByDenom(@Param("denom") String denom);

    @Query("MATCH (n) WHERE (n:token OR n:derivative) AND n.denom = $denom RETURN id(n) AS identity LIMIT 1")
    Long findOneIdentityByDenom(@Param("denom") String denom);
}
