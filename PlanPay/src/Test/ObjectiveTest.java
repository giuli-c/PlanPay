package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.AbstractOperations;
import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.IllegalInputException;
import pp.projects.model.Objective;
import pp.projects.model.ObjectiveImpl;

class ObjectiveTest {
	
	private Objective objective1,
						  objective2;
	private Account account;
	private LocalDate actualDayMonth;

	@BeforeEach
	public void setUp() {
		account = new AccountImpl("Giovanni");
		objective1 = new ObjectiveImpl(account, "Auto", "Tesla", 30000.0);
		objective2 = new ObjectiveImpl(account, "Gaming", "Playstation5", 600.0);
		actualDayMonth = LocalDate.now();
	}
    @Test
    public void testInitialization() {
		assertTrue(objective1 instanceof AbstractOperations);
		assertTrue(objective2 instanceof AbstractOperations);
		assertTrue(objective1.getName().equals("Auto"));		
		assertTrue(objective2.getName().equals("Gaming"));		
		assertTrue(objective1.getDescription().equals("Tesla"));		
		assertTrue(objective2.getDescription().equals("Playstation5"));		
		assertTrue(objective1.getSavingTarget() == 30000.0);
		assertTrue(objective2.getSavingTarget() == 600.0);
		assertTrue(objective1.getBalance() == 0.0);
		assertFalse(objective1.isTargetMet());			
		assertFalse(objective2.isTargetMet());
		assertTrue(objective1.getDate().equals(actualDayMonth));	
		assertTrue(objective2.getDate().equals(actualDayMonth));
    }
    
	@Test
	public void testSetters() {
		objective1.setDescription("Auto Elettrica");
		assertTrue(objective1.getDescription().equals("Auto Elettrica"));		
		objective2.setName("Next-gen Console");
		assertTrue(objective2.getName().equals("Next-gen Console"));
		assertFalse(objective2.isTargetMet());
		objective2.setSavingTarget(0.0);
		assertTrue(objective2.getBalance() == 0.0);
		assertTrue(objective2.getSavingTarget() == 0.0);
		assertTrue(objective2.isTargetMet());
	}
	
	@Test
	public void testEquals() {
		assertFalse(objective1.equals(objective2));
	}
	
	@Test
	public void testOperations() {
		account.addBalance(280.22);
		assertTrue(objective1.deposit(280.22, "risparmi mese"));
		assertTrue(objective1.getBalance() == 280.22);
		assertFalse(objective1.withdraw(50.0, "tentativo di prelievo1"));
		assertTrue(account.getBalance() == 0.0);
		
		account.addBalance(700);
		assertTrue(objective2.deposit(700.0, "vendita vecchie console e giochi"));
		assertTrue(objective2.isTargetMet());
		assertTrue(objective2.withdraw(700.0, "Compro PS5 e giochi"));
		assertTrue(objective2.getBalance() == 0.0);
		assertTrue(account.getBalance() == 700.0);
		
		// Verifico che la possibilità di prelevare non rimanga tale 
		//se il saldo torna inferiore alla soglia risparmio
		account.addBalance(700);
		assertTrue(objective2.deposit(700, "vendita Ps5"));
		assertTrue(objective2.isTargetMet());
		assertTrue(objective2.withdraw(450.0, "Compro altro modello Ps5"));
		assertFalse(objective2.isTargetMet());
		objective2.setSavingTarget(250.0);
		assertTrue(objective2.isTargetMet());
		assertTrue(objective2.withdraw(250.0, "Ritiro la differenza"));
	}

	@Test
	public void testSavingForecast() {
		assertThrows(IllegalInputException.class, 
				() -> objective2.savingForecast(0, 0, 0, 0, false));		
		assertThrows(IllegalInputException.class, 
				() -> objective2.savingForecast(-1000, 0, 0, 0, false));
		assertThrows(IllegalInputException.class, 
				() -> objective2.savingForecast(1000, -1, 0, 0, false));
		assertThrows(IllegalInputException.class, 
				() -> objective2.savingForecast(1000, 1, -1, 1, false));
		assertThrows(IllegalInputException.class, 
				() -> objective2.savingForecast(1000, 6, 1, -5, false));
		
		try {
			assertTrue(objective2.savingForecast(1000, 6, 1, 0, false) == 500.0);
		} catch (IllegalInputException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete() {
		account.addBalance(40000.0);
		objective1.deposit(30000.0, "vendita Tesla");
		objective2.deposit(700, "vendita Ps5");
		assertTrue(account.getBalance() == 9300.0);
		objective2.reset();
		assertTrue(account.getBalance() == 10000.0);
		assertTrue(account.getTransactionList().size() == 3 &&
					account.getTransactionList().get(2).getName().equals(" Prelievo Obbiettivo 'Gaming' cancellato"));
		objective2.reset();
		assertTrue(account.getBalance() == 10000.0);
		assertTrue(account.getTransactionList().size() == 3);
		objective1.reset();
		assertTrue(account.getBalance() == 40000.0);
		assertTrue(account.getTransactionList().size() == 4);
	}
}
