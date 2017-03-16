package at.ac.tuwien.waecm.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import at.ac.tuwien.waecm.persistence.dbo.Counter;

/**
 * @author Martin Griesler
 */
public interface CounterRepository extends MongoRepository<Counter, String> {

}
