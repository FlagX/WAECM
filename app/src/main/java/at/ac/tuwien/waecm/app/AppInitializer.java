package at.ac.tuwien.waecm.app;

import at.ac.tuwien.waecm.app.persistence.dbo.Account;
import at.ac.tuwien.waecm.app.persistence.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {
	@Autowired private AccountRepository accountRepository;

	@Override
	public void run(String... arg0) throws Exception {
		if(accountRepository.findByUsername("user") == null) {
			accountRepository.save(new Account("user", "password"));
		}
	}

}
