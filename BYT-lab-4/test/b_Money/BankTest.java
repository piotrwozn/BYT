package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Alice");
        assertNotNull(SweBank.getBalance("Alice"));
	}

	@Test(expected = AccountExistsException.class)
	public void testOpenAccountDuplicate() throws AccountExistsException {
		SweBank.openAccount("Bob"); // This should throw an exception because the account already exists
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		Money depositAmount = new Money(1000, SEK);
		SweBank.deposit("Ulrika", depositAmount);
		assertEquals((Integer) 1000, SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		Money depositAmount = new Money(2000, SEK);
		SweBank.deposit("Ulrika", depositAmount);
		SweBank.withdraw("Ulrika", new Money(1000, SEK));
		assertEquals((Integer) 1000, SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals((Integer) 0, SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.transfer("Ulrika", DanskeBank, "Gertrud", new Money(1000, SEK));
		assertEquals((Integer) 0, SweBank.getBalance("Ulrika"));
		assertEquals((Integer) 750, DanskeBank.getBalance("Gertrud"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Ulrika", "payment1", 5, 0, new Money(100, SEK), Nordea, "Bob");
		for (int i = 0; i < 5; i++) {
			SweBank.tick();
		}
		assertEquals((Integer)(-200), SweBank.getBalance("Ulrika"));
	}
}
