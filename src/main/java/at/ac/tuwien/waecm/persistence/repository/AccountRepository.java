package at.ac.tuwien.waecm.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.ac.tuwien.waecm.persistence.dbo.Account;

/**
 * Created by Stefan on 15.03.2017.
 */
public interface AccountRepository extends MongoRepository<Account,Long> {
    Account findByUsername(String username);
}
