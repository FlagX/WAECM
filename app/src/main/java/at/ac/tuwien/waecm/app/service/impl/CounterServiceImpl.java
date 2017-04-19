package at.ac.tuwien.waecm.app.service.impl;

import at.ac.tuwien.waecm.app.persistence.repository.CounterRepository;
import at.ac.tuwien.waecm.app.service.CounterService;
import at.ac.tuwien.waecm.app.dto.CounterDto;
import at.ac.tuwien.waecm.app.persistence.dbo.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Griesler
 */
@Service
public class CounterServiceImpl implements CounterService {
	@Autowired private CounterRepository counterRepository;

	@Override
	public CounterDto getCounter() {
		List<Counter> counterList = new ArrayList<>();
		counterRepository.findAll().forEach(counterList::add);
		if(counterList.isEmpty()) {
			Counter counter = counterRepository.save(new Counter(Long.valueOf(0)));
			return new CounterDto(counter.getCounter());
		}
		return new CounterDto(counterList.get(0).getCounter());

	}

	@Override
	public void incrementCounter() {
		List<Counter> counterList = new ArrayList<>();
		counterRepository.findAll().forEach(counterList::add);
		if(counterList.isEmpty()) {
			counterRepository.save(new Counter(Long.valueOf(1)));
		}
		else {
			Counter counter = counterList.get(0);
			counter.setCounter(counter.getCounter() + 1);
			counterRepository.save(counter);
		}
	}

}
