package at.ac.tuwien.waecm.app.persistence.repository;


import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction,Long>{

}