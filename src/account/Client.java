package account;

import java.util.Random;

public class Client implements Runnable {
	private String name;
	private BankAccount bankAccount;
	private boolean isDeposit;
	private ClientAction action;
	
	public Client(String name, BankAccount bankAccount) {
		super();
		this.name = name;
		this.bankAccount = bankAccount;
	}
	
	public Client(String name, BankAccount bankAccount, ClientAction action) {
		super();
		this.name = name;
		this.bankAccount = bankAccount;
		this.action = action;
	}

	@Override
	public void run() {
//		System.out.println("Current thread state of " + Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
		int amount = new Random().nextInt((int)bankAccount.getBalance());
		amount = (amount < 0) ? 0 : amount;
		switch (action) {
			case Deposit: {
				bankAccount.deposit(amount);
				break;
			}
			case Withdraw: {
				bankAccount.withdraw(amount);
				break;
			}
			case CheckBalance: {
				bankAccount.checkBalance();
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + action);
		}
		
	}
	
	
}
