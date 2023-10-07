package network.easypay.server.repository;

import network.easypay.server.model.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends Neo4jRepository<Category, Long> {
    List<Category> findAll();
}
