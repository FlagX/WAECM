package at.ac.tuwien.waecm.app.service;

import org.springframework.stereotype.Service;

import at.ac.tuwien.waecm.app.dto.CounterDto;

/**
 * @author Martin Griesler
 */
@Service
public interface CounterService {
	CounterDto getCounter();
	void incrementCounter();
}
