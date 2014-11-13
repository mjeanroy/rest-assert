/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.rest_assert.api.http;

import static com.github.mjeanroy.rest_assert.utils.Utils.firstNonNull;

import com.github.mjeanroy.rest_assert.error.RestAssertError;
import com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult;
import com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

/**
 * Static assertions.
 */
public final class HttpAssert {

	private static HttpResponseAssertions assertions = HttpResponseAssertions.instance();

	// Private constructor to ensure no instantiation
	private HttpAssert() {
	}

	/**
	 * Asserts that an status code of http response is "OK" (a.k.a 200).
	 * If it isn't it throws an {@link AssertionError} with default message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsOk(HttpResponse response) {
		assertIsOk(null, response);
	}

	/**
	 * Asserts that an status code of http response is "OK" (a.k.a 200).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsOk(String message, HttpResponse response) {
		check(message, assertions.isOk(response));
	}

	/**
	 * Asserts that an status code of http response is "OK" (a.k.a 200).
	 * If it isn't it throws an {@link AssertionError} with default message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsCreated(HttpResponse response) {
		assertIsCreated(null, response);
	}

	/**
	 * Asserts that an status code of http response is "CREATED" (a.k.a 201).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsCreated(String message, HttpResponse response) {
		check(message, assertions.isCreated(response));
	}

	/**
	 * Asserts that an status code of http response is "OK" (a.k.a 200).
	 * If it isn't it throws an {@link AssertionError} with default message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsAccepted(HttpResponse response) {
		assertIsAccepted(null, response);
	}

	/**
	 * Asserts that an status code of http response is "ACCEPTED" (a.k.a 202).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsAccepted(String message, HttpResponse response) {
		check(message, assertions.isAccepted(response));
	}

	/**
	 * Asserts that an status code of http response is "OK" (a.k.a 200).
	 * If it isn't it throws an {@link AssertionError} with default message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsNoContent(HttpResponse response) {
		assertIsNoContent(null, response);
	}

	/**
	 * Asserts that an status code of http response is "NO CONTENT" (a.k.a 204).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsNoContent(String message, HttpResponse response) {
		check(message, assertions.isNoContent(response));
	}

	/**
	 * Asserts that an status code of http response is "NOT MODIFIED" (a.k.a 304).
	 * If it isn't it throws an {@link AssertionError} with default message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsNotModified(HttpResponse response) {
		assertIsNotModified(null, response);
	}

	/**
	 * Asserts that an status code of http response is "UNAUTHORIZED" (a.k.a 401).
	 * If it isn't it throws an {@link AssertionError} with default message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsUnauthorized(HttpResponse response) {
		assertIsUnauthorized(null, response);
	}

	/**
	 * Asserts that an status code of http response is "UNAUTHORIZED" (a.k.a 401).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsUnauthorized(String message, HttpResponse response) {
		check(message, assertions.isUnautorized(response));
	}

	/**
	 * Asserts that an status code of http response is "NOT MODIFIED" (a.k.a 304).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsNotModified(String message, HttpResponse response) {
		check(message, assertions.isNotModified(response));
	}

	/**
	 * Asserts that an status code of http response is "BAD REQUEST" (a.k.a 400).
	 * If it isn't it throws an {@link AssertionError} with default message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsBadRequest(HttpResponse response) {
		assertIsBadRequest(null, response);
	}

	/**
	 * Asserts that an status code of http response is "BAD REQUEST" (a.k.a 400).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsBadRequest(String message, HttpResponse response) {
		check(message, assertions.isBadRequest(response));
	}

	/**
	 * Asserts that an status code of http response is "NOT FOUND" (a.k.a 404).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsNotFound(HttpResponse response) {
		assertIsNotFound(null, response);
	}

	/**
	 * Asserts that an status code of http response is "NOT FOUND" (a.k.a 404).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsNotFound(String message, HttpResponse response) {
		check(message, assertions.isNotFound(response));
	}

	/**
	 * Asserts that an status code of http response is "INTERNAL SERVER ERROR"
	 * (a.k.a 500).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param response Http response to check.
	 */
	public static void assertIsInternalServerError(HttpResponse response) {
		assertIsInternalServerError(null, response);
	}

	/**
	 * Asserts that an status code of http response is "INTERNAL SERVER ERROR"
	 * (a.k.a 500).
	 * If it isn't it throws an {@link AssertionError} with given message.
	 *
	 * @param message The identifying message for the {@link AssertionError}.
	 * @param response Http response to check.
	 */
	public static void assertIsInternalServerError(String message, HttpResponse response) {
		check(message, assertions.isInternalServerError(response));
	}

	private static void check(String message, AssertionResult result) {
		if (result.isFailure()) {
			fail(message, result.getError());
		}
	}

	private static void fail(String message1, RestAssertError message2) {
		throw new AssertionError(firstNonNull(message1, message2.buildMessage()));
	}
}
