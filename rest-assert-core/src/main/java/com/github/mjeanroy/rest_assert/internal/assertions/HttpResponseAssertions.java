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
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatusBetween.shouldHaveStatusBetween;
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
	 * Check that status code of http response is {@link HttpStatus#NOT_MODIFIED}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is NOT MODIFIED, false otherwise.
	 */
	public AssertionResult isNotModified(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.NOT_MODIFIED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#UNAUTHORIZED}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is UNAUTHORIZED, false otherwise.
	 */
	public AssertionResult isUnauthorized(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.UNAUTHORIZED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#FORBIDDEN}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is FORBIDDEN, false otherwise.
	 */
	public AssertionResult isForbidden(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.FORBIDDEN.getStatus());
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
	 * Check that status code of http response is {@link HttpStatus#PRE_CONDITION_FAILED}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is PRE CONDITION FAILED, false otherwise.
	 */
	public AssertionResult isPreConditionFailed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.PRE_CONDITION_FAILED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#METHOD_NOT_ALLOWED}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is METHOD NOT ALLOWED, false otherwise.
	 */
	public AssertionResult isMethodNotAllowed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.METHOD_NOT_ALLOWED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#CONFLICT}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is CONFLICT, false otherwise.
	 */
	public AssertionResult isConflict(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.CONFLICT.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#UNSUPPORTED_MEDIA_TYPE}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is UNSUPPORTED MEDIA TYPE, false otherwise.
	 */
	public AssertionResult isUnsupportedMediaType(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#NOT_IMPLEMENTED}.
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is NOT IMPLEMENTED, false otherwise.
	 */
	public AssertionResult isNotImplemented(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.NOT_IMPLEMENTED.getStatus());
	}

	/**
	 * Check that status code of http response is a success status (i.e
	 * between 200 and 299).
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is a success, false otherwise.
	 */
	public AssertionResult isSuccess(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 200, 299);
	}

	/**
	 * Check that status code of http response is a redirection status (i.e
	 * between 300 and 399).
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is a redirection, false otherwise.
	 */
	public AssertionResult isRedirection(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 300, 399);
	}

	/**
	 * Check that status code of http response is a client error status (i.e
	 * between 400 and 499).
	 *
	 * @param httpResponse Http response.
	 * @return True if http response is a client error, false otherwise.
	 */
	public AssertionResult isClientError(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 400, 499);
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

	/**
	 * Check that status code of http response is include between
	 * a lower bound and an upper bound (inclusive).
	 *
	 * @param httpResponse Http response.
	 * @param start Lower bound.
	 * @param end Upper bound.
	 * @return True if http response is included in bounds, false otherwise.
	 */
	public AssertionResult isStatusBetween(HttpResponse httpResponse, int start, int end) {
		int actualStatus = httpResponse.getStatus();
		return actualStatus >= start && actualStatus <= end ?
				success() :
				failure(shouldHaveStatusBetween(actualStatus, start, end));
	}
}
