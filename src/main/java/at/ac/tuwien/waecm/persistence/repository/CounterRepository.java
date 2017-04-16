package at.ac.tuwien.waecm.persistence.repository;

import at.ac.tuwien.waecm.persistence.dbo.Counter;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Martin Griesler
 */
public interface CounterRepository extends CrudRepository<Counter, String> {

}
