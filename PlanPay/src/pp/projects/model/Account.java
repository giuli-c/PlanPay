package pp.projects.model;

import java.util.List;

public interface Account {
	/**
	 * 
	 * @return nome dell'Account
	 */
	public String getName();
	/**
	 * 
	 * @return balance = il saldo attuale dell'Account
	 */
	public double getBalance();
	/**
	 * 
	 * @return transactionList = la lista di transazioni relative all'Account
	 */
	public List<Transaction> getTransactionList();
	/**
	 * 
	 * @param transaction = la transazione da inserire
	 */
	public void addTransaction(Transaction transaction);
	/**
	 * Incrementa il saldo dell'account di un ammontare
	 * @param amount = la somma da aggiungere al saldo
	 */
	public void addBalance(double amount);
	/**
	 * Sottrae al saldo dell'account una somma solo se il saldo attuale è sufficiente
	 * 
	 * @param amount = la somma da sottrarre
	 * @return true se l'operazione è accettata, false in caso contrario
	 */
	public boolean subBalance(double amount);
}
