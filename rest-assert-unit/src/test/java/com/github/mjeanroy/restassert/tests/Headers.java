package com.github.mjeanroy.restassert.tests;

import static com.github.mjeanroy.restassert.tests.models.Header.header;

import com.github.mjeanroy.restassert.tests.models.Header;

public final class Headers {

	private Headers() {
	}

	/**
	 * The {@code "Access-Control-Allow-Credentials"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_ALLOW_CREDENTIALS = header("Access-Control-Allow-Credentials", "true");

	/**
	 * The {@code "Access-Control-Allow-Methods"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_ALLOW_METHODS = header("Access-Control-Allow-Methods", "GET, POST");

	/**
	 * The {@code "Access-Control-Expose-Headers"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_EXPOSE_HEADERS = header("Access-Control-Expose-Headers", "Content-Length");

	/**
	 * The {@code "Access-Control-Allow-Max-Age"} header, to use in unit test.
	 */
	public static final Header ACCESS_CONTROL_ALLOW_MAX_AGE = header("Access-Control-Allow-Max-Age", "3600");

	/**
	 * The {@code "Strict-Transport-Security"} header, to use in unit test.
	 */
	public static final Header STRICT_TRANSPORT_SECURITY = header("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
}
