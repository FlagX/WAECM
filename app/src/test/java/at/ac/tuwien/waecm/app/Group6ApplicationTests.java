package at.ac.tuwien.waecm.app;

import at.ac.tuwien.waecm.app.service.TransactionService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Group6ApplicationTests {

	@Autowired private TransactionService transactionService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void transactions() {
		transactionService.findAll();
	}

}
