package at.ac.tuwien.waecm.dto;

import java.time.ZonedDateTime;

import at.ac.tuwien.waecm.persistence.dbo.Account;

public class TransactionDbo {
	private Long id;
	private String description;
	private Account owner;
	private Account target;
	private Double value;
	private ZonedDateTime created;
	private ZonedDateTime commited;

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

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public Account getTarget() {
		return target;
	}

	public void setTarget(Account target) {
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
