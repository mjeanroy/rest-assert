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

import static com.github.mjeanroy.rest_assert.api.AssertUtil.check;

/**
 * Static assertions.
 * This class is generated.
 */
public final class HttpAssert {

	private static com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions assertions = com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions.instance();

	// Private constructor to ensure no instantiation
	private HttpAssert() {
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasCacheControl}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasCacheControl(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertHasCacheControl(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasCacheControl}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasCacheControl(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.hasCacheControl(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasContentLength}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasContentLength(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertHasContentLength(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasContentLength}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasContentLength(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.hasContentLength(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasContentType}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasContentType(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertHasContentType(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasContentType}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasContentType(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.hasContentType(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasETag}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasETag(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertHasETag(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasETag}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasETag(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.hasETag(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasHeader}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasHeader(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		assertHasHeader(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasHeader}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasHeader(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		check(message, assertions.hasHeader(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasLastModified}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasLastModified(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertHasLastModified(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasLastModified}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasLastModified(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.hasLastModified(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasLocation}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasLocation(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertHasLocation(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasLocation}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasLocation(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.hasLocation(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasMimeType}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasMimeType(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		assertHasMimeType(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasMimeType}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasMimeType(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		check(message, assertions.hasMimeType(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasMimeTypeIn}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertHasMimeTypeIn(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.util.Collection<java.lang.String> arg1) {
		assertHasMimeTypeIn(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#hasMimeTypeIn}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertHasMimeTypeIn(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.util.Collection<java.lang.String> arg1) {
		check(message, assertions.hasMimeTypeIn(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isAccepted}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsAccepted(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsAccepted(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isAccepted}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsAccepted(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isAccepted(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isBadRequest}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsBadRequest(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsBadRequest(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isBadRequest}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsBadRequest(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isBadRequest(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isClientError}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsClientError(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsClientError(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isClientError}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsClientError(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isClientError(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isConflict}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsConflict(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsConflict(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isConflict}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsConflict(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isConflict(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isContentTypeEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsContentTypeEqualTo(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		assertIsContentTypeEqualTo(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isContentTypeEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsContentTypeEqualTo(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		check(message, assertions.isContentTypeEqualTo(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isCreated}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsCreated(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsCreated(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isCreated}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsCreated(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isCreated(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isCss}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsCss(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsCss(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isCss}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsCss(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isCss(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isCsv}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsCsv(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsCsv(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isCsv}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsCsv(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isCsv(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isETagEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsETagEqualTo(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		assertIsETagEqualTo(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isETagEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsETagEqualTo(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		check(message, assertions.isETagEqualTo(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isForbidden}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsForbidden(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsForbidden(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isForbidden}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsForbidden(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isForbidden(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isHeaderEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsHeaderEqualTo(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1, java.lang.String arg2) {
		assertIsHeaderEqualTo(null, actual, arg1, arg2);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isHeaderEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsHeaderEqualTo(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1, java.lang.String arg2) {
		check(message, assertions.isHeaderEqualTo(actual, arg1, arg2));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isHtml}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsHtml(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsHtml(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isHtml}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsHtml(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isHtml(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isInternalServerError}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsInternalServerError(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsInternalServerError(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isInternalServerError}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsInternalServerError(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isInternalServerError(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isJavascript}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsJavascript(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsJavascript(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isJavascript}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsJavascript(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isJavascript(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isJson}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsJson(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsJson(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isJson}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsJson(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isJson(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isLocationEqualTo}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsLocationEqualTo(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		assertIsLocationEqualTo(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isLocationEqualTo}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsLocationEqualTo(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, java.lang.String arg1) {
		check(message, assertions.isLocationEqualTo(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isMethodNotAllowed}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsMethodNotAllowed(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsMethodNotAllowed(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isMethodNotAllowed}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsMethodNotAllowed(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isMethodNotAllowed(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNoContent}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsNoContent(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsNoContent(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNoContent}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsNoContent(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isNoContent(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNotFound}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsNotFound(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsNotFound(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNotFound}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsNotFound(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isNotFound(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNotImplemented}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsNotImplemented(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsNotImplemented(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNotImplemented}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsNotImplemented(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isNotImplemented(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNotModified}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsNotModified(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsNotModified(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNotModified}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsNotModified(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isNotModified(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNotSuccess}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsNotSuccess(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsNotSuccess(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isNotSuccess}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsNotSuccess(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isNotSuccess(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isOk}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsOk(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsOk(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isOk}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsOk(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isOk(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isPdf}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsPdf(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsPdf(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isPdf}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsPdf(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isPdf(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isPreConditionFailed}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsPreConditionFailed(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsPreConditionFailed(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isPreConditionFailed}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsPreConditionFailed(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isPreConditionFailed(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isRedirection}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsRedirection(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsRedirection(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isRedirection}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsRedirection(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isRedirection(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isServerError}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsServerError(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsServerError(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isServerError}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsServerError(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isServerError(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isStatusBetween}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsStatusBetween(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, int arg1, int arg2) {
		assertIsStatusBetween(null, actual, arg1, arg2);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isStatusBetween}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsStatusBetween(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, int arg1, int arg2) {
		check(message, assertions.isStatusBetween(actual, arg1, arg2));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isStatusEqual}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsStatusEqual(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, int arg1) {
		assertIsStatusEqual(null, actual, arg1);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isStatusEqual}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsStatusEqual(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, int arg1) {
		check(message, assertions.isStatusEqual(actual, arg1));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isStatusOutOf}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsStatusOutOf(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, int arg1, int arg2) {
		assertIsStatusOutOf(null, actual, arg1, arg2);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isStatusOutOf}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsStatusOutOf(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual, int arg1, int arg2) {
		check(message, assertions.isStatusOutOf(actual, arg1, arg2));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isSuccess}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsSuccess(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsSuccess(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isSuccess}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsSuccess(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isSuccess(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isText}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsText(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsText(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isText}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsText(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isText(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isUnauthorized}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsUnauthorized(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsUnauthorized(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isUnauthorized}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsUnauthorized(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isUnauthorized(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isUnsupportedMediaType}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsUnsupportedMediaType(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsUnsupportedMediaType(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isUnsupportedMediaType}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsUnsupportedMediaType(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isUnsupportedMediaType(actual));
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isXml}
	 * Throws an {@link AssertionError} with default message if test failed.
	 *
	 * @param actual Actual object.
	 */
	public static void assertIsXml(com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		assertIsXml(null, actual);
	}

	/**
	 * @see {@link com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions#isXml}
	 * Throws an {@link AssertionError} with given message if test failed.
	 *
	 * @param message  The identifying message for the {@link AssertionError}.
	 * @param actual Actual object.
	 */
	public static void assertIsXml(String message, com.github.mjeanroy.rest_assert.internal.data.HttpResponse actual) {
		check(message, assertions.isXml(actual));
	}

}

