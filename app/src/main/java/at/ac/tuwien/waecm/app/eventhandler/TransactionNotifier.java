package at.ac.tuwien.waecm.app.eventhandler;

import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static at.ac.tuwien.waecm.app.WebSocketConfiguration.MESSAGE_PREFIX;

/**
 * Created by Dominik on 26.04.17.
 */
@Service
public class TransactionNotifier{

    private static final Logger logger = LoggerFactory.getLogger(TransactionNotifier.class);

    @Autowired
    private SimpMessagingTemplate websocket;

    public void sendNotification(Transaction transaction) {
        logger.info("sending transaction notification to user with id="+transaction.getTarget().getId());
        this.websocket.convertAndSend(MESSAGE_PREFIX,"incomingTransactionNotification");
    }

}
