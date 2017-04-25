package at.ac.tuwien.waecm.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.tuwien.waecm.app.dto.TransactionDto;

/**
 * @author  Martin Griesler
 */
@Service
public interface TransactionService {
	List<TransactionDto> findAll();
}
