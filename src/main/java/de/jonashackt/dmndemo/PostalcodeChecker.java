package de.jonashackt.dmndemo;

public class PostalcodeChecker {

	public static boolean isValidPostalcode(String postalcode) {
		System.out.println("Logic called");
		return postalcode != null && postalcode.length() == 5 && Integer.valueOf(postalcode) > 1000 && Integer.valueOf(postalcode) < 99999;
	}
}
