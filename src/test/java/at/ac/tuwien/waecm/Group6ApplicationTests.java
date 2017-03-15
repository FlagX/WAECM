package at.ac.tuwien.waecm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import at.ac.tuwien.waecm.service.CounterService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Group6ApplicationTests {
	@Autowired private CounterService counterService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getCounter(){
		counterService.getCounter();
	}

}
