package at.ac.tuwien.waecm.service;

import org.springframework.stereotype.Service;

import at.ac.tuwien.waecm.dto.CounterDto;

/**
 * @author Martin Griesler
 */
@Service
public interface CounterService {
	CounterDto getCounter();
	void incrementCounter();
	void setCounterId(Long counterId);
}
