package at.ac.tuwien.waecm.app.controller;

import at.ac.tuwien.waecm.app.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.waecm.app.service.TransactionService;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST, path = "/transfer")
    public Long transfer(Double value, Long account, String description){
        return 0L;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/commit")
    public boolean commitTransaction(String mTan, Long transactionId){
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/transactionsByAccountId")
    public String getTransactions(AccountDto account) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(transactionService.findAll());
    }
}
