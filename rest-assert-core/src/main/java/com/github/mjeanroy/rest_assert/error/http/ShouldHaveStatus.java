package com.github.mjeanroy.rest_assert.error.http;

import com.github.mjeanroy.rest_assert.error.AbstractError;

public class ShouldHaveStatus extends AbstractError {

	private final int status;

	private ShouldHaveStatus(int status, String message, Object... args) {
		super(message, args);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public static ShouldHaveStatus shouldHaveStatus(int status, int expectedStatus) {
		return new ShouldHaveStatus(status, "Expecting status code to be %s but was %s", status, expectedStatus);
	}
}
