package at.ac.tuwien.waecm.app.controller;

import at.ac.tuwien.waecm.app.dto.TransactionDto;
import at.ac.tuwien.waecm.app.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.app.service.AccountService;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.waecm.app.service.TransactionService;

import java.time.ZonedDateTime;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.POST, path = "/transfer")
    public Long transfer(Double value, Long account, String description){

        TransactionDto newTransaction = new TransactionDto();

        newTransaction.setOwner(accountService.getUserInfo());
        //newTransaction.setTarget(accountService.findById(account));
        newTransaction.setDescription(description);
        newTransaction.setCreated(ZonedDateTime.now());

        return transactionService.createTransaction(newTransaction).getId();
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
