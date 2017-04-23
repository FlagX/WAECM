package at.ac.tuwien.waecm.service.impl;

import at.ac.tuwien.waecm.persistence.repository.TransactionRepository;
import at.ac.tuwien.waecm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;


}
