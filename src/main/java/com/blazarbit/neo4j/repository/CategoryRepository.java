package com.blazarbit.neo4j.repository;

import com.blazarbit.neo4j.model.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends Neo4jRepository<Category, Long> {
    List<Category> findAll();
}
