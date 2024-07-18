package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.AbstractOperations;
import pp.projects.model.Account;
import pp.projects.model.AccountImpl;
import pp.projects.model.Services;

class ServicesTest {
	private Services services;
	private Account account;
	
	@BeforeEach
	public void setUp() {
		account = new AccountImpl("Laura");
		services = new Services(account);		
	}
	
	@Test
	void testInitialization() {
		assertTrue(services.nome().equals(""));
		assertTrue(services instanceof AbstractOperations);
	}
	
	@Test
	public void testOperations() {
		//test del deposito di soldi dall'esterno verso il Conto
		assertTrue(services.deposit(100.65, "rimborso spese"));
		assertTrue(account.getBalance() == 100.65);
		
		assertTrue(services.withdraw(100, "cena fuori"));
		assertFalse(services.withdraw(20.0, "mancia"));
		assertTrue(account.getBalance() == 0.65);		
		
		assertFalse(services.withdraw(1.20, "Caffè"));
		assertTrue(account.getBalance() == 0.65);
	}

}

