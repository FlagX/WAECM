package at.ac.tuwien.waecm.app.persistence.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {
	@Query("Select t from Transaction t WHERE t.owner.id = :id OR t.target.id = :id")
	List<Transaction> findByInvolvedAccount(@Param("id") Long id);
}