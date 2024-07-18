package pp.projects.model;


public class Services extends AbstractOperations {
	
	

	public Services(Account account) {
		super(account);
	}
	/**
	 * Effettua richiesta di deposito al saldo di Account
	 * @param amount
     * @return true se la richiesta di deposito viene accettata, false in caso contrario
	 */
	@Override
	protected boolean doDeposit(double amount) {
		accountRef.addBalance(amount);
		return true;
	}
	/**
	 * Effettua richiesta di prelievo al saldo di Account
	 * @param amount
     * @return true se la richiesta di prelievo viene accettata, false in caso contrario
	 */
	@Override
	protected boolean doWithdraw(double amount) {
		return accountRef.subBalance(amount);
	}

	@Override
	protected String getTransactionType() {
		return "Servizio ";
	}

	@Override
	public String nome() {
		return "";
	}

}