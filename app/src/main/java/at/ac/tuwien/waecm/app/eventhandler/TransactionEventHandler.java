package at.ac.tuwien.waecm.app.eventhandler;

import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import at.ac.tuwien.waecm.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static at.ac.tuwien.waecm.app.WebSocketConfiguration.MESSAGE_PREFIX;

/**
 * Created by Dominik on 26.04.17.
 */
@Component
@RepositoryEventHandler(Transaction.class)
public class TransactionEventHandler {

    private final SimpMessagingTemplate websocket;
    private final EntityLinks entityLinks;

    @Autowired
    AccountService accountService;

    @Autowired
    public TransactionEventHandler(SimpMessagingTemplate websocket,
                        EntityLinks entityLinks) {
        this.websocket = websocket;
        this.entityLinks = entityLinks;
    }

    @HandleAfterSave
    public void updateTransaction(Transaction transaction) {
        if ((transaction.getTarget().getId() == accountService.getUserInfo().getId()) &&
                (transaction.getCommited() != null)){
            this.websocket.convertAndSend(
                    MESSAGE_PREFIX, getPath(transaction));
        }
    }

    private String getPath(Transaction transaction) {
        return this.entityLinks.linkForSingleResource(transaction.getClass(),
                transaction.getId()).toUri().getPath();
    }

}
