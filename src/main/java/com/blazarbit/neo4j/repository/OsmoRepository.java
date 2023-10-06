package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Token;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OsmoRepository extends Neo4jRepository<Token, Long> {
    @Query("MATCH (n:token) WHERE n.denom = $denomName RETURN n LIMIT 1")
    Token findByDenom(String denomName);

    Token findTokenByDenom(String denomName);
}
