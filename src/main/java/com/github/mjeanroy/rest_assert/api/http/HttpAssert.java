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

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatus.shouldHaveStatus;
import static com.github.mjeanroy.rest_assert.internal.data.HttpStatus.BAD_REQUEST;
import static com.github.mjeanroy.rest_assert.internal.data.HttpStatus.INTERNAL_SERVER_ERROR;
import static com.github.mjeanroy.rest_assert.internal.data.HttpStatus.NOT_FOUND;
import static com.github.mjeanroy.rest_assert.internal.data.HttpStatus.OK;
import static com.github.mjeanroy.rest_assert.utils.Utils.firstNonNull;

import com.github.mjeanroy.rest_assert.error.AbstractError;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

public final class HttpAssert {

	private HttpAssert() {
	}

	public static void assertIsOk(HttpResponse response) {
		assertIsOk(null, response);
	}

	public static void assertIsOk(String message, HttpResponse response) {
		assertIsStatusEqual(message, OK.getStatus(), response.getStatus());
	}

	public static void assertIsBadRequest(HttpResponse response) {
		assertIsBadRequest(null, response);
	}

	public static void assertIsBadRequest(String message, HttpResponse response) {
		assertIsStatusEqual(message, BAD_REQUEST.getStatus(), response.getStatus());
	}

	public static void assertIsNotFound(HttpResponse response) {
		assertIsNotFound(null, response);
	}

	public static void assertIsNotFound(String message, HttpResponse response) {
		assertIsStatusEqual(message, NOT_FOUND.getStatus(), response.getStatus());
	}

	public static void assertIsInternalServerError(HttpResponse response) {
		assertIsInternalServerError(null, response);
	}

	public static void assertIsInternalServerError(String message, HttpResponse response) {
		assertIsStatusEqual(message, INTERNAL_SERVER_ERROR.getStatus(), response.getStatus());
	}

	private static void assertIsStatusEqual(String message, int status, int expectedStatus) {
		if (status != expectedStatus) {
			fail(message, shouldHaveStatus(status, expectedStatus));
		}
	}

	private static void fail(String message1, AbstractError message2) {
		throw new AssertionError(firstNonNull(message1, message2.toString()));
	}
}
