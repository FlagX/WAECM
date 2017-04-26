package at.ac.tuwien.waecm.app.service;

import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    AccountDto getUserInfo();

    AccountDto findById(Long id);
}
