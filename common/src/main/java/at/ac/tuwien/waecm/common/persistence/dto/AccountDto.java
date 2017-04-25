package at.ac.tuwien.waecm.common.persistence.dto;

import at.ac.tuwien.waecm.common.persistence.dbo.Account;

/**
 * @author  Martin Griesler
 */
public class AccountDto {
	private Long id;
	private String username;
	private String password;

	private Double balance = new Double(0);

	public static AccountDto of(Account account) {
		AccountDto accountDto = new AccountDto();
		accountDto.id = account.getId();
		accountDto.username = account.getUsername();
		accountDto.password = account.getPassword();
		return accountDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
