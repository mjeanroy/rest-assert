package com.github.mjeanroy.rest_assert.generator.utils;

/**
 * Static utilities used by generator.
 */
public final class GeneratorUtils {

	// Ensure non intantiation
	private GeneratorUtils() {
	}

	/**
	 * Generate assertion method name.
	 * This will build a method name such as:
	 *   isOk ==> assertIsOk
	 *
	 * @param methodName Method name.
	 * @return Assertion method name.
	 */
	public static String generateAssertMethodName(String methodName) {
		return "assert" + capitalize(methodName);
	}

	/**
	 * Capitalize string.
	 *
	 * @param value Value to capitalize.
	 * @return Capitalized string.
	 */
	public static String capitalize(String value) {
		return Character.toUpperCase(value.charAt(0)) + value.substring(1);
	}
}