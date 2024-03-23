package account;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank {
	private static Map<String, BankAccount> accounts = new HashMap<String, BankAccount>();

	public Bank() {
		super();
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
	
	// bulk operation.
	public static void depositInterestsToAccounts() {
		for (BankAccount account : accounts.values()) {
			int interestAmount = new Random().nextInt(-100, 2000);
			account.deposit(interestAmount);
		}
	}
	
	
	public static void withdrawInterestFromAccounts() {
		for (BankAccount account : accounts.values()) {
			int interestAmount = new Random().nextInt(-100, 2000);
			account.withdraw(interestAmount);
		}
	}
	
	
	public void withdrawInterest(String acctNo) {
		BankAccount account = getBankAccount(acctNo);
		account.withdraw(new Random().nextInt(1500));
	}
	
	public void depositInterest(String acctNo) {
		BankAccount account = getBankAccount(acctNo);
		int eligibleAmount = (int)account.getBalance();
		int amount = new Random().nextInt(0, eligibleAmount + 1);
		account.deposit(amount);
	}
	
	public synchronized void systemMaintenance() {
		try {
			System.out.println("*********Banking activities stopped************");
			System.out.println("Currently running: " + Thread.currentThread().getName());
			wait(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
