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

package com.github.mjeanroy.rest_assert.internal.assertions;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatus.shouldHaveStatus;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.internal.data.HttpStatus;

/**
 * Reusable Assertions of http response.
 * This class is implemented as a singleton object.
 * This class is thread safe.
 */
public final class HttpResponseAssertions {

	/**
	 * Singleton object.
	 */
	private static HttpResponseAssertions INSTANCE = new HttpResponseAssertions();

	/**
	 * Get singleton object.
	 *
	 * @return Singleton object.
	 */
	public static HttpResponseAssertions instance() {
		return INSTANCE;
	}

	// Private constructor to ensure singleton
	private HttpResponseAssertions() {
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#OK}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is OK, false otherwise.
	 */
	public AssertionResult isOk(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.OK.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#CREATED}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is CREATED, false otherwise.
	 */
	public AssertionResult isCreated(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.CREATED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#ACCEPTED}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is ACCEPTED, false otherwise.
	 */
	public AssertionResult isAccepted(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.ACCEPTED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#NO_CONTENT}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is NO CONTENT, false otherwise.
	 */
	public AssertionResult isNoContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.NO_CONTENT.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#BAD_REQUEST}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is BAD REQUEST, false otherwise.
	 */
	public AssertionResult isBadRequest(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.BAD_REQUEST.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#NOT_FOUND}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is NOT FOUND, false otherwise.
	 */
	public AssertionResult isNotFound(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.NOT_FOUND.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is INTERNAL SERVER ERROR, false otherwise.
	 */
	public AssertionResult isInternalServerError(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.INTERNAL_SERVER_ERROR.getStatus());
	}

	/**
	 * Check that status code of http response has an expected status.
	 *
	 * @param httpResponse Http response.
	 * @param status Expected status.
	 * @return True if http response has expected status, false otherwise.
	 */
	public AssertionResult isStatusEqual(HttpResponse httpResponse, int status) {
		return httpResponse.getStatus() == status ?
				success() :
				failure(shouldHaveStatus(httpResponse.getStatus(), status));
	}
}
