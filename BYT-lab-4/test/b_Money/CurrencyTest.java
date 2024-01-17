package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.001);
		assertEquals(0.20, DKK.getRate(), 0.001);
		assertEquals(1.5, EUR.getRate(), 0.001);
	}

	@Test
	public void testSetRate() {
		SEK.setRate(0.10);
		assertEquals(0.10, SEK.getRate(), 0.001);

		EUR.setRate(1.25);
		assertEquals(1.25, EUR.getRate(), 0.001);
	}

	@Test
	public void testUniversalValue() {
		assertEquals((Integer)1500, SEK.universalValue(10000));
		assertEquals((Integer)2000, DKK.universalValue(10000));
	}

	@Test
	public void testValueInThisCurrency() {
		assertEquals((Integer)10000, SEK.valueInThisCurrency(1000, EUR));
		assertEquals((Integer)100, EUR.valueInThisCurrency(1000, SEK));
	}

}
