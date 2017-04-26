package at.ac.tuwien.waecm.app.controller;

import at.ac.tuwien.waecm.app.service.AccountService;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Stefan on 25.04.2017.
 */
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, path = "/userinfo")
    public String userinfo() throws JsonProcessingException {
        AccountDto currentUser = accountService.getUserInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(currentUser);
    }
}
