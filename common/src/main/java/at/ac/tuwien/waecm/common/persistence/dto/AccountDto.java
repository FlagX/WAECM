package at.ac.tuwien.waecm.common.persistence.dto;

import at.ac.tuwien.waecm.common.persistence.dbo.Account;

/**
 * @author  Martin Griesler
 */
public class AccountDto {
	private Long id;
	private String accountNumber;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private Double balance = new Double(0);

	public static AccountDto of(Account account) {
		AccountDto accountDto = new AccountDto();
		accountDto.id = account.getId();
		accountDto.accountNumber = account.getAccountNumber();
		accountDto.username = account.getUsername();
		accountDto.password = account.getPassword();
		accountDto.firstname = account.getFirstname();
		accountDto.lastname = account.getLastname();
		accountDto.balance = account.getBalance();
		return accountDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
