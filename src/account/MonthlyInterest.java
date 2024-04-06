package account;

public class MonthlyInterest implements Runnable {
	private BankAccount account;
	private double interestRate;

	public MonthlyInterest(BankAccount account, double interestRate) {
		super();
		this.account = account;
		this.interestRate = interestRate;
	}

	@Override
	public void run() {
		double interestAmt = account.getBalance() * interestRate;
		account.deposit(interestAmt);
	}
}
