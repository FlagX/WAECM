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
	List<TransactionDto> findAll() throws ServiceException;
	List<TransactionDto> findByInvolvedAccount(Long id) throws ServiceException;
	TransactionDto createTransaction(TransactionDto transaction) throws ServiceException;
	Boolean commitTransaction(Long id, String tan) throws ServiceException;

}
