package account;

public enum ClientAction {
	Deposit(1),
	Withdraw(2),
	CheckBalance(3);

	private int value;
	private ClientAction(int val) {
		this.value = val;
	}
	
	public int getValue() {
		return value;
	}
	
	public static ClientAction fromInt(int value) {
        for (ClientAction action : values()) {
            if (action.value == value) {
                return action;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}
