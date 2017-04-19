package at.ac.tuwien.waecm.app.controller;

import at.ac.tuwien.waecm.app.dto.CounterDto;
import at.ac.tuwien.waecm.app.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Martin Griesler
 */
@RestController
public class CounterController {
	@Autowired private CounterService counterService;

	@RequestMapping(method = RequestMethod.GET, path = "/counter")
	public String getCounter() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		CounterDto counter = counterService.getCounter();
		return mapper.writeValueAsString(counter);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/counter")
	public String incrementCounter() throws Exception {
		counterService.incrementCounter();
		return "Successfully incremented counter.";
	}
}
