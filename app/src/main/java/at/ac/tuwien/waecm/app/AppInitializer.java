package at.ac.tuwien.waecm.app;

import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import at.ac.tuwien.waecm.app.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import at.ac.tuwien.waecm.common.persistence.repository.AccountRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {
	@Autowired private AccountRepository accountRepository;
	@Autowired private TransactionRepository transactionRepository;

	@Override
	public void run(String... arg0) throws Exception {
		if(accountRepository.findByUsername("user") == null) {
			accountRepository.save(new Account("user", "password"));
		}

		transactionRepository.save(createTransactions());
	}

	private List<Transaction> createTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			transactions.add(createTransaction(i));
		}
		return transactions;
	}

	private Transaction createTransaction(int i) {
		return new Transaction("Transaction" + i, accountRepository.findByUsername("user"), accountRepository.findByUsername("user"), (double)i, new Date(0));
	}

}
