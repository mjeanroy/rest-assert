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

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.internal.data.HttpStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveHeader.shouldHaveHeader;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveMimeType.shouldHaveMimeType;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatus.shouldHaveStatus;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatusBetween.shouldHaveStatusBetween;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.CONTENT_TYPE;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.ETAG;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.LOCATION;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.APPLICATION_JAVASCRIPT;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.APPLICATION_XML;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.CSS;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.CSV;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.JSON;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.PDF;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.TEXT_HTML;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.TEXT_JAVASCRIPT;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.TEXT_PLAIN;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.TEXT_XML;
import static com.github.mjeanroy.rest_assert.internal.data.MimeType.XHTML;
import static com.github.mjeanroy.rest_assert.utils.LowercaseMapper.lowercaseMapper;
import static com.github.mjeanroy.rest_assert.utils.Utils.map;
import static java.util.Arrays.asList;

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
	 * @return Assertion result.
	 */
	public AssertionResult isOk(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.OK.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#CREATED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isCreated(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.CREATED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#ACCEPTED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isAccepted(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.ACCEPTED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#NO_CONTENT}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNoContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.NO_CONTENT.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#NOT_MODIFIED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotModified(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.NOT_MODIFIED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#UNAUTHORIZED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isUnauthorized(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.UNAUTHORIZED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#FORBIDDEN}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isForbidden(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.FORBIDDEN.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#BAD_REQUEST}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isBadRequest(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.BAD_REQUEST.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#NOT_FOUND}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotFound(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.NOT_FOUND.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isInternalServerError(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.INTERNAL_SERVER_ERROR.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#PRE_CONDITION_FAILED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isPreConditionFailed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.PRE_CONDITION_FAILED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#METHOD_NOT_ALLOWED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isMethodNotAllowed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.METHOD_NOT_ALLOWED.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#CONFLICT}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isConflict(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.CONFLICT.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#UNSUPPORTED_MEDIA_TYPE}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isUnsupportedMediaType(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE.getStatus());
	}

	/**
	 * Check that status code of http response is {@link HttpStatus#NOT_IMPLEMENTED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotImplemented(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatus.NOT_IMPLEMENTED.getStatus());
	}

	/**
	 * Check that status code of http response is a success status (i.e
	 * between 200 and 299).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isSuccess(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 200, 299);
	}

	/**
	 * Check that status code of http response is a redirection status (i.e
	 * between 300 and 399).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isRedirection(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 300, 399);
	}

	/**
	 * Check that status code of http response is a server error status (i.e
	 * between 500 and 599).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isServerError(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 500, 599);
	}

	/**
	 * Check that status code of http response is a client error status (i.e
	 * between 400 and 499).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isClientError(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 400, 499);
	}

	/**
	 * Check that http response contains ETag header.
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasETag(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ETAG);
	}

	/**
	 * Check that http response contains ETag header with
	 * expected value.
	 * @param httpResponse Http response.
	 * @param etagValue ETag value.
	 * @return Assertion result.
	 */
	public AssertionResult isETagEqualTo(HttpResponse httpResponse, String etagValue) {
		return isHeaderEqualTo(httpResponse, ETAG, etagValue);
	}

	/**
	 * Check that http response contains Content-Type header.
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentType(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_TYPE);
	}

	/**
	 * Check that http response contains Content-Type header with
	 * expected value.
	 * @param httpResponse Http response.
	 * @param contentTypeValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentTypeEqualTo(HttpResponse httpResponse, String contentTypeValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_TYPE, contentTypeValue);
	}

	/**
	 * Check that http response contains Content-Length header.
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentLength(HttpResponse httpResponse) {
		return hasHeader(httpResponse, "Content-Length");
	}

	/**
	 * Check that http response contains Location header.
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasLocation(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LOCATION);
	}

	/**
	 * Check that http response contains Location header with
	 * expected value.
	 * @param httpResponse Http response.
	 * @param locationValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLocationEqualTo(HttpResponse httpResponse, String locationValue) {
		return isHeaderEqualTo(httpResponse, LOCATION, locationValue);
	}

	/**
	 * Check that http response contains Last-Modified header.
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasLastModified(HttpResponse httpResponse) {
		return hasHeader(httpResponse, "Last-Modified");
	}

	/**
	 * Check that http response contains Cache-Control header.
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasCacheControl(HttpResponse httpResponse) {
		return hasHeader(httpResponse, "Cache-Control");
	}

	/**
	 * Check that http response is "application/json".
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isJson(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, JSON);
	}

	/**
	 * Check that http response is "application/xml" or "text/xml".
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isXml(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(APPLICATION_XML, TEXT_XML));
	}

	/**
	 * Check that http response is "text/css".
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isCss(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, CSS);
	}

	/**
	 * Check that http response is "text/plain".
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isText(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, TEXT_PLAIN);
	}

	/**
	 * Check that http response is "text/csv".
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isCsv(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, CSV);
	}

	/**
	 * Check that http response is "application/pdf".
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isPdf(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, PDF);
	}

	/**
	 * Check that http response is "text/html" or "application/xhtml+xml".
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isHtml(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(TEXT_HTML, XHTML));
	}

	/**
	 * Check that http response is "application/javascript" or "text/javascript".
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isJavascript(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(APPLICATION_JAVASCRIPT, TEXT_JAVASCRIPT));
	}

	/**
	 * Check that http response contains expected header.
	 * @param httpResponse Http response.
	 * @param headerName Header name.
	 * @return Assertion result.
	 */
	public AssertionResult hasHeader(HttpResponse httpResponse, String headerName) {
		return httpResponse.hasHeader(headerName) ?
				success() :
				failure(shouldHaveHeader(headerName));
	}

	/**
	 * Check that http response is expected mime type.
	 * @param httpResponse Http response.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 */
	public AssertionResult hasMimeType(HttpResponse httpResponse, String expectedMimeType) {
		AssertionResult result = hasHeader(httpResponse, CONTENT_TYPE);
		if (result.isFailure()) {
			return result;
		}

		String contentType = httpResponse.getHeader(CONTENT_TYPE);
		String actualMimeType = contentType.split(";")[0].trim().toLowerCase();
		return actualMimeType.equals(expectedMimeType) ?
				success() :
				failure(shouldHaveMimeType(expectedMimeType, actualMimeType));
	}

	/**
	 * Check that http response is expected mime type.
	 * @param httpResponse Http response.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 */
	public AssertionResult hasMimeTypeIn(HttpResponse httpResponse, Collection<String> expectedMimeType) {
		AssertionResult result = hasHeader(httpResponse, CONTENT_TYPE);
		if (result.isFailure()) {
			return result;
		}

		String contentType = httpResponse.getHeader(CONTENT_TYPE);
		String actualMimeType = contentType.split(";")[0].trim().toLowerCase();

		List<String> expected = new ArrayList<>(expectedMimeType);
		List<String> list = map(expected, lowercaseMapper());

		return list.contains(actualMimeType) ?
				success() :
				failure(shouldHaveMimeType(expected, actualMimeType));
	}

	/**
	 * Check that http response contains expected header with
	 * expected value.
	 * @param httpResponse Http response.
	 * @param headerName Header name.
	 * @param headerValue Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isHeaderEqualTo(HttpResponse httpResponse, String headerName, String headerValue) {
		AssertionResult result = hasHeader(httpResponse, headerName);
		if (result.isFailure()) {
			return result;
		}

		String actualValue = httpResponse.getHeader(headerName);
		return actualValue.equals(headerValue) ?
				success() :
				failure(shouldHaveHeaderWithValue(headerName, headerValue, actualValue));
	}

	/**
	 * Check that status code of http response has an expected status.
	 *
	 * @param httpResponse Http response.
	 * @param status Expected status.
	 * @return Assertion result.
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
	 * @return Assertion result.
	 */
	public AssertionResult isStatusBetween(HttpResponse httpResponse, int start, int end) {
		int actualStatus = httpResponse.getStatus();
		return actualStatus >= start && actualStatus <= end ?
				success() :
				failure(shouldHaveStatusBetween(actualStatus, start, end));
	}
}
