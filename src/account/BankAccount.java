package account;

import exceptions.MoneyDepositException;
import exceptions.MoneyWithdrawException;

public class BankAccount {
	private String acctNo;
	private String acctHolder;
	private double balance;
	
	public BankAccount(String acctNo, String acctHolder, double balance) {
		super();
		this.acctNo = acctNo;
		this.acctHolder = acctHolder;
		this.balance = balance;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctHolder() {
		return acctHolder;
	}

	public void setAcctHolder(String acctHolder) {
		this.acctHolder = acctHolder;
	}

	public synchronized double getBalance() {
		return balance;
	}

	public synchronized void setBalance(double balance) {
		this.balance = balance;
	}
	
	private synchronized boolean canWithdraw(double amount) {
		return (this.getBalance() > 0 && (amount > 0) && (balance - amount) > 0);
	}
	
	private synchronized boolean canDeposit(double amount) {
		return (amount > 0);
	}
	
	// withdraw method
	public synchronized void withdraw(double amount) {
		if (canWithdraw(amount)) {
			this.setBalance(this.getBalance() - amount);
			System.out.println(Thread.currentThread().getName()+" has WITHDRAWN: Rs." + amount + " from the account " +acctNo+", remaining amount: Rs." + getBalance());
		}
		else {
			throw new IllegalArgumentException("Invalid amount Rs." +amount+" tried to be withdrawn by " + Thread.currentThread().getName() + " on account " + acctNo + ". Account current balance " + this.getBalance());
		}
	}
	
	// withdraw method (for transferring)
	public synchronized void withdrawForTransfer(double amount) throws MoneyWithdrawException {
		if (canWithdraw(amount)) {
			this.setBalance(this.getBalance() - amount);
			System.out.println(Thread.currentThread().getName()+" has WITHDRAWN (as part of transfer): Rs." + amount + " from the account " +acctNo+", remaining amount: Rs." + getBalance());
		}
		else {
			throw new MoneyWithdrawException("/// Invalid amount Rs." +amount+" tried to be withdrawn by " + Thread.currentThread().getName() + " on account " + acctNo + " (as part of transfer). Account current balance " + this.getBalance());
		}
	}
	
	// withdraw tax amount
	public synchronized void collectTax(double amount) {
		if (canWithdraw(amount)) {
			this.setBalance(this.getBalance() - amount);
			System.out.println("#### " +Thread.currentThread().getName()+" has WITHDRAWN: Rs." + amount + " from the account " +acctNo+", as WITHHOLDING TAX. Remaining amount: Rs." + getBalance() + " ####");
		}
		else {
			throw new IllegalArgumentException("#### " +"Invalid amount Rs." +amount+" tried to be withdrawn by " + Thread.currentThread().getName() + " on account " + acctNo + " as WITHHOLDING TAX. Account current balance " + this.getBalance() + " ####");
		}
	}
	
	// deposit method
	public synchronized void deposit(double amount) {
		if (canDeposit(amount)) {
			this.setBalance(this.getBalance() + amount);;
			System.out.println(Thread.currentThread().getName()+" has DEPOSITED: Rs." + amount + " to the account " +acctNo+", remaining amount: Rs." + getBalance());
		}
		else {
			throw new IllegalArgumentException("Invalid amount Rs." +amount+" tried to be deposited by " + Thread.currentThread().getName() + " on account " + acctNo + ". Account current balance " + this.getBalance());		
		}
	}
	
	// deposit method (after money transferring)
	public synchronized void depositByTransfer(double amount) {
		if (canDeposit(amount)) {
			this.setBalance(this.getBalance() + amount);;
			System.out.println(Thread.currentThread().getName()+" has DEPOSITED (as part of transfer): Rs." + amount + " to the account " +acctNo+", remaining amount: Rs." + getBalance());
		}
		else {
			throw new IllegalArgumentException("&&& Invalid amount Rs." +amount+" tried to be deposited by " + Thread.currentThread().getName() + " on account " + acctNo + "(as part of transfer). Account current balance " + this.getBalance());		
		}
	}
	
	// check balance
	public synchronized void checkBalance() {
		System.out.println("The current balance checked by " +Thread.currentThread().getName()+" : Rs." + getBalance());
	}
}
