package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		// Set up a timed payment
		Money amount = new Money(1000, SEK);
		testAccount.addTimedPayment("payment1", 10, 5, amount, SweBank, "Alice");

		// Check if the timed payment exists
		assertTrue(testAccount.timedPaymentExists("payment1"));

		// Remove the timed payment and check again
		testAccount.removeTimedPayment("payment1");
		assertFalse(testAccount.timedPaymentExists("payment1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		Money amount = new Money(5000, SEK);
		testAccount.addTimedPayment("payment2", 10, 0, amount, SweBank, "Alice");

		// Simulate a tick
		testAccount.tick();

		// Check if balance is reduced by the payment amount
		assertEquals((Integer) 9995000, testAccount.getBalance().getAmount());
	}

	@Test
	public void testAddWithdraw() {
		Money depositAmount = new Money(5000, SEK);
		testAccount.deposit(depositAmount);

		// Check if balance increased after deposit
		assertEquals((Integer) 10005000, testAccount.getBalance().getAmount());

		Money withdrawAmount = new Money(2000, SEK);
		testAccount.withdraw(withdrawAmount);

		// Check if balance decreased after withdrawal
		assertEquals((Integer) 10003000, testAccount.getBalance().getAmount());
	}

	@Test
	public void testGetBalance() {
		// Check if the initial balance is as expected
		assertEquals((Integer) 10000000, testAccount.getBalance().getAmount());
	}
}
