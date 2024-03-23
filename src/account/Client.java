package account;

import java.util.Random;

public class Client implements Runnable {
	private String name;
	private BankAccount bankAccount;
	private ClientAction action;
	
	public Client(String name, BankAccount bankAccount, ClientAction action) {
		super();
		this.name = name;
		this.bankAccount = bankAccount;
		this.action = action;
	}

	@Override
	public void run() {	
		if (!Thread.currentThread().getThreadGroup().getName().equalsIgnoreCase("main")) {			
			try {
				Thread.sleep(2000);
				performTransaction();
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() +" interrupted by " + Thread.currentThread().getThreadGroup().getName());
				return;
			}
		}
		else {
			performTransaction();
		}
	}
	
	private void performTransaction() {
		switch (action) {
			case Deposit: {
				int amount = new Random().nextInt(500, 3000);
				bankAccount.deposit(amount);
				break;
			}
			case Withdraw: {
				int amount = new Random().nextInt(500, 3000);
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
