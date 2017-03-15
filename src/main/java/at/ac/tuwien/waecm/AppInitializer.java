package at.ac.tuwien.waecm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import at.ac.tuwien.waecm.persistence.dbo.Counter;
import at.ac.tuwien.waecm.persistence.repository.CounterRepository;
import at.ac.tuwien.waecm.service.CounterService;

@Component
public class AppInitializer implements CommandLineRunner {
	@Autowired private CounterRepository counterRepo;
	@Autowired private CounterService counterService;

	@Override
	public void run(String... arg0) throws Exception {
		Counter counter = counterRepo.save(new Counter(Long.valueOf(0)));
		counterService.setCounterId(counter.getId());
	}

}
