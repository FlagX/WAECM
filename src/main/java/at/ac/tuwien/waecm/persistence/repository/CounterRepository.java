package at.ac.tuwien.waecm.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import at.ac.tuwien.waecm.persistence.dbo.Counter;

/**
 * @author Martin Griesler
 */
public interface CounterRepository extends CrudRepository<Counter, Long> {

}
