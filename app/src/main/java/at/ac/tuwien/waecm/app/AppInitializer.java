package at.ac.tuwien.waecm.app;

import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import at.ac.tuwien.waecm.app.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import at.ac.tuwien.waecm.common.persistence.repository.AccountRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {
	@Autowired private AccountRepository accountRepository;
	@Autowired private TransactionRepository transactionRepository;
	@Autowired private PasswordEncoder passwordEncoder;

	private String[] users = {"max", "gabi", "erika"};

	private Random random = new Random();

	@Override
	public void run(String... arg0) throws Exception {
		addUsers();
		transactionRepository.save(createTransactions());
	}

	private void addUsers() {
		if(accountRepository.findByUsername(users[0]) == null) {
			accountRepository.save(new Account(users[0], passwordEncoder.encode("maxmax"), "1", "Max", "Mustermann", 1200.22));
		}
		if(accountRepository.findByUsername(users[1]) == null) {
			accountRepository.save(new Account(users[1], passwordEncoder.encode("gabigabi"),"2", "Gabi", "Musterfrau", 20012.23));
		}
		if(accountRepository.findByUsername(users[2]) == null) {
			accountRepository.save(new Account(users[2], passwordEncoder.encode("erikaerika"),"3", "Erika", "Test", 33243.24));
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
		int first = random.nextInt(users.length);
		Account owner = accountRepository.findByUsername(users[first]);
		Account target = accountRepository.findByUsername(users[(first+random.nextInt(users.length-1)+1) % users.length]);
		Transaction transaction = new Transaction("Transaction " + i, owner, target, (double)random.nextInt(1000), ZonedDateTime.now().minusDays(i), ZonedDateTime.now().minusDays(i));
		return transaction;
	}

}
