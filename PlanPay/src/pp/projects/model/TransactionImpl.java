package pp.projects.model;

import java.time.LocalDate;

public class TransactionImpl implements Transaction {
	// Definizione dei campi
	private LocalDate date;
    private String name;
    private double amount;
    
	// Costruttore per inizializzare la transazione
	public TransactionImpl(LocalDate date, String name, double amount) {
		this.date = date;
        this.name = name;
        this.amount = amount;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public String getDescription() {
		String segno = "";
    	String nameT = "";
    	
    	if(this.name.toLowerCase().contains("obbiettivo")) {
    		if(this.name.toLowerCase().contains("deposito")) {
    			segno = "-";
    			nameT = this.name.replaceFirst("Deposito", "");
    		} else if(this.name.toLowerCase().contains("prelievo")) {
    			segno = "+";
    			nameT = this.name.replaceFirst("Prelievo", "");
    		}
    	} else if(this.name.toLowerCase().contains("servizio")) {
    		if(this.name.toLowerCase().contains("deposito")) {
    			segno = "+";
    			nameT = this.name.replaceFirst("Deposito", "");
    		} else if(this.name.toLowerCase().contains("prelievo")) {
    			segno = "-";
    			nameT = this.name.replaceFirst("Prelievo", "");
    		}
    	}
    	return String.format(
    			"<html><table width='100%%'><tr>" +
    			"<td style='width: 26px;'><font color='%s' size='6'>&nbsp;%s&nbsp;</font></td>" +
    			"<td>%s&nbsp;&nbsp;</td>" +
    			"<td style='width: 260px;'>%s&nbsp;&nbsp;</td>" +
    			"<td align='right' style='width: 100px;'>&nbsp;&nbsp;%s €</td>" +
    			"</tr></table></html>",
    			segno.equals("+") ? "green" : "red", segno, this.date, nameT, amount
    	);
	}

	@Override
	public void setDescription(String d) {
	}

	@Override
	public LocalDate getDate() {
		return this.date;
	}
	
	@Override
	public double getAmount() {
		return this.amount;
	}
}
