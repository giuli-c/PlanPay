package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.controller.ConsoleController;
import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.controller.LoginControllerImpl;
import pp.projects.model.IllegalOperationException;
import pp.projects.model.OperationType;
import pp.projects.model.Transaction;

class TransactionTest {
	
	private ConsoleController controller;

	@BeforeEach
	void setUp() {
		try {
			controller = new ConsoleControllerImpl(new LoginControllerImpl());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testServiceTransaction() {
		//effettuo operazioni per creare le transazioni relative
		try {
			controller.updateConto(125.0, true, "vendita bici usata", OperationType.SERVIZIO);
			controller.updateConto(40.8, false, "Cena Fuori", OperationType.SERVIZIO);
			controller.updateConto(11.0, false, "Abbonamento Netflix", OperationType.SERVIZIO);
		}
		catch(IllegalOperationException e) {
			e.printStackTrace();
		}
		// Verifico che ci sia lo stesso numero di transazioni rispetto alle operazioni effettuate
		
		List<Transaction> trList = controller.getAllTransactions();
		assertTrue(trList.size() == 3);
		
		assertTrue(trList.get(0).getAmount() == 125.0);
		assertTrue(trList.get(1).getAmount() == 40.8);
		assertTrue(trList.get(2).getAmount() == 11.0);
		
		assertTrue(trList.get(0).getName().equals(" Deposito Servizio vendita bici usata"));
		assertTrue(trList.get(1).getName().equals(" Prelievo Servizio Cena Fuori"));
		
		assertFalse(trList.get(2).getName().equals(" Servizio Deposito Abbonamento Netflix"));
		assertTrue(trList.get(2).getName().equals(" Prelievo Servizio Abbonamento Netflix"));
		
		}
	
	@Test
	public void testObjectiveTransaction() {
		//effettuo operazioni su un obbiettivo per creare relative transazioni
		controller.saveObjective(true, "Friggitrice", "modello 9xT4", 99.99);
		try {
			controller.updateConto(1000.0, true, "Vendita vecchia auto", OperationType.SERVIZIO);
			controller.updateConto(20.01, true, "Friggitrice", OperationType.OBIETTIVO);
			controller.updateConto(70.0, true, "Friggitrice", OperationType.OBIETTIVO);
			controller.updateConto(9.98, true, "Friggitrice", OperationType.OBIETTIVO);
			controller.updateConto(99.99, false, "Friggitrice", OperationType.OBIETTIVO);
		} 
		catch (IllegalOperationException e) {
			e.printStackTrace();
		}
		//Verifico la presenza delle transazioni e le informazioni che contengono
		List<Transaction> trList = controller.getAllTransactions();
		assertTrue(trList.size() == 5);		
		assertTrue(trList.get(0).getAmount() == 1000.0);
		assertTrue(trList.get(1).getAmount() == 20.01);
		assertTrue(trList.get(2).getAmount() == 70.0);
		assertTrue(trList.get(3).getAmount() == 9.98);
		assertTrue(trList.get(4).getAmount() == 99.99);
		
		assertTrue(trList.get(0).getName().equals(" Deposito Servizio Vendita vecchia auto"));
		assertTrue(trList.get(1).getName().equals(" Deposito Obbiettivo 'Friggitrice'"));
		assertTrue(trList.get(2).getName().equals(" Deposito Obbiettivo 'Friggitrice'"));
		assertTrue(trList.get(3).getName().equals(" Deposito Obbiettivo 'Friggitrice'"));
		assertTrue(trList.get(4).getName().equals(" Prelievo Obbiettivo 'Friggitrice'"));
		
		// Verifico la data delle transazioni
		LocalDate actualDate = LocalDate.now();
		assertTrue(trList.get(0).getDate().equals(actualDate) &&
					trList.get(1).getDate().equals(actualDate) &&
					trList.get(2).getDate().equals(actualDate) &&
					trList.get(2).getDate().equals(actualDate) &&
					trList.get(3).getDate().equals(actualDate));
	}

}
