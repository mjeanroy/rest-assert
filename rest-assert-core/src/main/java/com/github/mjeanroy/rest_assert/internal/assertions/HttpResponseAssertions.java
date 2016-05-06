/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.github.mjeanroy.rest_assert.internal.assertions.impl.DoesNotHaveCookieAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.DoesNotHaveHeaderAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.HasCharsetAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.HasCookieAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.HasHeaderAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.HasMimeTypeAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.IsDateHeaderEqualToAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.IsHeaderEqualToAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.IsHeaderListEqualToAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.IsHeaderMatchingAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.StatusBetweenAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.StatusEqualAssertion;
import com.github.mjeanroy.rest_assert.internal.assertions.impl.StatusOutOfAssertion;
import com.github.mjeanroy.rest_assert.data.CacheControl;
import com.github.mjeanroy.rest_assert.data.ContentSecurityPolicy;
import com.github.mjeanroy.rest_assert.data.ContentTypeOptions;
import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.data.FrameOptions;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.internal.data.HttpStatusCodes;
import com.github.mjeanroy.rest_assert.data.XssProtection;
import com.github.mjeanroy.rest_assert.reflect.Param;

import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.ACCESS_CONTROL_ALLOW_HEADERS;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.ACCESS_CONTROL_ALLOW_ORIGIN;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.CACHE_CONTROL;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.CONTENT_DISPOSITION;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.CONTENT_ENCODING;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.CONTENT_LENGTH;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.CONTENT_SECURITY_POLICY;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.CONTENT_TYPE;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.ETAG;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.EXPIRES;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.LAST_MODIFIED;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.LOCATION;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.PRAGMA;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.X_CONTENT_TYPE_OPTIONS;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.X_FRAME_OPTIONS;
import static com.github.mjeanroy.rest_assert.internal.data.HttpHeader.X_XSS_PROTECTION;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.APPLICATION_JAVASCRIPT;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.APPLICATION_XML;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.CSS;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.CSV;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.JSON;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.PDF;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.TEXT_HTML;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.TEXT_JAVASCRIPT;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.TEXT_PLAIN;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.TEXT_XML;
import static com.github.mjeanroy.rest_assert.internal.data.MimeTypes.XHTML;
import static com.github.mjeanroy.rest_assert.utils.DateUtils.parseHttpDate;
import static java.util.Arrays.asList;
import static java.util.Collections.addAll;

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
	 * Check that status code of http response is {@link HttpStatusCodes#OK}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isOk(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.OK);
	}

	/**
	 * Check that status code of http response is {@link HttpStatusCodes#CREATED}.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isCreated(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.CREATED);
	}

	/**
	 * Check that status code of http response is 'ACCEPTED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isAccepted(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.ACCEPTED);
	}

	/**
	 * Check that status code of http response is 'NO_CONTENT' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNoContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NO_CONTENT);
	}

	/**
	 * Check that status code of http response is 'PARTIAL_CONTENT' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isPartialContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.PARTIAL_CONTENT);
	}

	/**
	 * Check that status code of http response is 'RESET_CONTENT' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isResetContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.RESET_CONTENT);
	}

	/**
	 * Check that status code of http response is 'MOVED_PERMANENTLY' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isMovedPermanently(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.MOVED_PERMANENTLY);
	}

	/**
	 * Check that status code of http response is 'MOVED_TEMPORARILY' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isMovedTemporarily(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.MOVED_TEMPORARILY);
	}

	/**
	 * Check that status code of http response is 'NOT_MODIFIED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotModified(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_MODIFIED);
	}

	/**
	 * Check that status code of http response is 'UNAUTHORIZED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isUnauthorized(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.UNAUTHORIZED);
	}

	/**
	 * Check that status code of http response is 'FORBIDDEN' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isForbidden(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.FORBIDDEN);
	}

	/**
	 * Check that status code of http response is 'BAD_REQUEST' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isBadRequest(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.BAD_REQUEST);
	}

	/**
	 * Check that status code of http response is 'NOT_ACCEPTABLE' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotAcceptable(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_ACCEPTABLE);
	}

	/**
	 * Check that status code of http response is 'NOT_FOUND' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotFound(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_FOUND);
	}

	/**
	 * Check that status code of http response is 'INTERNAL_SERVER_ERROR' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isInternalServerError(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Check that status code of http response is 'PRE_CONDITION_FAILED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isPreConditionFailed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.PRE_CONDITION_FAILED);
	}

	/**
	 * Check that status code of http response is 'METHOD_NOT_ALLOWED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isMethodNotAllowed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.METHOD_NOT_ALLOWED);
	}

	/**
	 * Check that status code of http response is 'CONFLICT' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isConflict(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.CONFLICT);
	}

	/**
	 * Check that status code of http response is 'UNSUPPORTED_MEDIA_TYPE' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isUnsupportedMediaType(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * Check that status code of http response is 'NOT_IMPLEMENTED' status.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult isNotImplemented(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_IMPLEMENTED);
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
		return hasHeader(httpResponse, ETAG.getName());
	}

	/**
	 * Check that http response does contains ETag header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveETag(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, ETAG.getName());
	}

	/**
	 * Check that http response contains ETag header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param etagValue ETag value.
	 * @return Assertion result.
	 */
	public AssertionResult isETagEqualTo(HttpResponse httpResponse, @Param("etagValue") String etagValue) {
		return isHeaderEqualTo(httpResponse, ETAG.getName(), etagValue);
	}

	/**
	 * Check that http response contains Content-Type header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentType(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_TYPE.getName());
	}

	/**
	 * Check that http response contains Content-Type header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param contentTypeValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentTypeEqualTo(HttpResponse httpResponse, @Param("contentTypeValue") String contentTypeValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_TYPE.getName(), contentTypeValue);
	}

	/**
	 * Check that http response contains Content-Encoding header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentEncoding(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_ENCODING.getName());
	}

	/**
	 * Check that http response does not contains Content-Encoding header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveContentEncoding(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, CONTENT_ENCODING.getName());
	}

	/**
	 * Check that http response contains Content-Disposition header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentDisposition(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_DISPOSITION.getName());
	}

	/**
	 * Check that http response does contains Content-Disposition header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveContentDispostion(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, CONTENT_DISPOSITION.getName());
	}

	/**
	 * Check that http response contains Content-Disposition header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param contentDispositionValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentDispositionEqualTo(HttpResponse httpResponse, @Param("contentDispositionValue") String contentDispositionValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_DISPOSITION.getName(), contentDispositionValue);
	}

	/**
	 * Check that http response contains Content-Encoding header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param contentEncodingValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentEncodingEqualTo(HttpResponse httpResponse, @Param("contentEncodingValue") String contentEncodingValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_ENCODING.getName(), contentEncodingValue);
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
		return isHeaderEqualTo(httpResponse, CONTENT_ENCODING.getName(), "gzip");
	}

	/**
	 * Check that http response contains Content-Length header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentLength(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_LENGTH.getName());
	}

	/**
	 * Check that http response contains Location header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasLocation(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LOCATION.getName());
	}

	/**
	 * Check that http response does contains Location header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveLocation(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, LOCATION.getName());
	}

	/**
	 * Check that http response contains Location header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param locationValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLocationEqualTo(HttpResponse httpResponse, @Param("locationValue") String locationValue) {
		return isHeaderEqualTo(httpResponse, LOCATION.getName(), locationValue);
	}

	/**
	 * Check that http response contains Last-Modified header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasLastModified(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LAST_MODIFIED.getName());
	}

	/**
	 * Check that http response does contains Last-Modifier header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveLastModified(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, LAST_MODIFIED.getName());
	}

	/**
	 * Check that http response contains Last-Modified header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasExpires(HttpResponse httpResponse) {
		return hasHeader(httpResponse, EXPIRES.getName());
	}

	/**
	 * Check that http response does contains Expires header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveExpires(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, EXPIRES.getName());
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
		return assertWith(httpResponse, new IsDateHeaderEqualToAssertion(EXPIRES.getName(), expiresValue));
	}

	/**
	 * Check that http response contains Last-Modified header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
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
	 * @param httpResponse Http response.
	 * @param lastModifiedDate Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, @Param("lastModifiedValue") Date lastModifiedDate) {
		return assertWith(httpResponse, new IsDateHeaderEqualToAssertion(LAST_MODIFIED.getName(), lastModifiedDate));
	}

	/**
	 * Check that http response contains Pragma header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasPragma(HttpResponse httpResponse) {
		return hasHeader(httpResponse, PRAGMA.getName());
	}

	/**
	 * Check that http response does contains Pragma header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHavePragma(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, PRAGMA.getName());
	}

	/**
	 * Check that http response contains Pragma header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param pragma Pragma value.
	 * @return Assertion result.
	 */
	public AssertionResult isPragmaEqualTo(HttpResponse httpResponse, @Param("pragma") String pragma) {
		return isHeaderEqualTo(httpResponse, PRAGMA.getName(), pragma);
	}

	/**
	 * Check that http response contains Cache-Control header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasCacheControl(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CACHE_CONTROL.getName());
	}

	/**
	 * Check that http response does contains Cache-Control header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveCacheControl(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, CACHE_CONTROL.getName());
	}

	/**
	 * Check that http response contains Cache-Control header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param cacheControl Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, @Param("cacheControl") String cacheControl) {
		return isHeaderEqualTo(httpResponse, CACHE_CONTROL.getName(), cacheControl);
	}

	/**
	 * Check that http response contains Cache-Control header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param cacheControl Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, @Param("cacheControl") CacheControl cacheControl) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CACHE_CONTROL.getName(), cacheControl));
	}

	/**
	 * Check that http response contains X-XSS-Protection header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasXssProtection(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_XSS_PROTECTION.getName());
	}

	/**
	 * Check that http response does contains X-XSS-Protection header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveXssProtection(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, X_XSS_PROTECTION.getName());
	}

	/**
	 * Check that http response contains X-XSS-Protection header.
	 *
	 * @param httpResponse Http response.
	 * @param xssProtection Expected X-XSS-Protection header value.
	 * @return Assertion result.
	 */
	public AssertionResult isXssProtectionEqualTo(HttpResponse httpResponse, @Param("xssProtection") String xssProtection) {
		return isHeaderEqualTo(httpResponse, X_XSS_PROTECTION.getName(), xssProtection);
	}

	/**
	 * Check that http response contains X-XSS-Protection header.
	 *
	 * @param httpResponse Http response.
	 * @param xssProtection Expected X-XSS-Protection header value.
	 * @return Assertion result.
	 */
	public AssertionResult isXssProtectionEqualTo(HttpResponse httpResponse, @Param("xssProtection") XssProtection xssProtection) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_XSS_PROTECTION.getName(), xssProtection));
	}

	/**
	 * Check that http response contains X-Content-Type-Options header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentTypeOptions(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_CONTENT_TYPE_OPTIONS.getName());
	}

	/**
	 * Check that http response does contains X-Content-Type-Options header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveContentTypeOptions(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, X_CONTENT_TYPE_OPTIONS.getName());
	}

	/**
	 * Check that http response contains X-Content-Type-Options header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param contentTypeOptions Expected X-Content-Type-Options header value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentTypeOptionsEqualTo(HttpResponse httpResponse, @Param("contentTypeOptions") String contentTypeOptions) {
		return isHeaderEqualTo(httpResponse, X_CONTENT_TYPE_OPTIONS.getName(), contentTypeOptions);
	}

	/**
	 * Check that http response contains X-Content-Type-Options header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param contentTypeOptions Expected X-Content-Type-Options header value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentTypeOptionsEqualTo(HttpResponse httpResponse, @Param("contentTypeOptions") ContentTypeOptions contentTypeOptions) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_CONTENT_TYPE_OPTIONS.getName(), contentTypeOptions));
	}

	/**
	 * Check that http response contains X-Frame-Options header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasFrameOptions(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_FRAME_OPTIONS.getName());
	}

	/**
	 * Check that http response does contains X-Frame-Options header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveFrameOptions(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, X_FRAME_OPTIONS.getName());
	}

	/**
	 * Check that http response contains X-Frame-Options header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param frameOptions Expected X-Frame-Options header value.
	 * @return Assertion result.
	 */
	public AssertionResult isFrameOptionsEqualTo(HttpResponse httpResponse, @Param("frameOptions") String frameOptions) {
		return isHeaderEqualTo(httpResponse, X_FRAME_OPTIONS.getName(), frameOptions);
	}

	/**
	 * Check that http response contains X-Frame-Options header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param frameOptions Expected X-Frame-Options header value.
	 * @return Assertion result.
	 */
	public AssertionResult isFrameOptionsEqualTo(HttpResponse httpResponse, @Param("frameOptions") FrameOptions frameOptions) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_FRAME_OPTIONS.getName(), frameOptions));
	}

	/**
	 * Check that http response contains Content-Security-Policy header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentSecurityPolicy(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_SECURITY_POLICY.getName());
	}

	/**
	 * Check that http response does contains Content-Security-Policy header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveContentSecurityPolicy(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, CONTENT_SECURITY_POLICY.getName());
	}

	/**
	 * Check that http response contains Content-Security-Policy header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param contentSecurityPolicy Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentSecurityPolicyControlEqualTo(HttpResponse httpResponse, @Param("contentSecurityPolicy") String contentSecurityPolicy) {
		return isHeaderEqualTo(httpResponse, CONTENT_SECURITY_POLICY.getName(), contentSecurityPolicy);
	}

	/**
	 * Check that http response contains Content-Security-Policy header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param contentSecurityPolicy Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentSecurityPolicyControlEqualTo(HttpResponse httpResponse, @Param("contentSecurityPolicy") ContentSecurityPolicy contentSecurityPolicy) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CONTENT_SECURITY_POLICY.getName(), contentSecurityPolicy));
	}

	/**
	 * Check that http response contains Access-Control-Allow-Origin header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasAccessControlAllowOrigin(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName());
	}

	/**
	 * Check that http response does contains Access-Control-Allow-Origin header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveAccessControlAllowOrigin(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName());
	}

	/**
	 * Check that http response contains Access-Control-Allow-Origin header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param accessControlAllowOrigin Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowOriginEqualTo(HttpResponse httpResponse, @Param("accessControlAllowOrigin") String accessControlAllowOrigin) {
		return isHeaderEqualTo(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName(), accessControlAllowOrigin);
	}

	/**
	 * Check that http response contains Access-Control-Allow-Headers header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult hasAccessControlAllowHeaders(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_HEADERS.getName());
	}

	/**
	 * Check that http response does contains Access-Control-Allow-Headers header.
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveAccessControlAllowHeaders(HttpResponse httpResponse) {
		return doesNothaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_HEADERS.getName());
	}

	/**
	 * Check that http response contains Access-Control-Allow-Headers header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param value Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowHeadersEqualTo(HttpResponse httpResponse, @Param("value") String value, @Param("other") String... other) {
		List<String> list = new LinkedList<>();
		list.add(value);
		addAll(list, other);
		return isAccessControlAllowHeadersEqualTo(httpResponse, list);
	}

	/**
	 * Check that http response contains Access-Control-Allow-Headers header with expected value.
	 *
	 * @param httpResponse Http response.
	 * @param accessControlAllowHeaders Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowHeadersEqualTo(HttpResponse httpResponse, @Param("accessControlAllowHeaders") Iterable<String> accessControlAllowHeaders) {
		return assertWith(httpResponse, new IsHeaderListEqualToAssertion(ACCESS_CONTROL_ALLOW_HEADERS.getName(), accessControlAllowHeaders));
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
	 * @param headerName Header name.
	 * @return Assertion result.
	 */
	public AssertionResult hasHeader(HttpResponse httpResponse, @Param("headerName") String headerName) {
		return assertWith(httpResponse, new HasHeaderAssertion(headerName));
	}

	/**
	 * Check that http response does not contains expected header.
	 *
	 * @param httpResponse Http response.
	 * @param headerName Header name.
	 * @return Assertion result.
	 */
	public AssertionResult doesNothaveHeader(HttpResponse httpResponse, @Param("headerName") String headerName) {
		return assertWith(httpResponse, new DoesNotHaveHeaderAssertion(headerName));
	}

	/**
	 * Check that http response is expected mime type.
	 *
	 * @param httpResponse Http response.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 */
	public AssertionResult hasMimeType(HttpResponse httpResponse, @Param("expectedMimeType") String expectedMimeType) {
		return assertWith(httpResponse, new HasMimeTypeAssertion(expectedMimeType));
	}

	/**
	 * Check that http response has expected charset.
	 *
	 * @param httpResponse Http response.
	 * @param expectedCharset Expected charset.
	 * @return Assertion result.
	 */
	public AssertionResult hasCharset(HttpResponse httpResponse, @Param("expectedCharset") Charset expectedCharset) {
		return hasCharset(httpResponse, expectedCharset.name());
	}

	/**
	 * Check that http response has expected charset.
	 *
	 * @param httpResponse Http response.
	 * @param expectedCharset Expected charset.
	 * @return Assertion result.
	 */
	public AssertionResult hasCharset(HttpResponse httpResponse, @Param("expectedCharset") String expectedCharset) {
		return assertWith(httpResponse, new HasCharsetAssertion(expectedCharset));
	}

	/**
	 * Check that http response is expected mime type.
	 *
	 * @param httpResponse Http response.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 */
	public AssertionResult hasMimeTypeIn(HttpResponse httpResponse, @Param("expectedMimeType") Iterable<String> expectedMimeType) {
		return assertWith(httpResponse, new HasMimeTypeAssertion(expectedMimeType));
	}

	/**
	 * Check that http response contains expected header with
	 * expected value.
	 *
	 * @param httpResponse Http response.
	 * @param headerName Header name.
	 * @param headerValue Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isHeaderEqualTo(HttpResponse httpResponse, @Param("headerName") String headerName, @Param("headerValue") String headerValue) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(headerName, headerValue));
	}

	/**
	 * Check that status code of http response has an expected status.
	 *
	 * @param httpResponse Http response.
	 * @param status Expected status.
	 * @return Assertion result.
	 */
	public AssertionResult isStatusEqual(HttpResponse httpResponse, @Param("status") int status) {
		return assertWith(httpResponse, new StatusEqualAssertion(status));
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
	public AssertionResult isStatusBetween(HttpResponse httpResponse, @Param("start") int start, @Param("end") int end) {
		return assertWith(httpResponse, new StatusBetweenAssertion(start, end));
	}

	/**
	 * Check that status code of http response is not included between
	 * a lower bound and an upper bound (inclusive).
	 *
	 * @param httpResponse Http response.
	 * @param start Lower bound.
	 * @param end Upper bound.
	 * @return Assertion result.
	 */
	public AssertionResult isStatusOutOf(HttpResponse httpResponse, @Param("start") int start, @Param("end") int end) {
		return assertWith(httpResponse, new StatusOutOfAssertion(start, end));
	}

	/**
	 * Check that http response does not contains any cookies.
	 *
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveCookie(HttpResponse httpResponse) {
		return assertWith(httpResponse, new DoesNotHaveCookieAssertion());
	}

	/**
	 * Check that http response does not contains cookie with given name (note that cookie name
	 * is case-sensitive).
	 *
	 * @param name Cookie name.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveCookie(HttpResponse httpResponse, @Param("name") String name) {
		return assertWith(httpResponse, new DoesNotHaveCookieAssertion(name));
	}

	/**
	 * Check that http response contains cookie with given name (note that cookie name is
	 * case-sensitive).
	 *
	 * @param name Cookie name.
	 * @return Assertion result.
	 */
	public AssertionResult hasCookie(HttpResponse httpResponse, @Param("name") String name) {
		return assertWith(httpResponse, new HasCookieAssertion(name));
	}

	/**
	 * Check that http response contains cookie with given name and value (note that cookie name
	 * is case-sensitive).
	 *
	 * @param name Cookie name.
	 * @param value Cookie value.
	 * @return Assertion result.
	 */
	public AssertionResult hasCookie(HttpResponse httpResponse, @Param("name") String name, @Param("value") String value) {
		return assertWith(httpResponse, new HasCookieAssertion(name, value));
	}

	/**
	 * Check that http response contains cookie.
	 *
	 * @param cookie Cookie.
	 * @return Assertion result.
	 */
	public AssertionResult hasCookie(HttpResponse httpResponse, @Param("cookie") Cookie cookie) {
		return assertWith(httpResponse, new HasCookieAssertion(cookie));
	}

	private AssertionResult assertWith(HttpResponse httpResponse, Assertion<HttpResponse> assertion) {
		return assertion.handle(httpResponse);
	}
}
