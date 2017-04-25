package at.ac.tuwien.waecm.app.persistence.repository;


import org.springframework.data.repository.CrudRepository;

import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Long>{

}