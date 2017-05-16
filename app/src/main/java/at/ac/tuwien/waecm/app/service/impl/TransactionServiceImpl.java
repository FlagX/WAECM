package at.ac.tuwien.waecm.app.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import at.ac.tuwien.waecm.app.eventhandler.TransactionNotifier;
import at.ac.tuwien.waecm.app.service.AccountService;
import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import at.ac.tuwien.waecm.common.persistence.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.waecm.app.dto.TransactionDto;
import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import at.ac.tuwien.waecm.app.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.app.service.TransactionService;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;

import javax.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	@Autowired
	TransactionNotifier transactionNotifier;

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

		Transaction newTransaction = new Transaction();
		newTransaction.setCreated(ZonedDateTime.now());
		newTransaction.setCommited(null);
		newTransaction.setDescription(transaction.getDescription());
		newTransaction.setValue(transaction.getValue());

		Account currentUser = accountRepository.findOne(accountService.getUserInfo().getId());
		logger.info("current user has id "+currentUser.getId());
		Account targetUser = accountRepository.findOne(transaction.getTarget().getId());
		if(targetUser!=null){
			logger.info("target account with id "+transaction.getTarget().getId()+" was found");
		} else {
			logger.info("could not find target account");
		}

		newTransaction.setOwner(currentUser);
		newTransaction.setTarget(targetUser);

		//Generate mTAN
		String tan = "secret123"; //TODO: generate random value here
		newTransaction.setTan(digestToMD5(tan));
		logger.info("tan md5 hash for this transaction is:"+newTransaction.getTan());


		newTransaction = transactionRepository.save(newTransaction);
		logger.info("new transaction's id is "+newTransaction.getId());

		return convert(newTransaction);
	}

	@Override
	@Transactional
	public Boolean commitTransaction(Long id, String tan) {

		Transaction trans = transactionRepository.findOne(id);

		logger.info("transaction to commit: "+trans.toString());

		//check if still valid
		ZonedDateTime now = ZonedDateTime.now();

		Duration d = Duration.between(trans.getCreated(),now);

		logger.info(d.getSeconds() +" seconds passed since creation of transaction");

		if(d.getSeconds() > 60*5){
			//TODO: handle too old TAN!
			logger.info("transaction is too old! can't commit it anymore!");
			return false;
		}
		//check if mTan is valid
		if(!trans.getTan().equals(digestToMD5(tan))){
			//TODO: handle invalid TAN!
			logger.info("invalid tan!");
			return false;
		}

		trans.setCommited(ZonedDateTime.now());
		trans = transactionRepository.save(trans);

		logger.info("transfare the value "+trans.getValue());

		Account owner = trans.getOwner();

		if(owner==null){
			logger.info("owner of this transaction not found!");
		}
		Account target = trans.getTarget();

		if(target ==null){
			logger.info("target of this transaction not found!");
		}

		owner.setBalance(owner.getBalance()-trans.getValue());
		target.setBalance(target.getBalance()+trans.getValue());

		logger.info("transaction committed at "+trans.getCommited().format(DateTimeFormatter.ISO_DATE));

		transactionNotifier.sendNotification(trans);

		return true;
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
		acc.setAccountNumber(transaction.getOwner().getAccountNumber());
		acc.setFirstname(transaction.getOwner().getFirstname());
		acc.setLastname(transaction.getOwner().getLastname());
		result.setOwner(acc);

		acc = new AccountDto();
		acc.setId(transaction.getTarget().getId());
		acc.setUsername(transaction.getTarget().getUsername());
		acc.setAccountNumber(transaction.getTarget().getAccountNumber());
		acc.setFirstname(transaction.getTarget().getFirstname());
		acc.setLastname(transaction.getTarget().getLastname());
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

	private String digestToMD5(String tan){

		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(tan.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			return bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
