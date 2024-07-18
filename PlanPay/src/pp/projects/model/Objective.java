package pp.projects.model;

public interface Objective extends Data {
	/**
	 * 
	 * @param targetAmount = la cifra da raggiungere
	 * @param frequency = la frequenza mensile di versamento dei risparmi
	 * @param years = numero  di anni per raggiungere la cifra targetAmount
	 * @param months = numero di mesi per raggiungere la cifra targetAmount
	 * @param isBalanceAccounted indica se si deve tenere conto della somma già depositata nell'obbiettivo
	 * @return la somma da versare con frequenza = frequency, per raggiungere la cifra targetAmount
	 * @throws IllegalInputException 
	 */
	public double savingForecast(double targetAmount, double frequency, int years, int months, boolean isBalanceAccounted) throws IllegalInputException;
	/**
	 * 
	 * @return true se il risparmio è uguale o superiore al valore fissato come obbiettivo,
	 *  altrimenti false
	 */
	public boolean isTargetMet();
	/**
	 * 
	 * @return balance = il bilancio attuale dell'Obbiettivo
	 */
	public double getBalance();
	/**
	 * 
	 * @return savingTarget = la soglia di risparmio dell'obbiettivo
	 */
	public double getSavingTarget();
	/**
	 * modifica la soglia di risparmio dell'obbiettivo
	 * @param newTarget
	 */
	public void setSavingTarget(double newTarget);	
	/**
	 * se il saldo dell'obbiettivo non è nullo, resetta la soglia di risparmio e restituisce all'Account
	 * tutto l'ammontare versato
	 */
	public void reset();
    /**
     * Richiede di effettuare un deposito ad Account tramite doWithDraw(amount)
     * Se la richiesta è accettata aggiunge una nuova transazione alla lista dell'Account
     * @param amount
     * @param desc = causale
     * @return true se l'operazione va a buon fine, false se viene rifiutata
     */
    public boolean deposit(double amount, String desc);
    /**
     * Richiede di effettuare un prelievo ad Account tramite doWithDraw(amount)
     * Se la richiesta è accettata aggiunge una nuova transazione alla lista dell'Account
     * @param amount
     * @param desc = causale
     * @return true se l'operazione va a buon fine, false se viene rifiutata
     */
    public boolean withdraw(double amount, String desc);
}

