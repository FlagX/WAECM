package at.ac.tuwien.waecm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.waecm.dto.CounterDto;
import at.ac.tuwien.waecm.persistence.dbo.Counter;
import at.ac.tuwien.waecm.persistence.repository.CounterRepository;
import at.ac.tuwien.waecm.service.CounterService;

/**
 * @author Martin Griesler
 */
@Service
public class CounterServiceImpl implements CounterService {
	@Autowired private CounterRepository counterRepository;
	private Long counterId;


	@Override
	public CounterDto getCounter() {
		Counter counter;
		if(counterId != null) {
			counter = counterRepository.findOne(counterId);
			return new CounterDto(counter.getCounter());
		}
		return null;

	}

	public Long getCounterId() {
		return counterId;
	}

	public void setCounterId(Long counterId) {
		this.counterId = counterId;
	}

	@Override
	public void incrementCounter() {
		Counter counter = counterRepository.findOne(counterId);
		counter.setCounter(counter.getCounter()+1);
		counterRepository.save(counter);
	}

}
