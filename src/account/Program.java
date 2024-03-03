package account;

import java.util.Iterator;
import java.util.Random;

public class Program {
	private static Bank bank = new Bank();

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
			
			bank.withdrawInterest(acctNo);
			// start the thread.
			clientThreads[i].start();
		}
		// execute the bank interest deposit.
		// bank depositing money to random account.
//		System.out.println("=============================");
//		System.out.println("Random deposit made by Bank for an account.");
//		bank.depositInterest("2");
//		System.out.println("=============================");
		
		// interest charging
		System.out.println("=============================");
		System.out.println("Charging interests from all accounts.");
		Bank.withdrawInterestFromAccounts();
		System.out.println("=============================");
		
		// sudden maintenance
//		bank.systemMaintenance();
		
		// monthly interest deposition
		System.out.println("=============================");
		System.out.println("Depositing monthly interests to all accounts.");
		Bank.depositInterestsToAccounts();
		System.out.println("=============================");
		
		
		
	}
	
	private static void ClientGroupDemo() {
		ThreadGroup VIPGroup = new ThreadGroup("VIP");
		ThreadGroup regularGroup = new ThreadGroup("Regular");
	}

}
