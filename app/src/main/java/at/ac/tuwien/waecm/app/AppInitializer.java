package at.ac.tuwien.waecm.app;

import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import at.ac.tuwien.waecm.app.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import at.ac.tuwien.waecm.common.persistence.repository.AccountRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {
	@Autowired private AccountRepository accountRepository;
	@Autowired private TransactionRepository transactionRepository;
	@Autowired private PasswordEncoder passwordEncoder;

	private String[] users = {"user", "harald", "karl", "franz", "anna"};

	@Override
	public void run(String... arg0) throws Exception {

		for(String e : users) {
			addUser(e);
		}

		transactionRepository.save(createTransactions());
	}

	private void addUser(String name) {
		if(accountRepository.findByUsername(name) == null) {
			accountRepository.save(new Account(name, passwordEncoder.encode("password")));
		}
	}

	private List<Transaction> createTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		for(int i = 0; i < 30; i++) {
			Transaction transaction = createTransaction(i);
			transactions.add(transaction);
		}
		return transactions;
	}

	private Transaction createTransaction(int i) {
		Account owner = accountRepository.findByUsername(users[(i+1) % (users.length-1)]);
		Account target = accountRepository.findByUsername(users[(i) % (users.length-1)]);
		Transaction transaction = new Transaction("Transaction" + i, owner, target, (double)i, ZonedDateTime.now().minusDays(i));
		return transaction;
	}

}
