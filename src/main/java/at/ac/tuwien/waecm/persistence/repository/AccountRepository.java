package at.ac.tuwien.waecm.persistence.repository;

import at.ac.tuwien.waecm.persistence.dbo.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Stefan on 15.03.2017.
 */
public interface AccountRepository extends MongoRepository<Account,Long> {
    Account findByUsername(String username);
}
