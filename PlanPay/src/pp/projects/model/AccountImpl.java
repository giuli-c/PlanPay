package pp.projects.model;

import java.util.ArrayList;
import java.util.List;

public class AccountImpl implements Account{
	
	private String name;
	private Double balance;
	private List<Transaction> transactionList;
	
	public AccountImpl(String name) {
		this.name = name;
		this.balance = 0.0;
		this.transactionList = new ArrayList<>();
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getBalance() {
		return Math.floor(balance*100) / 100;
	}

	@Override
	public void addBalance(double amount) {
		this.balance += amount;		
	}

	@Override 
	public boolean subBalance(double amount) {
		boolean legalOp = false;
		if(this.balance - amount >= 0.0) {
			this.balance -= amount;
			legalOp = true;
		}
		return legalOp;
	}

	@Override
	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	@Override
	public void addTransaction(Transaction transaction) {
		transactionList.add(transaction);
		
	}
}
