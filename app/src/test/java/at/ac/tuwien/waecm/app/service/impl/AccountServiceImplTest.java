package at.ac.tuwien.waecm.app.service.impl;

import at.ac.tuwien.waecm.app.AbstractTest;
import at.ac.tuwien.waecm.app.service.ServiceException;
import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;
import at.ac.tuwien.waecm.common.persistence.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AccountServiceImplTest extends AbstractTest{

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private AccountRepository accountRepository;

    private static Account account;

    @Before
    public void setUp() {
        if(account == null) {
            account = accountRepository.findAll().iterator().next();
        }
    }

    @Test
    public void getUserInfo() throws ServiceException {
        AccountDto actual = accountService.getUserInfo(account.getUsername());

        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(account.getId()));
        assertThat(actual.getUsername(), is(account.getUsername()));
        assertThat(actual.getAccountNumber(), is(account.getAccountNumber()));
        assertThat(actual.getFirstname(), is(account.getFirstname()));
        assertThat(actual.getLastname(), is(account.getLastname()));
        assertThat(actual.getBalance(), is(account.getBalance()));
    }

    @Test(expected = ServiceException.class)
    public void getUserInfoWithInvalidUsername() throws ServiceException {
        accountService.getUserInfo("someOtherDude");
    }

    @Test
    public void findById() throws ServiceException {
        AccountDto actual = accountService.findById(account.getId());

        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(account.getId()));
    }

    @Test(expected = ServiceException.class)
    public void findByIdWithInvalidId() throws ServiceException {
        accountService.findById(12345L);
    }
}