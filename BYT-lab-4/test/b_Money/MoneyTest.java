package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals((Integer) 10000, SEK100.getAmount());
		assertEquals((Integer)1000, EUR10.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("10000 SEK", SEK100.toString());
		assertEquals("1000 EUR", EUR10.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals((Integer)1500, SEK100.universalValue());
		assertEquals((Integer)1500, EUR10.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(new Money(10000, SEK)));
		assertTrue(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		Money sum = SEK100.add(EUR10);
		assertEquals((Integer)20000, sum.getAmount());
		assertEquals(SEK, sum.getCurrency());
	}

	@Test
	public void testSub() {
		Money difference = SEK200.sub(EUR10);
		assertEquals((Integer)10000, difference.getAmount());
		assertEquals(SEK, difference.getCurrency());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(SEK100.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals((Integer)(-10000), SEKn100.getAmount());
		assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(new Money(10000, SEK)));
		assertFalse(SEK100.compareTo(EUR10) > 0);
	}
}
