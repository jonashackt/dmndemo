package de.jonashackt.dmndemo;

import org.junit.Test;
import static org.junit.Assert.*;

public class PostalcodeCheckerTest {

	@Test
	public void testPostalCode() {
		// Given
		String postalcode = "01000"; // wrong
		// When -> Then
		assertFalse(PostalcodeChecker.isValidPostalcode(postalcode));
		// Given
		postalcode = "46739"; // correct
		// When -> Then
		assertTrue(PostalcodeChecker.isValidPostalcode(postalcode));
		// Given
		postalcode = "99999"; // wrong
		// When -> Then
		assertFalse(PostalcodeChecker.isValidPostalcode(postalcode));
	}
}
