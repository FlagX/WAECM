package at.ac.tuwien.waecm.app;

import at.ac.tuwien.waecm.app.service.ServiceException;
import at.ac.tuwien.waecm.app.service.TransactionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Group6ApplicationTests extends AbstractTest {

	@Autowired private TransactionService transactionService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void transactions() throws ServiceException {
		transactionService.findAll();
	}
}
