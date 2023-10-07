package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Address;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends Neo4jRepository<Address, Long> {
    @Query("MATCH (a:address) WHERE a.address = $address RETURN a")
    Optional<Address> findByAddress(@Param("address") String address);
}
