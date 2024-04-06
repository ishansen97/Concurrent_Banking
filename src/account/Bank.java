package account;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import exceptions.MoneyDepositException;
import exceptions.MoneyWithdrawException;

public class Bank {
	private Map<String, BankAccount> accounts = new HashMap<String, BankAccount>();
	private double taxRate;

	public Bank() {
		super();
		this.taxRate = 0.12;
	}
	
	public Bank(double taxRate) {
		this.taxRate = taxRate;
	}

	public Map<String, BankAccount> getBankAccounts() {
		return accounts;
	}
	
	public BankAccount getBankAccount(String acctNumber) {
		return accounts.get(acctNumber);
	}
	
	public synchronized void addAccount(BankAccount account) {
        accounts.put(account.getAcctNo(), account);
    }
	
	public void collectWithholdingTax(String acctNo) {
		BankAccount account = getBankAccount(acctNo);
		double interestAmount = account.getBalance() * this.taxRate;
		account.collectTax(interestAmount);
	}
	
	public synchronized void transferMoney(BankAccount source, BankAccount dest, double amount) {
		try {
			source.withdrawForTransfer(amount);
			System.out.println("=== WITHDRAWN Rs." +amount+" from account " + source.getAcctNo() + " ===");
			Thread.sleep(1000);
			dest.depositByTransfer(amount);
			System.out.println("=== DEPOSITED Rs." +amount+" to account " + dest.getAcctNo() + " ===");
		} 
		catch (MoneyWithdrawException ex2) {
			System.out.println(ex2.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
