package at.ac.tuwien.waecm.app.service.impl;

import at.ac.tuwien.waecm.app.service.AccountService;
import at.ac.tuwien.waecm.app.service.ServiceException;
import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;
import at.ac.tuwien.waecm.common.persistence.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by e1126 on 26.04.2017.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public AccountDto getUserInfo(String username) throws ServiceException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new ServiceException(String.format("User %s not found", username));
        }
        return AccountDto.of(account);
    }

    @Override
    public AccountDto findById(Long id) throws ServiceException {
        Account account = accountRepository.findOne(id);
        if (account == null) {
            throw new ServiceException(String.format("User with id %d not found", id));
        }
        return AccountDto.of(account);
    }

}
