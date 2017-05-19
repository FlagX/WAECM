package at.ac.tuwien.waecm.app.dto;

import java.time.ZonedDateTime;

import at.ac.tuwien.waecm.app.persistence.dbo.Transaction;
import at.ac.tuwien.waecm.common.persistence.dbo.Account;
import at.ac.tuwien.waecm.common.persistence.dto.AccountDto;

/**
 * @author  Martin Griesler
 */
public class TransactionDto {
	private Long id;
	private String description;
	private AccountDto owner;
	private AccountDto target;
	private Double value;
	private ZonedDateTime created;
	private ZonedDateTime commited;

	public static TransactionDto of(Transaction transaction) {
		if (transaction == null) {
			return null;
		}

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setId(transaction.getId());
		transactionDto.setDescription(transaction.getDescription());
		transactionDto.setOwner(AccountDto.of(transaction.getOwner()));
		transactionDto.setTarget(AccountDto.of(transaction.getTarget()));
		transactionDto.setValue(transaction.getValue());
		transactionDto.setCreated(transaction.getCreated());
		transactionDto.setCommited(transaction.getCommited());

		return transactionDto;
	}

	public Long getId(){
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AccountDto getOwner() {
		return owner;
	}

	public void setOwner(AccountDto owner) {
		this.owner = owner;
	}

	public AccountDto getTarget() {
		return target;
	}

	public void setTarget(AccountDto target) {
		this.target = target;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public ZonedDateTime getCreated() {
		return created;
	}

	public void setCreated(ZonedDateTime created) {
		this.created = created;
	}

	public ZonedDateTime getCommited() {
		return commited;
	}

	public void setCommited(ZonedDateTime commited) {
		this.commited = commited;
	}

}
