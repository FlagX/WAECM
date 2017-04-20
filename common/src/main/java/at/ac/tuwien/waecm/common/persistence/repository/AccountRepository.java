package at.ac.tuwien.waecm.common.persistence.repository;

import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Stefan on 15.03.2017.
 */
public interface AccountRepository extends CrudRepository<Account,Long> {
    Account findByUsername(String username);
}
