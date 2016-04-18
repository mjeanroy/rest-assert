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
import com.github.mjeanroy.rest_assert.internal.data.defaults.StandardHttpStatus;
import com.github.mjeanroy.rest_assert.reflect.Param;

import java.nio.charset.Charset;
import java.util.Date;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveCharset.shouldHaveCharset;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveHeader.shouldHaveHeader;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveMimeType.shouldHaveMimeType;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatus.shouldHaveStatus;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatusBetween.shouldHaveStatusBetween;
import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveStatusOutOf.shouldHaveStatusOutOf;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.internal.data.defaults.StandardHttpHeader.*;
import static com.github.mjeanroy.rest_assert.internal.data.defaults.StandardMimeType.*;
import static com.github.mjeanroy.rest_assert.utils.DateUtils.formatHttpDate;
import static com.github.mjeanroy.rest_assert.utils.DateUtils.parseHttpDate;
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
	private static final HttpResponseAssertions INSTANCE = new HttpResponseAssertions();

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
	 * Check that status code of http response is {@link com.github.mjeanroy.rest_assert.internal.data.defaults.StandardHttpStatus#OK}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isOk(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.OK);
	}

	/**
	 * Check that status code of http response is {@link com.github.mjeanroy.rest_assert.internal.data.defaults.StandardHttpStatus#CREATED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isCreated(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.CREATED);
	}

	/**
	 * Check that status code of http response is 'ACCEPTED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isAccepted(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.ACCEPTED);
	}

	/**
	 * Check that status code of http response is 'NO_CONTENT' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNoContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.NO_CONTENT);
	}

	/**
	 * Check that status code of http response is 'PARTIAL_CONTENT' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isPartialContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.PARTIAL_CONTENT);
	}

	/**
	 * Check that status code of http response is 'RESET_CONTENT' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isResetContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.RESET_CONTENT);
	}

	/**
	 * Check that status code of http response is 'MOVED_PERMANENTLY' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isMovedPermanently(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.MOVED_PERMANENTLY);
	}

	/**
	 * Check that status code of http response is 'MOVED_TEMPORARILY' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isMovedTemporarily(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.MOVED_TEMPORARILY);
	}

	/**
	 * Check that status code of http response is 'NOT_MODIFIED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotModified(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.NOT_MODIFIED);
	}

	/**
	 * Check that status code of http response is 'UNAUTHORIZED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isUnauthorized(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.UNAUTHORIZED);
	}

	/**
	 * Check that status code of http response is 'FORBIDDEN' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isForbidden(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.FORBIDDEN);
	}

	/**
	 * Check that status code of http response is 'BAD_REQUEST' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isBadRequest(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.BAD_REQUEST);
	}

	/**
	 * Check that status code of http response is 'NOT_ACCEPTABLE' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotAcceptable(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Check that status code of http response is 'NOT_FOUND' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotFound(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.NOT_FOUND);
	}

	/**
	 * Check that status code of http response is 'INTERNAL_SERVER_ERROR' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isInternalServerError(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Check that status code of http response is 'PRE_CONDITION_FAILED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isPreConditionFailed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.PRE_CONDITION_FAILED);
	}

	/**
	 * Check that status code of http response is 'METHOD_NOT_ALLOWED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isMethodNotAllowed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Check that status code of http response is 'CONFLICT' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isConflict(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.CONFLICT);
	}

	/**
	 * Check that status code of http response is 'UNSUPPORTED_MEDIA_TYPE' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isUnsupportedMediaType(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * Check that status code of http response is 'NOT_IMPLEMENTED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotImplemented(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, StandardHttpStatus.NOT_IMPLEMENTED);
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
	 * Check that status code of http response is not a success status (i.e
	 * not between 200 and 299).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotSuccess(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 200, 299);
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
	 * Check that status code of http response is not a redirection status (i.e
	 * not between 300 and 399).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotRedirection(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 300, 399);
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
	 * Check that status code of http response is not a server error status (i.e
	 * not between 500 and 599).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotServerError(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 500, 599);
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
	 * Check that status code of http response is not a client error status (i.e
	 * not between 400 and 499).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotClientError(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 400, 499);
	}

	/**
	 * Check that http response contains ETag header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasETag(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ETAG);
	}

	/**
	 * Check that http response contains ETag header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param etagValue    ETag value.
	 * @return Assertion result.
	 */
	public AssertionResult isETagEqualTo(HttpResponse httpResponse, @Param("etagValue") String etagValue) {
		return isHeaderEqualTo(httpResponse, ETAG, etagValue);
	}

	/**
	 * Check that http response contains Content-Type header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentType(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_TYPE);
	}

	/**
	 * Check that http response contains Content-Type header with
	 * expected value.
	 *
	 * @param httpResponse     Http response.
	 * @param contentTypeValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentTypeEqualTo(HttpResponse httpResponse, @Param("contentTypeValue") String contentTypeValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_TYPE, contentTypeValue);
	}

	/**
	 * Check that http response contains Content-Encoding header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentEncoding(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_ENCODING);
	}

	/**
	 * Check that http response contains Content-Disposition header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentDisposition(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_DISPOSITION);
	}

	/**
	 * Check that http response contains Content-Disposition header with
	 * expected value.
	 *
	 * @param httpResponse            Http response.
	 * @param contentDispositionValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentDispositionEqualTo(HttpResponse httpResponse, @Param("contentDispositionValue") String contentDispositionValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_DISPOSITION, contentDispositionValue);
	}

	/**
	 * Check that http response contains Content-Encoding header with
	 * expected value.
	 *
	 * @param httpResponse         Http response.
	 * @param contentEncodingValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentEncodingEqualTo(HttpResponse httpResponse, @Param("contentEncodingValue") String contentEncodingValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_ENCODING, contentEncodingValue);
	}

	/**
	 * Check that http response contains Content-Encoding header with
	 * gzip value (meaning that response body has been gzipped).
	 * This is a shortcut for checking that Content-Encoding is strictly equal
	 * to "gzip".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isGzipped(HttpResponse httpResponse) {
		return isHeaderEqualTo(httpResponse, CONTENT_ENCODING, "gzip");
	}

	/**
	 * Check that http response contains Content-Length header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentLength(HttpResponse httpResponse) {
		return hasHeader(httpResponse, "Content-Length");
	}

	/**
	 * Check that http response contains Location header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasLocation(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LOCATION);
	}

	/**
	 * Check that http response contains Location header with
	 * expected value.
	 *
	 * @param httpResponse  Http response.
	 * @param locationValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLocationEqualTo(HttpResponse httpResponse, @Param("locationValue") String locationValue) {
		return isHeaderEqualTo(httpResponse, LOCATION, locationValue);
	}

	/**
	 * Check that http response contains Last-Modified header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasLastModified(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LAST_MODIFIED);
	}

	/**
	 * Check that http response contains Last-Modified header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasExpires(HttpResponse httpResponse) {
		return hasHeader(httpResponse, EXPIRES);
	}

	/**
	 * Check that http response contains Expires header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param expiresValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isExpiresEqualTo(HttpResponse httpResponse, @Param("expiresValue") String expiresValue) {
		return isExpiresEqualTo(httpResponse, parseHttpDate(expiresValue));
	}

	/**
	 * Check that http response contains Expires header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param expiresValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isExpiresEqualTo(HttpResponse httpResponse, @Param("expiresValue") Date expiresValue) {
		return isHeaderDateEqualTo(httpResponse, EXPIRES, expiresValue);
	}

	/**
	 * Check that http response contains Last-Modified header with
	 * expected value.
	 *
	 * @param httpResponse      Http response.
	 * @param lastModifiedValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, @Param("lastModifiedValue") String lastModifiedValue) {
		return isLastModifiedEqualTo(httpResponse, parseHttpDate(lastModifiedValue));
	}

	/**
	 * Check that http response contains Last-Modified header with
	 * expected value.
	 *
	 * @param httpResponse     Http response.
	 * @param lastModifiedDate Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, @Param("lastModifiedValue") Date lastModifiedDate) {
		return isHeaderDateEqualTo(httpResponse, LAST_MODIFIED, lastModifiedDate);
	}

	private AssertionResult isHeaderDateEqualTo(HttpResponse httpResponse, String name, Date expectedValue) {
		AssertionResult result = hasHeader(httpResponse, name);
		if (result.isFailure()) {
			return result;
		}

		String actualValue = httpResponse.getHeader(name);
		Date actualDate = parseHttpDate(actualValue);
		return actualDate.equals(expectedValue) ?
				success() :
				failure(shouldHaveHeaderWithValue(name, formatHttpDate(expectedValue), formatHttpDate(actualDate)));
	}

	/**
	 * Check that http response contains Cache-Control header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasCacheControl(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CACHE_CONTROL);
	}

	/**
	 * Check that http response contains Cache-Control header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param cacheControl Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, @Param("cacheControl") String cacheControl) {
		return isHeaderEqualTo(httpResponse, CACHE_CONTROL, cacheControl);
	}

	/**
	 * Check that http response contains X-XSS-Protection header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasXssProtection(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_XSS_PROTECTION);
	}

	/**
	 * Check that http response is "application/json".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isJson(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, JSON);
	}

	/**
	 * Check that http response is "application/xml" or "text/xml".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isXml(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(
				APPLICATION_XML,
				TEXT_XML
		));
	}

	/**
	 * Check that http response is "text/css".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isCss(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, CSS);
	}

	/**
	 * Check that http response is "text/plain".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isText(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, TEXT_PLAIN);
	}

	/**
	 * Check that http response is "text/csv".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isCsv(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, CSV);
	}

	/**
	 * Check that http response is "application/pdf".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isPdf(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, PDF);
	}

	/**
	 * Check that http response is "text/html" or "application/xhtml+xml".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isHtml(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(
				TEXT_HTML,
				XHTML
		));
	}

	/**
	 * Check that http response has UTF-8 charset.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isUtf8(HttpResponse httpResponse) {
		return hasCharset(httpResponse, "UTF-8");
	}

	/**
	 * Check that http response is "application/javascript" or "text/javascript".
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isJavascript(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(
				APPLICATION_JAVASCRIPT,
				TEXT_JAVASCRIPT
		));
	}

	/**
	 * Check that http response contains expected header.
	 *
	 * @param httpResponse Http response.
	 * @param headerName   Header name.
	 * @return Assertion result.
	 */
	public AssertionResult hasHeader(HttpResponse httpResponse, @Param("headerName") String headerName) {
		return httpResponse.hasHeader(headerName) ?
				success() :
				failure(shouldHaveHeader(headerName));
	}

	/**
	 * Check that http response is expected mime type.
	 *
	 * @param httpResponse     Http response.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 */
	public AssertionResult hasMimeType(HttpResponse httpResponse, @Param("expectedMimeType") String expectedMimeType) {
		String headerName = CONTENT_TYPE;
		AssertionResult result = hasHeader(httpResponse, headerName);
		if (result.isFailure()) {
			return result;
		}

		String contentType = httpResponse.getHeader(headerName);
		String actualMimeType = contentType.split(";")[0].trim().toLowerCase();
		return actualMimeType.equals(expectedMimeType) ?
				success() :
				failure(shouldHaveMimeType(expectedMimeType, actualMimeType));
	}

	/**
	 * Check that http response has expected charset.
	 *
	 * @param httpResponse    Http response.
	 * @param expectedCharset Expected charset.
	 * @return Assertion result.
	 */
	public AssertionResult hasCharset(HttpResponse httpResponse, @Param("expectedCharset") Charset expectedCharset) {
		return hasCharset(httpResponse, expectedCharset.name());
	}

	/**
	 * Check that http response has expected charset.
	 *
	 * @param httpResponse    Http response.
	 * @param expectedCharset Expected charset.
	 * @return Assertion result.
	 */
	public AssertionResult hasCharset(HttpResponse httpResponse, @Param("expectedCharset") String expectedCharset) {
		String headerName = CONTENT_TYPE;
		AssertionResult result = hasHeader(httpResponse, headerName);
		if (result.isFailure()) {
			return result;
		}

		String contentType = httpResponse.getHeader(headerName);
		String[] contentTypeParts = contentType.split(";");
		if (contentTypeParts.length == 1) {
			return failure(shouldHaveCharset());
		}

		String actualCharset = contentTypeParts[1].trim().split("=")[1].trim();
		return actualCharset.equalsIgnoreCase(expectedCharset) ?
				success() :
				failure(shouldHaveCharset(expectedCharset, actualCharset));
	}

	/**
	 * Check that http response is expected mime type.
	 *
	 * @param httpResponse     Http response.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 */
	public AssertionResult hasMimeTypeIn(HttpResponse httpResponse, @Param("expectedMimeType") Iterable<String> expectedMimeType) {
		String headerName = CONTENT_TYPE;
		AssertionResult result = hasHeader(httpResponse, headerName);
		if (result.isFailure()) {
			return result;
		}

		String contentType = httpResponse.getHeader(headerName);
		String actualMimeType = contentType.split(";")[0].trim().toLowerCase();

		boolean found = false;
		for (String current : expectedMimeType) {
			if (actualMimeType.equals(current.toLowerCase())) {
				found = true;
				break;
			}
		}

		return found ?
				success() :
				failure(shouldHaveMimeType(expectedMimeType, actualMimeType));
	}

	/**
	 * Check that http response contains expected header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param headerName   Header name.
	 * @param headerValue  Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isHeaderEqualTo(HttpResponse httpResponse, @Param("headerName") String headerName, @Param("headerValue") String headerValue) {
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
	 * @param status       Expected status.
	 * @return Assertion result.
	 */
	public AssertionResult isStatusEqual(HttpResponse httpResponse, @Param("status") int status) {
		return httpResponse.getStatus() == status ?
				success() :
				failure(shouldHaveStatus(httpResponse.getStatus(), status));
	}

	/**
	 * Check that status code of http response is include between
	 * a lower bound and an upper bound (inclusive).
	 *
	 * @param httpResponse Http response.
	 * @param start        Lower bound.
	 * @param end          Upper bound.
	 * @return Assertion result.
	 */
	public AssertionResult isStatusBetween(HttpResponse httpResponse, @Param("start") int start, @Param("end") int end) {
		int actualStatus = httpResponse.getStatus();
		return actualStatus >= start && actualStatus <= end ?
				success() :
				failure(shouldHaveStatusBetween(actualStatus, start, end));
	}

	/**
	 * Check that status code of http response is not included between
	 * a lower bound and an upper bound (inclusive).
	 *
	 * @param httpResponse Http response.
	 * @param start        Lower bound.
	 * @param end          Upper bound.
	 * @return Assertion result.
	 */
	public AssertionResult isStatusOutOf(HttpResponse httpResponse, @Param("start") int start, @Param("end") int end) {
		int actualStatus = httpResponse.getStatus();
		return actualStatus < start || actualStatus > end ?
				success() :
				failure(shouldHaveStatusOutOf(actualStatus, start, end));
	}
}
