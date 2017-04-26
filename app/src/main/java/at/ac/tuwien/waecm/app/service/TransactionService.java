package at.ac.tuwien.waecm.app.service;

import java.util.List;

import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;

import org.springframework.stereotype.Service;

import at.ac.tuwien.waecm.app.dto.TransactionDto;

/**
 * @author  Martin Griesler
 */
@Service
public interface TransactionService {
	List<TransactionDto> findAll();
	List<TransactionDto> findByInvolvedAccount(Long id);
	TransactionDto createTransaction(TransactionDto transaction);

}
