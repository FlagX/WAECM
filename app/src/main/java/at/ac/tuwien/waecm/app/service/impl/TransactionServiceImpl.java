package at.ac.tuwien.waecm.app.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.waecm.app.dto.TransactionDto;
import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import at.ac.tuwien.waecm.app.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.app.service.TransactionService;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

	@Override
	public List<TransactionDto> findAll() {
		List<TransactionDto> result = new ArrayList<>();
		transactionRepository.findAll().forEach(x -> {
			result.add(convert(x));
		});
		return result;
	}

	@Override
	public TransactionDto createTransaction(TransactionDto transaction) {

		//TODO

		return null;
	}

	private TransactionDto convert(Transaction transaction) {
		TransactionDto result = new TransactionDto();
		result.setId(transaction.getId());
		result.setValue(transaction.getValue());
		result.setDescription(transaction.getDescription());
		result.setCommited(transaction.getCommited());
		result.setCreated(transaction.getCreated());

		AccountDto acc = new AccountDto();
		acc.setId(transaction.getOwner().getId());
		acc.setUsername(transaction.getOwner().getUsername());
		result.setOwner(acc);

		acc = new AccountDto();
		acc.setId(transaction.getTarget().getId());
		acc.setUsername(transaction.getTarget().getUsername());
		result.setTarget(acc);

		return result;
	}

	@Override
	public List<TransactionDto> findByInvolvedAccount(Long id) {
		List<TransactionDto> result = new ArrayList<>();
		transactionRepository.findByInvolvedAccount(id).forEach(x -> {
			result.add(convert(x));
		});

		result.sort(Comparator.comparing(TransactionDto::getCreated));

		return result;
	}
}
