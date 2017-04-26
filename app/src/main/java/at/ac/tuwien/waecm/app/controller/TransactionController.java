package at.ac.tuwien.waecm.app.controller;

import at.ac.tuwien.waecm.app.dto.TransactionDto;
import at.ac.tuwien.waecm.app.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.waecm.app.service.TransactionService;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

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
    public List<TransactionDto> getTransactions(@RequestParam("accountid") Long id) throws JsonProcessingException {
        logger.info("recieved account name: " + id);
        System.out.println("recieved account name: " + id);
        List<TransactionDto> result = transactionService.findByInvolvedAccount(id);
        System.out.println("returning " + result.size() + "transactions");

        return result;
    }
}
