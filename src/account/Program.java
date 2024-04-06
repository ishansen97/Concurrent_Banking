package account;

import java.util.Iterator;
import java.util.Random;

public class Program {
	private static Bank bank = new Bank(0.05);

	public static void main(String[] args) {				
		// clients
		Thread[] clientThreads = new Thread[10];
				
		int randomAction = 0;
		double initialBalance = 1500;
		Thread.currentThread().setName("Bank");
		
		// create 10 runnable objects
		for (int i = 0; i < 10; i++) {
			String clientName = "Client " + (i+1);
			String acctNo = String.valueOf((i+1));
			BankAccount account = new BankAccount(acctNo, clientName, initialBalance);
			bank.addAccount(account);
			
			randomAction = new Random().nextInt(1,4);
			Runnable runnable = new Client(clientName, account, ClientAction.fromInt(randomAction));
			clientThreads[i] = new Thread(runnable, clientName);
		}
		
		// run the individual threads
		for (int i = 0; i < clientThreads.length; i++) {
			Thread thread = clientThreads[i];
			String acctNo = String.valueOf((i+1));
			// start the client thread.
			thread.start();
			// bank withdraws amount from the individual account.
			bank.collectWithholdingTax(acctNo);
			// simultaneously, the bank transfers money from one account to another
			int randomAcct = new Random().nextInt(0, 10);
			// this condition ensures that the source and destination accounts are different.
			if (randomAcct + 1 != (i+1)) {
				Thread randomAcctThread = clientThreads[randomAcct];
				int transferAmount = new Random().nextInt(100,2000);
				bank.transferMoney(bank.getBankAccount(acctNo), bank.getBankAccount(String.valueOf(randomAcct+1)), transferAmount);
			}
			
			// deposit monthly interest to the account.
			Runnable monthlyInterestDepositor = new MonthlyInterest(bank.getBankAccount(acctNo), 0.12);
			Thread monthlyInterestThread = new Thread(monthlyInterestDepositor, "Monthly Interest Calculator".toUpperCase());
			monthlyInterestThread.start();
		}
		
		// thread group demo
		clientThreadGroupDemo();
	}
	
	private static void clientThreadGroupDemo() {
		System.out.println("===== Thread Group Demo =====");
		Bank threadGroupBank = new Bank();
		ThreadGroup vipGroup = new ThreadGroup("VIP");
		ThreadGroup regularGroup = new ThreadGroup("Regular");
		Thread[] clientThreads = new Thread[10];
		
		int initialBalance = new Random().nextInt(500, 3000);
		int clientAction = 0;
		
		// adding clients
		for (int i = 0; i < 10; i++) {
			String clientName = "Client " + (i+1);
			String acctNo = String.valueOf((i+1) * 1000);
			BankAccount account = new BankAccount(acctNo, clientName, initialBalance);
			threadGroupBank.addAccount(account);
			
			// creating the runnable instances
			clientAction = new Random().nextInt(1,4);
			Runnable runnable = new Client(clientName, account, ClientAction.fromInt(clientAction));
			// creating 5 VIP clients
			Thread clientThread = null;
			if (i < 5) {
				clientName += " VIP";
				clientThread = new Thread(vipGroup, runnable, clientName);
			}
			// for regular clients
			else {
				clientName += " REGULAR";
				clientThread = new Thread(regularGroup, runnable, clientName);				
			}
			clientThreads[i] = clientThread;
		}
		
		// running client threads.
		for (Thread thread : clientThreads) {
			thread.start();
		}
		// interrupt the regular clients.
		regularGroup.interrupt();	
	}

}
