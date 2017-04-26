package at.ac.tuwien.waecm.app.controller;

import at.ac.tuwien.waecm.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
