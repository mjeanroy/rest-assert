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

package com.github.mjeanroy.rest_assert.assertj.internal;

import org.assertj.core.api.AssertionInfo;

import com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

/**
 * Reusable assertions of http response.
 * This class is implemented as a singleton.
 * This class is thread safe.
 */
public class HttpResponses extends AbstractRestAssertions {

	/**
	 * Singleton instance.
	 */
	private static final HttpResponses INSTANCE = new HttpResponses();

	/**
	 * Returns the singleton instance of this class.
	 */
	public static HttpResponses instance() {
		return INSTANCE;
	}

	/**
	 * Original assertions object, retrieved from core module.
	 */
	private HttpResponseAssertions assertions = HttpResponseAssertions.instance();

	// Private constructor to ensure class is a singleton
	private HttpResponses() {
	}

	/**
	 * Asserts that http response status code is OK (200).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 200.
	 */
	public void assertIsOk(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isOk(actual));
	}

	/**
	 * Asserts that http response status code is OK (201).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 201.
	 */
	public void assertIsCreated(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isCreated(actual));
	}

	/**
	 * Asserts that http response status code is ACCEPTED (202).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 201.
	 */
	public void assertIsAccepted(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isAccepted(actual));
	}

	/**
	 * Asserts that http response status code is NO CONTENT (204).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 204.
	 */
	public void assertIsNoContent(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isNoContent(actual));
	}

	/**
	 * Asserts that http response status code is BAD REQUEST (400).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 400.
	 */
	public void assertIsBadRequest(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isBadRequest(actual));
	}

	/**
	 * Asserts that http response status code is NOT FOUND (404).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 404.
	 */
	public void assertIsNotFound(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isNotFound(actual));
	}

	/**
	 * Asserts that http response status code is NOT MODIFIED (304).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 304.
	 */
	public void assertIsNotModified(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isNotModified(actual));
	}

	/**
	 * Asserts that http response status code is UNAUTHORIZED (401).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 401.
	 */
	public void assertIsUnauthorized(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isUnauthorized(actual));
	}

	/**
	 * Asserts that http response status code is FORBIDDEN (403).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 403.
	 */
	public void assertIsForbidden(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isForbidden(actual));
	}

	/**
	 * Asserts that http response status code is PRE CONDITION FAILED (412).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 412.
	 */
	public void assertIsPreConditionFailed(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isPreConditionFailed(actual));
	}

	/**
	 * Asserts that http response status code is METHOD NOT ALLOWED (405).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 405.
	 */
	public void assertIsMethodNotAllowed(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isMethodNotAllowed(actual));
	}

	/**
	 * Asserts that http response status code is CONFLICT (409).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 409.
	 */
	public void assertIsConflict(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isConflict(actual));
	}

	/**
	 * Asserts that http response status code is UNSUPPORTED MEDIA TYPE (415).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 415.
	 */
	public void assertIsUnsupportedMediaType(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isUnsupportedMediaType(actual));
	}

	/**
	 * Asserts that http response status code is NOT IMPLEMENTED (401).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 401.
	 */
	public void assertIsNotImplemented(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isNotImplemented(actual));
	}

	/**
	 * Asserts that http response status code is INTERNAL SERVER ERROR (500).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not 500.
	 */
	public void assertIsInternalServerError(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isInternalServerError(actual));
	}

	/**
	 * Asserts that http response status code is a success code (i.e between
	 * 200 and 299, inclusive).
	 *
	 * @param info contains information about the assertion.
	 * @param actual the actual http response.
	 * @throws AssertionError if the actual value is {@code null}.
	 *                        if the status code of the actual http response is
	 *                        not between 200 and 299.
	 */
	public void assertIsSuccess(AssertionInfo info, HttpResponse actual) {
		assertNotNull(info, actual);
		check(info, assertions.isSuccess(actual));
	}
}
