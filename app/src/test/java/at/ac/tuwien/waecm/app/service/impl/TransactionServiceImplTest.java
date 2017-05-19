package at.ac.tuwien.waecm.app.service.impl;

import at.ac.tuwien.waecm.app.AbstractTest;
import at.ac.tuwien.waecm.app.dto.TransactionDto;
import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import at.ac.tuwien.waecm.app.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.app.service.ServiceException;
import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;
import at.ac.tuwien.waecm.common.persistence.repository.AccountRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TransactionServiceImplTest extends AbstractTest {

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void createTransaction() throws ServiceException {
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        Account owner = accountIterator.next();
        Account target = accountIterator.next();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription("Description");
        transactionDto.setValue(12.0);
        transactionDto.setOwner(AccountDto.of(owner));
        transactionDto.setTarget(AccountDto.of(target));

        TransactionDto returned = transactionService.createTransaction(transactionDto);

        assertThat(returned, is(notNullValue()));
        assertThat(returned.getId(), is(notNullValue()));
        assertThat(returned.getOwner().getId(), is(owner.getId()));
        assertThat(returned.getTarget().getId(), is(target.getId()));
        assertThat(returned.getDescription(), is("Description"));
        assertThat(returned.getValue(), is(12.0));

        Transaction transaction = transactionRepository.findOne(returned.getId());

        assertThat(transaction.getTan(), is(notNullValue()));
        assertThat(transaction.getOwner().getBalance(), is(owner.getBalance()));
        assertThat(transaction.getTarget().getBalance(), is(target.getBalance()));
    }

    @Test(expected = ServiceException.class)
    public void createTransactionWithOwnerNotFound() throws ServiceException {
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        Account owner = accountIterator.next();
        Account target = accountIterator.next();

        owner.setUsername("someOtherDue");

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription("Description");
        transactionDto.setValue(12.0);
        transactionDto.setOwner(AccountDto.of(owner));
        transactionDto.setTarget(AccountDto.of(target));

        transactionService.createTransaction(transactionDto);
    }

    @Test(expected = ServiceException.class)
    public void createTransactionWithTargetNotFound() throws ServiceException {
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        Account owner = accountIterator.next();
        Account target = accountIterator.next();

        target.setId(12345L);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription("Description");
        transactionDto.setValue(12.0);
        transactionDto.setOwner(AccountDto.of(owner));
        transactionDto.setTarget(AccountDto.of(target));

        transactionService.createTransaction(transactionDto);
    }

    @Test
    public void commitTransaction() throws ServiceException {
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        Account owner = accountIterator.next();
        Account target = accountIterator.next();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription("Description");
        transactionDto.setValue(12.0);
        transactionDto.setOwner(AccountDto.of(owner));
        transactionDto.setTarget(AccountDto.of(target));

        TransactionDto returned = transactionService.createTransaction(transactionDto);

        boolean result = transactionService.commitTransaction(returned.getId(), "secret123");

        assertThat(result, is(true));

        Transaction commitedTransaction = transactionRepository.findOne(returned.getId());

        assertThat(commitedTransaction.getCommited(), is(notNullValue()));
        assertThat(commitedTransaction.getOwner().getBalance(), is(owner.getBalance() - 12.0));
        assertThat(commitedTransaction.getTarget().getBalance(), is(target.getBalance() + 12.0));
    }

    @Test
    public void commitTransactionWithInvalidTan() throws ServiceException {
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        Account owner = accountIterator.next();
        Account target = accountIterator.next();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription("Description");
        transactionDto.setValue(12.0);
        transactionDto.setOwner(AccountDto.of(owner));
        transactionDto.setTarget(AccountDto.of(target));

        TransactionDto returned = transactionService.createTransaction(transactionDto);

        boolean result = transactionService.commitTransaction(returned.getId(), "secret123xxx");

        assertThat(result, is(false));

        Transaction commitedTransaction = transactionRepository.findOne(returned.getId());

        assertThat(commitedTransaction.getCommited(), is(nullValue()));
    }

    @Test
    public void commitTransactionWithinvalidId() throws ServiceException {
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        Account owner = accountIterator.next();
        Account target = accountIterator.next();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription("Description");
        transactionDto.setValue(12.0);
        transactionDto.setOwner(AccountDto.of(owner));
        transactionDto.setTarget(AccountDto.of(target));

        TransactionDto returned = transactionService.createTransaction(transactionDto);

        boolean result = transactionService.commitTransaction(returned.getId() + 100000L, "secret123");

        assertThat(result, is(false));

        Transaction commitedTransaction = transactionRepository.findOne(returned.getId());

        assertThat(commitedTransaction.getCommited(), is(nullValue()));
    }

    @Test
    public void commitTransactionWithTooOldTransaction() throws ServiceException {
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        Account owner = accountIterator.next();
        Account target = accountIterator.next();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription("Description");
        transactionDto.setValue(12.0);
        transactionDto.setOwner(AccountDto.of(owner));
        transactionDto.setTarget(AccountDto.of(target));

        TransactionDto returned = transactionService.createTransaction(transactionDto);
        Transaction transaction = transactionRepository.findOne(returned.getId());
        transaction.setCreated(transaction.getCreated().minusMonths(1));
        transaction = transactionRepository.save(transaction);

        boolean result = transactionService.commitTransaction(returned.getId() + 100000L, "secret123");

        assertThat(result, is(false));

        Transaction commitedTransaction = transactionRepository.findOne(returned.getId());

        assertThat(commitedTransaction.getCommited(), is(nullValue()));
    }
}
