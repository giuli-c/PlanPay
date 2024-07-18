package pp.projects.model;

import java.time.LocalDate;

public abstract class AbstractOperations {
	
	
    protected Account accountRef;

    public AbstractOperations(Account accountRef) {
        this.accountRef = accountRef;
    }

    /**Template Method:
     * richiede di effettuare un deposito ad Account tramite doWithDraw(amount)
     * se la richiesta è accettata aggiunge una nuova transazione alla lista dell'Account
     * @param amount
     * @param desc = causale
     * @return true se l'operazione va a buon fine, false se viene rifiutata
     */
    public boolean deposit(double amount, String desc) {
        boolean success = doDeposit(amount);
        if(success) {
        	accountRef.addTransaction(new TransactionImpl(LocalDate.now(), " Deposito "+ getTransactionType() + desc, amount));
        }
        return success;    
    }
    /**Template Method:
     * richiede di effettuare un prelievo ad Account tramite doWithDraw(amount)
     * se la richiesta è accettata aggiunge una nuova transazione alla lista dell'Account
     * @param amount
     * @param desc = causale
     * @return true se l'operazione va a buon fine, false se viene rifiutata
     */
    public boolean withdraw(double amount, String desc) {
        boolean success = doWithdraw(amount);
        if (success) {
        	accountRef.addTransaction(new TransactionImpl(LocalDate.now(), " Prelievo "+ getTransactionType() + desc, amount));
        }
        return success;
    }
    /**
     * Richiesta di deposito
     * @param amount
     * @return true se la richiesta di deposito viene accettata, false in caso contrario
     */
    protected abstract boolean doDeposit(double amount);
    /**
     * Richiesta di prelievo
     * @param amount
     * @return true se la richiesta di prelievo viene accettata, false in caso contrario
     */
    protected abstract boolean doWithdraw(double amount);
    /**
     * 
     * @return stringa di informazioni sul tipo di Operazione (Services o Objective) ed eventuale nome in caso di Objective
     */
    protected abstract String getTransactionType();
    public abstract String nome();
}