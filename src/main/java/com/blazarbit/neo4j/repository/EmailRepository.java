package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Email;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends Neo4jRepository<Email, Long> {
    @Query("MATCH (a:email) WHERE a.address = $address RETURN a")
    Optional<Email> findByAddress(@Param("address") String address);
}
