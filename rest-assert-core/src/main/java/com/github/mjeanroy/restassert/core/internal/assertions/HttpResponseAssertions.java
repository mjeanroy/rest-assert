/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.assertions;

import com.github.mjeanroy.restassert.core.data.CacheControl;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy;
import com.github.mjeanroy.restassert.core.data.ContentTypeOptions;
import com.github.mjeanroy.restassert.core.data.FrameOptions;
import com.github.mjeanroy.restassert.core.data.RequestMethod;
import com.github.mjeanroy.restassert.core.data.StrictTransportSecurity;
import com.github.mjeanroy.restassert.core.data.XssProtection;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.DoesNotHaveCookieAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.DoesNotHaveHeaderAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.HasCharsetAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.HasCookieAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.HasHeaderAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.HasMimeTypeAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.IsDateHeaderEqualToAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.IsHeaderEqualToAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.IsHeaderListEqualToAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.IsHeaderMatchingAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.StatusBetweenAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.StatusEqualAssertion;
import com.github.mjeanroy.restassert.core.internal.assertions.impl.StatusOutOfAssertion;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.HttpStatusCodes;
import com.github.mjeanroy.restassert.documentation.Documentation;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.ACCESS_CONTROL_ALLOW_HEADERS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.ACCESS_CONTROL_ALLOW_MAX_AGE;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.ACCESS_CONTROL_ALLOW_METHODS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.ACCESS_CONTROL_ALLOW_ORIGIN;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.ACCESS_CONTROL_EXPOSE_HEADERS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.CACHE_CONTROL;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.CONTENT_DISPOSITION;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.CONTENT_ENCODING;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.CONTENT_LENGTH;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.CONTENT_SECURITY_POLICY;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.CONTENT_TYPE;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.ETAG;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.EXPIRES;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.LAST_MODIFIED;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.LOCATION;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.PRAGMA;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.STRICT_TRANSPORT_SECURITY;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.X_CONTENT_TYPE_OPTIONS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.X_FRAME_OPTIONS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.X_XSS_PROTECTION;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.APPLICATION_JAVASCRIPT;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.APPLICATION_XML;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.CSS;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.CSV;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.JSON;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.PDF;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.TEXT_HTML;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.TEXT_JAVASCRIPT;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.TEXT_PLAIN;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.TEXT_XML;
import static com.github.mjeanroy.restassert.core.internal.data.MimeTypes.XHTML;
import static com.github.mjeanroy.restassert.core.internal.common.Dates.parseHttpDate;
import static java.util.Arrays.asList;
import static java.util.Collections.addAll;

/**
 * Reusable Assertions of http response.
 * This class is implemented as a singleton object.
 * This class is thread safe.
 */
@Documentation
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
	 * Check that status code of http response is {@code "OK"} (i.e is strictly equals to {@code 200}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/200">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/200</a>
	 * @see <a href="https://httpstatuses.com/200">https://httpstatuses.com/200</a>
	 */
	public AssertionResult isOk(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.OK);
	}

	/**
	 * Check that status code of http response is {@code "CREATED"} (i.e is strictly equals to {@code 201}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/201">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/201</a>
	 * @see <a href="https://httpstatuses.com/201">https://httpstatuses.com/201</a>
	 */
	public AssertionResult isCreated(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.CREATED);
	}

	/**
	 * Check that status code of http response is {@code "ACCEPTED"} status (i.e is strictly equals to {@code 202}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/202">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/202</a>
	 * @see <a href="https://httpstatuses.com/202">https://httpstatuses.com/202</a>
	 */
	public AssertionResult isAccepted(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.ACCEPTED);
	}

	/**
	 * Check that status code of http response is {@code "NO CONTENT"} status (i.e is strictly equals to {@code 204}).
	 *
	 * @param httpResponse Http response.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/204">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/204</a>
	 * @see <a href="https://httpstatuses.com/204">https://httpstatuses.com/204</a>
	 */
	public AssertionResult isNoContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NO_CONTENT);
	}

	/**
	 * Check that status code of http response is {@code "PARTIAL CONTENT"} status (i.e is strictly equals to {@code 206}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/206">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/206</a>
	 * @see <a href="https://httpstatuses.com/206">https://httpstatuses.com/206</a>
	 */
	public AssertionResult isPartialContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.PARTIAL_CONTENT);
	}

	/**
	 * Check that status code of http response is {@code "RESET CONTENT"} status (i.e is strictly equals to {@code 205}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/205">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/205</a>
	 * @see <a href="https://httpstatuses.com/205">https://httpstatuses.com/205</a>
	 */
	public AssertionResult isResetContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.RESET_CONTENT);
	}

	/**
	 * Check that status code of http response is {@code "MOVED PERMANENTLY"} status (i.e is strictly equals to {@code 301}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/301">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/301</a>
	 * @see <a href="https://httpstatuses.com/301">https://httpstatuses.com/301</a>
	 */
	public AssertionResult isMovedPermanently(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.MOVED_PERMANENTLY);
	}

	/**
	 * Check that status code of http response is {@code "MOVED TEMPORARILY"} status (i.e is strictly equals to {@code 302}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/302">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/302</a>
	 * @see <a href="https://httpstatuses.com/302">https://httpstatuses.com/302</a>
	 */
	public AssertionResult isMovedTemporarily(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.MOVED_TEMPORARILY);
	}

	/**
	 * Check that status code of http response is {@code "NOT MODIFIED"} status (i.e is strictly equals to {@code 304}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/304">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/304</a>
	 * @see <a href="https://httpstatuses.com/304">https://httpstatuses.com/304</a>
	 */
	public AssertionResult isNotModified(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_MODIFIED);
	}

	/**
	 * Check that status code of http response is {@code "UNAUTHORIZED"} status (i.e is strictly equals to {@code 401}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/401">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/401</a>
	 * @see <a href="https://httpstatuses.com/401">https://httpstatuses.com/401</a>
	 */
	public AssertionResult isUnauthorized(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.UNAUTHORIZED);
	}

	/**
	 * Check that status code of http response is {@code "FORBIDDEN"} status (i.e is strictly equals to {@code 403}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/403">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/403</a>
	 * @see <a href="https://httpstatuses.com/403">https://httpstatuses.com/403</a>
	 */
	public AssertionResult isForbidden(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.FORBIDDEN);
	}

	/**
	 * Check that status code of http response is {@code "BAD REQUEST"} status (i.e is strictly equals to {@code 400}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400</a>
	 * @see <a href="https://httpstatuses.com/400">https://httpstatuses.com/400</a>
	 */
	public AssertionResult isBadRequest(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.BAD_REQUEST);
	}

	/**
	 * Check that status code of http response is {@code "NOT ACCEPTABLE"} status (i.e is strictly equals to {@code 406}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/406">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/406</a>
	 * @see <a href="https://httpstatuses.com/406">https://httpstatuses.com/406</a>
	 */
	public AssertionResult isNotAcceptable(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_ACCEPTABLE);
	}

	/**
	 * Check that status code of http response is {@code "NOT FOUND"} status (i.e is strictly equals to {@code 404}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/405">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/405</a>
	 * @see <a href="https://httpstatuses.com/406">https://httpstatuses.com/405</a>
	 */
	public AssertionResult isNotFound(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_FOUND);
	}

	/**
	 * Check that status code of http response is {@code "INTERNAL SERVER ERROR"} status (i.e is strictly equals to {@code 500}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/500">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/500</a>
	 * @see <a href="https://httpstatuses.com/500">https://httpstatuses.com/500</a>
	 */
	public AssertionResult isInternalServerError(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Check that status code of http response is {@code "PRE CONDITION FAILED"} status (i.e is strictly equals to {@code 412}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/412">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/412</a>
	 * @see <a href="https://httpstatuses.com/412">https://httpstatuses.com/412</a>
	 */
	public AssertionResult isPreConditionFailed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.PRE_CONDITION_FAILED);
	}

	/**
	 * Check that status code of http response is {@code "METHOD NOT ALLOWED} status (i.e is strictly equals to {@code 405}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/405">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/405</a>
	 * @see <a href="https://httpstatuses.com/405">https://httpstatuses.com/405</a>
	 */
	public AssertionResult isMethodNotAllowed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.METHOD_NOT_ALLOWED);
	}

	/**
	 * Check that status code of http response is {@code "CONFLICT"} status (i.e is strictly equals to {@code 406}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/406">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/406</a>
	 * @see <a href="https://httpstatuses.com/406">https://httpstatuses.com/406</a>
	 */
	public AssertionResult isConflict(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.CONFLICT);
	}

	/**
	 * Check that status code of http response is {@code "UNSUPPORTED MEDIA TYPE"} status (i.e is strictly equals to {@code 415}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/415">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/415</a>
	 * @see <a href="https://httpstatuses.com/415">https://httpstatuses.com/415</a>
	 */
	public AssertionResult isUnsupportedMediaType(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * Check that status code of http response is {@code "NOT IMPLEMENTED"} status (i.e is strictly equals to {@code 501}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/501">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/501</a>
	 * @see <a href="https://httpstatuses.com/501">https://httpstatuses.com/501</a>
	 */
	public AssertionResult isNotImplemented(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_IMPLEMENTED);
	}

	/**
	 * Check that status code of HTTP response is a "SUCCESS" status a.k.a 2XX status.
	 *
	 * Note that this assertion will check that the HTTP response status is between {@code 200} and {@code 299} (inclusive),
	 * it does not check that the status code is in the list of status described
	 * in <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2">official specification</a>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#2xx_Success">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#2xx_Success</a>
	 */
	public AssertionResult isSuccess(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 200, 299);
	}

	/**
	 * Check that status code of HTTP response is <strong>not</strong> a "SUCCESS" status, a.k.a 2XX status.
	 *
	 * Note that this assertion will check that the HTTP response status is <strong>strictly</strong> not
	 * between {@code 200} and {@code 299}.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#2xx_Success">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#2xx_Success</a>
	 */
	public AssertionResult isNotSuccess(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 200, 299);
	}

	/**
	 * Check that status code of HTTP response is a "REDIRECTION" status, a.k.a 3XX status.
	 *
	 * Note that this assertion will check that HTTP response status is between {@code 300} and {@code 399} (inclusive),
	 * it does not check that the status code is in the list of status described
	 * in <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3">official specification</a>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#3xx_Redirection">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#3xx_Redirection</a>
	 */
	public AssertionResult isRedirection(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 300, 399);
	}

	/**
	 * Check that status code of HTTP response is <strong>not</strong> a "REDIRECTION" status, a.k.a 3XX status.
	 *
	 * Note that this assertion will only check that the HTTP response status is <strong>strictly</strong> not
	 * between {@code 300} and {@code 399}.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#3xx_Redirection">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#3xx_Redirection</a>
	 */
	public AssertionResult isNotRedirection(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 300, 399);
	}

	/**
	 * Check that status code of HTTP response is a "SERVER ERROR" status, a.k.a 5XX status.
	 *
	 * Note that this assertion will check that HTTP response status is between {@code 500} and {@code 599} (inclusive),
	 * it does not check that the status code is in the list of status described
	 * in <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5">official specification</a>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#5xx_Server_errors">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#5xx_Server_errors</a>
	 */
	public AssertionResult isServerError(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 500, 599);
	}

	/**
	 * Check that status code of HTTP response is <strong>not</strong> a "SERVER ERROR" status, a.k.a 5XX status.
	 *
	 * Note that this assertion will only check that the HTTP response status is <strong>strictly</strong> not
	 * between {@code 500} and {@code 599}.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#5xx_Server_errors">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#5xx_Server_errors</a>
	 */
	public AssertionResult isNotServerError(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 500, 599);
	}

	/**
	 * Check that status code of HTTP response is a "CLIENT ERROR" status, a.k.a 4XX status.
	 *
	 * Note that this assertion will check that HTTP response status is between {@code 400} and {@code 499} (inclusive),
	 * it does not check that the status code is in the list of status described
	 * in <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4">official specification</a>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors</a>
	 */
	public AssertionResult isClientError(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 400, 499);
	}

	/**
	 * Check that status code of HTTP response is <strong>not</strong> a "CLIENT ERROR" status, a.k.a 4XX status.
	 *
	 * Note that this assertion will only check that the HTTP response status is <strong>strictly</strong> not
	 * between {@code 400} and {@code 499}.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors</a>
	 */
	public AssertionResult isNotClientError(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 400, 499);
	}

	/**
	 * Check that HTTP response contains {@code "ETag"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/ETag">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/ETag</a>
	 */
	public AssertionResult hasETag(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ETAG.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "ETag"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/ETag">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/ETag</a>
	 */
	public AssertionResult doesNotHaveETag(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ETAG.getName());
	}

	/**
	 * Check that HTTP response contains {@code "ETag"} header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param etagValue ETag value.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/ETag">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/ETag</a>
	 */
	public AssertionResult isETagEqualTo(HttpResponse httpResponse, String etagValue) {
		return isHeaderEqualTo(httpResponse, ETAG.getName(), etagValue);
	}

	/**
	 * Check that HTTP response contains {@code "Content-Type"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult hasContentType(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_TYPE.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Content-Type"} header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentTypeValue Expected value.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isContentTypeEqualTo(HttpResponse httpResponse, String contentTypeValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_TYPE.getName(), contentTypeValue);
	}

	/**
	 * Check that HTTP response contains {@code "Content-Encoding"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding</a>
	 */
	public AssertionResult hasContentEncoding(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_ENCODING.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Content-Encoding"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding</a>
	 */
	public AssertionResult doesNotHaveContentEncoding(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CONTENT_ENCODING.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Content-Disposition"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6266">https://tools.ietf.org/html/rfc6266</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition</a>
	 */
	public AssertionResult hasContentDisposition(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_DISPOSITION.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Content-Disposition"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6266">https://tools.ietf.org/html/rfc6266</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition</a>
	 */
	public AssertionResult doesNotHaveContentDisposition(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CONTENT_DISPOSITION.getName());
	}

	/**
	 * Check that http response contains Content-Disposition header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentDispositionValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentDispositionEqualTo(HttpResponse httpResponse, String contentDispositionValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_DISPOSITION.getName(), contentDispositionValue);
	}

	/**
	 * Check that http response contains Content-Encoding header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentEncodingValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentEncodingEqualTo(HttpResponse httpResponse, String contentEncodingValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_ENCODING.getName(), contentEncodingValue);
	}

	/**
	 * Check that http response contains Content-Encoding header with
	 * gzip value (meaning that response body has been gzipped).
	 * This is a shortcut for checking that Content-Encoding is strictly equal
	 * to "gzip".
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult isGzipped(HttpResponse httpResponse) {
		return isHeaderEqualTo(httpResponse, CONTENT_ENCODING.getName(), "gzip");
	}

	/**
	 * Check that http response contains Content-Length header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentLength(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_LENGTH.getName());
	}

	/**
	 * Check that http response contains Location header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasLocation(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LOCATION.getName());
	}

	/**
	 * Check that http response does contains Location header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveLocation(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, LOCATION.getName());
	}

	/**
	 * Check that http response contains Location header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param locationValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLocationEqualTo(HttpResponse httpResponse, String locationValue) {
		return isHeaderEqualTo(httpResponse, LOCATION.getName(), locationValue);
	}

	/**
	 * Check that http response contains Last-Modified header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasLastModified(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LAST_MODIFIED.getName());
	}

	/**
	 * Check that http response does contains Last-Modifier header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveLastModified(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, LAST_MODIFIED.getName());
	}

	/**
	 * Check that http response contains Last-Modified header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasExpires(HttpResponse httpResponse) {
		return hasHeader(httpResponse, EXPIRES.getName());
	}

	/**
	 * Check that http response does contains Expires header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveExpires(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, EXPIRES.getName());
	}

	/**
	 * Check that http response contains Expires header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expiresValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isExpiresEqualTo(HttpResponse httpResponse, String expiresValue) {
		return isExpiresEqualTo(httpResponse, parseHttpDate(expiresValue));
	}

	/**
	 * Check that http response contains Expires header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expiresValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isExpiresEqualTo(HttpResponse httpResponse, Date expiresValue) {
		return assertWith(httpResponse, new IsDateHeaderEqualToAssertion(EXPIRES.getName(), expiresValue));
	}

	/**
	 * Check that http response contains Last-Modified header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param lastModifiedValue Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, String lastModifiedValue) {
		return isLastModifiedEqualTo(httpResponse, parseHttpDate(lastModifiedValue));
	}

	/**
	 * Check that http response contains Last-Modified header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param lastModifiedDate Expected value.
	 * @return Assertion result.
	 */
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, Date lastModifiedDate) {
		return assertWith(httpResponse, new IsDateHeaderEqualToAssertion(LAST_MODIFIED.getName(), lastModifiedDate));
	}

	/**
	 * Check that http response contains Pragma header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasPragma(HttpResponse httpResponse) {
		return hasHeader(httpResponse, PRAGMA.getName());
	}

	/**
	 * Check that http response does contains Pragma header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHavePragma(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, PRAGMA.getName());
	}

	/**
	 * Check that http response contains Pragma header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param pragma Pragma value.
	 * @return Assertion result.
	 */
	public AssertionResult isPragmaEqualTo(HttpResponse httpResponse, String pragma) {
		return isHeaderEqualTo(httpResponse, PRAGMA.getName(), pragma);
	}

	/**
	 * Check that http response contains Cache-Control header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasCacheControl(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CACHE_CONTROL.getName());
	}

	/**
	 * Check that http response does contains Cache-Control header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveCacheControl(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CACHE_CONTROL.getName());
	}

	/**
	 * Check that http response contains Cache-Control header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param cacheControl Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, String cacheControl) {
		return isHeaderEqualTo(httpResponse, CACHE_CONTROL.getName(), cacheControl);
	}

	/**
	 * Check that http response contains Cache-Control header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param cacheControl Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, CacheControl cacheControl) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CACHE_CONTROL.getName(), cacheControl, CacheControl.parser()));
	}

	/**
	 * Check that http response contains X-XSS-Protection header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasXssProtection(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_XSS_PROTECTION.getName());
	}

	/**
	 * Check that http response does contains X-XSS-Protection header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveXssProtection(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_XSS_PROTECTION.getName());
	}

	/**
	 * Check that http response contains X-XSS-Protection header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param xssProtection Expected X-XSS-Protection header value.
	 * @return Assertion result.
	 */
	public AssertionResult isXssProtectionEqualTo(HttpResponse httpResponse, String xssProtection) {
		return isHeaderEqualTo(httpResponse, X_XSS_PROTECTION.getName(), xssProtection);
	}

	/**
	 * Check that http response contains X-XSS-Protection header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param xssProtection Expected X-XSS-Protection header value.
	 * @return Assertion result.
	 */
	public AssertionResult isXssProtectionEqualTo(HttpResponse httpResponse, XssProtection xssProtection) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_XSS_PROTECTION.getName(), xssProtection, XssProtection.parser()));
	}

	/**
	 * Check that http response contains X-Content-Type-Options header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentTypeOptions(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_CONTENT_TYPE_OPTIONS.getName());
	}

	/**
	 * Check that http response does contains X-Content-Type-Options header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveContentTypeOptions(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_CONTENT_TYPE_OPTIONS.getName());
	}

	/**
	 * Check that http response contains X-Content-Type-Options header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentTypeOptions Expected X-Content-Type-Options header value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentTypeOptionsEqualTo(HttpResponse httpResponse, String contentTypeOptions) {
		return isHeaderEqualTo(httpResponse, X_CONTENT_TYPE_OPTIONS.getName(), contentTypeOptions);
	}

	/**
	 * Check that http response contains X-Content-Type-Options header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentTypeOptions Expected X-Content-Type-Options header value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentTypeOptionsEqualTo(HttpResponse httpResponse, ContentTypeOptions contentTypeOptions) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_CONTENT_TYPE_OPTIONS.getName(), contentTypeOptions, ContentTypeOptions.parser()));
	}

	/**
	 * Check that http response contains X-Frame-Options header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasFrameOptions(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_FRAME_OPTIONS.getName());
	}

	/**
	 * Check that http response does contains X-Frame-Options header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveFrameOptions(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_FRAME_OPTIONS.getName());
	}

	/**
	 * Check that http response contains X-Frame-Options header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param frameOptions Expected X-Frame-Options header value.
	 * @return Assertion result.
	 */
	public AssertionResult isFrameOptionsEqualTo(HttpResponse httpResponse, String frameOptions) {
		return isHeaderEqualTo(httpResponse, X_FRAME_OPTIONS.getName(), frameOptions);
	}

	/**
	 * Check that http response contains X-Frame-Options header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param frameOptions Expected X-Frame-Options header value.
	 * @return Assertion result.
	 */
	public AssertionResult isFrameOptionsEqualTo(HttpResponse httpResponse, FrameOptions frameOptions) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_FRAME_OPTIONS.getName(), frameOptions, FrameOptions.parser()));
	}

	/**
	 * Check that http response contains Content-Security-Policy header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasContentSecurityPolicy(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_SECURITY_POLICY.getName());
	}

	/**
	 * Check that http response does contains Content-Security-Policy header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveContentSecurityPolicy(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CONTENT_SECURITY_POLICY.getName());
	}

	/**
	 * Check that http response contains Content-Security-Policy header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentSecurityPolicy Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentSecurityPolicyControlEqualTo(HttpResponse httpResponse, String contentSecurityPolicy) {
		return isHeaderEqualTo(httpResponse, CONTENT_SECURITY_POLICY.getName(), contentSecurityPolicy);
	}

	/**
	 * Check that http response contains Content-Security-Policy header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentSecurityPolicy Cache-Control value.
	 * @return Assertion result.
	 */
	public AssertionResult isContentSecurityPolicyControlEqualTo(HttpResponse httpResponse, ContentSecurityPolicy contentSecurityPolicy) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CONTENT_SECURITY_POLICY.getName(), contentSecurityPolicy, ContentSecurityPolicy.parser()));
	}

	/**
	 * Check that http response contains Access-Control-Allow-Origin header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasAccessControlAllowOrigin(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName());
	}

	/**
	 * Check that http response does contains Access-Control-Allow-Origin header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveAccessControlAllowOrigin(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName());
	}

	/**
	 * Check that http response contains Access-Control-Allow-Origin header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param accessControlAllowOrigin Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowOriginEqualTo(HttpResponse httpResponse, String accessControlAllowOrigin) {
		return isHeaderEqualTo(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName(), accessControlAllowOrigin);
	}

	/**
	 * Check that http response contains Access-Control-Allow-Headers header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasAccessControlAllowHeaders(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_HEADERS.getName());
	}

	/**
	 * Check that http response does contains Access-Control-Allow-Headers header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveAccessControlAllowHeaders(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_HEADERS.getName());
	}

	/**
	 * Check that http response contains Access-Control-Allow-Headers header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param value Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowHeadersEqualTo(HttpResponse httpResponse, String value, String... other) {
		List<String> list = new LinkedList<>();
		list.add(value);
		addAll(list, other);
		return isAccessControlAllowHeadersEqualTo(httpResponse, list);
	}

	/**
	 * Check that http response contains Access-Control-Allow-Headers header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param accessControlAllowHeaders Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowHeadersEqualTo(HttpResponse httpResponse, Iterable<String> accessControlAllowHeaders) {
		return assertWith(httpResponse, new IsHeaderListEqualToAssertion(ACCESS_CONTROL_ALLOW_HEADERS.getName(), accessControlAllowHeaders));
	}

	/**
	 * Check that http response contains Access-Control-Expose-Headers header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasAccessControlExposeHeaders(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_EXPOSE_HEADERS.getName());
	}

	/**
	 * Check that http response does contains Access-Control-Expose-Headers header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveAccessControlExposeHeaders(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_EXPOSE_HEADERS.getName());
	}

	/**
	 * Check that http response contains Access-Control-Expose-Headers header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param value Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlExposeHeadersEqualTo(HttpResponse httpResponse, String value, String... other) {
		List<String> list = new LinkedList<>();
		list.add(value);
		addAll(list, other);
		return isAccessControlExposeHeadersEqualTo(httpResponse, list);
	}

	/**
	 * Check that http response contains Access-Control-Allow-Headers header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param accessControlExposeHeaders Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlExposeHeadersEqualTo(HttpResponse httpResponse, Iterable<String> accessControlExposeHeaders) {
		return assertWith(httpResponse, new IsHeaderListEqualToAssertion(ACCESS_CONTROL_EXPOSE_HEADERS.getName(), accessControlExposeHeaders));
	}

	/**
	 * Check that http response contains Access-Control-Allow-Methods header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasAccessControlAllowMethods(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_METHODS.getName());
	}

	/**
	 * Check that http response does contains Access-Control-Allow-Headers header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveAccessControlAllowMethods(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_METHODS.getName());
	}

	/**
	 * Check that http response contains Access-Control-Allow-Headers header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param method HTTP method.
	 * @param other Other, optional, HTTP method.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowMethodsEqualTo(HttpResponse httpResponse, RequestMethod method, RequestMethod... other) {
		List<RequestMethod> list = new LinkedList<>();
		list.add(method);
		addAll(list, other);
		return isAccessControlAllowMethodsEqualTo(httpResponse, list);
	}

	/**
	 * Check that http response contains Access-Control-Allow-Methods header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param methods HTTP Methods.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowMethodsEqualTo(HttpResponse httpResponse, Iterable<RequestMethod> methods) {
		List<String> list = new LinkedList<>();
		for (RequestMethod method : methods) {
			list.add(method.verb());
		}

		return assertWith(httpResponse, new IsHeaderListEqualToAssertion(ACCESS_CONTROL_ALLOW_METHODS.getName(), list));
	}

	/**
	 * Check that http response contains Access-Control-Allow-Credentials header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasAccessControlAllowCredentials(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_CREDENTIALS.getName());
	}

	/**
	 * Check that http response does contains Access-Control-Allow-Credentials header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveAccessControlAllowCredentials(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_CREDENTIALS.getName());
	}

	/**
	 * Check that http response contains Access-Control-Allow-Credentials header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param flag Flag value.
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowCredentialsEqualTo(HttpResponse httpResponse, boolean flag) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(ACCESS_CONTROL_ALLOW_CREDENTIALS.getName(), Boolean.toString(flag)));
	}

	/**
	 * Check that http response contains Access-Control-Allow-Max-Age header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasAccessControlAllowMaxAge(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_MAX_AGE.getName());
	}

	/**
	 * Check that http response does contains Access-Control-Allow-Max-Age header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveAccessControlAllowMaxAge(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_MAX_AGE.getName());
	}

	/**
	 * Check that http response contains Access-Control-Allow-Max-Age header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param maxAge Max age (in seconds).
	 * @return Assertion result.
	 */
	public AssertionResult isAccessControlAllowMaxAgeEqualTo(HttpResponse httpResponse, long maxAge) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(ACCESS_CONTROL_ALLOW_MAX_AGE.getName(), Long.toString(maxAge)));
	}

	/**
	 * Check that http response contains Strict-Transport-Security header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult hasStrictTransportSecurity(HttpResponse httpResponse) {
		return hasHeader(httpResponse, STRICT_TRANSPORT_SECURITY.getName());
	}

	/**
	 * Check that http response does contains Strict-Transport-Security header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveStrictTransportSecurity(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, STRICT_TRANSPORT_SECURITY.getName());
	}

	/**
	 * Check that http response contains Strict-Transport-Security header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param strictTransportSecurity Strict-Transport-Security value.
	 * @return Assertion result.
	 */
	public AssertionResult isStrictTransportSecurityEqualTo(HttpResponse httpResponse, String strictTransportSecurity) {
		return isHeaderEqualTo(httpResponse, STRICT_TRANSPORT_SECURITY.getName(), strictTransportSecurity);
	}

	/**
	 * Check that http response contains Strict-Transport-Security header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param strictTransportSecurity Strict-Transport-Security value.
	 * @return Assertion result.
	 */
	public AssertionResult isStrictTransportSecurityEqualTo(HttpResponse httpResponse, StrictTransportSecurity strictTransportSecurity) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(STRICT_TRANSPORT_SECURITY.getName(), strictTransportSecurity, StrictTransportSecurity.parser()));
	}

	/**
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "application/json").
	 *
	 * The mime type of an HTTP response is specified in the {@code "Content-Type"} header, and is
	 * case-insensitive.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isJson(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, JSON);
	}

	/**
	 * Check that HTTP response has a mime-type corresponding to an XML document, meaning one of the following
	 * values (ignoring case):
	 *
	 * <ul>
	 *   <li>{@code "application/xml"}</li>
	 *   <li>{@code "text/xml"}</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7303#page-19">https://tools.ietf.org/html/rfc7303#page-19</a>
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isXml(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(APPLICATION_XML, TEXT_XML));
	}

	/**
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "text/css").
	 *
	 * The mime type of an HTTP response is specified in the {@code "Content-Type"} header, and is
	 * case-insensitive.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isCss(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, CSS);
	}

	/**
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "text/plain").
	 *
	 * The mime type of an HTTP response is specified in the {@code "Content-Type"} header, and is
	 * case-insensitive.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isText(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, TEXT_PLAIN);
	}

	/**
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "text/csv").
	 *
	 * The mime type of an HTTP response is specified in the {@code "Content-Type"} header, and is
	 * case-insensitive.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isCsv(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, CSV);
	}

	/**
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "application/pdf").
	 *
	 * The mime type of an HTTP response is specified in the {@code "Content-Type"} header, and is
	 * case-insensitive.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isPdf(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, PDF);
	}

	/**
	 * Check that HTTP response has a mime-type corresponding to an HTML document, meaning one of
	 * the following values (ignoring case):
	 *
	 * <ul>
	 *   <li>{@code "text/html"}</li>
	 *   <li>{@code "application/xhtml+xml"}</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isHtml(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(TEXT_HTML, XHTML));
	}

	/**
	 * Check that http response has UTF-8 charset.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult isUtf8(HttpResponse httpResponse) {
		return hasCharset(httpResponse, "UTF-8");
	}

	/**
	 * Check that http response is "application/javascript" or "text/javascript".
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult isJavascript(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(
				APPLICATION_JAVASCRIPT,
				TEXT_JAVASCRIPT
		));
	}

	/**
	 * Check that HTTP response contains expected header (note that header name are case-insensitive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param headerName Header name.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers</a>
	 */
	public AssertionResult hasHeader(HttpResponse httpResponse, String headerName) {
		return assertWith(httpResponse, new HasHeaderAssertion(headerName));
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains expected header (note that header name are
	 * case-insensitive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param headerName Header name.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers</a>
	 */
	public AssertionResult doesNotHaveHeader(HttpResponse httpResponse, String headerName) {
		return assertWith(httpResponse, new DoesNotHaveHeaderAssertion(headerName));
	}

	/**
	 * Check that http response is expected mime type.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 */
	public AssertionResult hasMimeType(HttpResponse httpResponse, String expectedMimeType) {
		return assertWith(httpResponse, new HasMimeTypeAssertion(expectedMimeType));
	}

	/**
	 * Check that http response has expected charset.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expectedCharset Expected charset.
	 * @return Assertion result.
	 */
	public AssertionResult hasCharset(HttpResponse httpResponse, Charset expectedCharset) {
		return hasCharset(httpResponse, expectedCharset.name());
	}

	/**
	 * Check that http response has expected charset.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expectedCharset Expected charset.
	 * @return Assertion result.
	 */
	public AssertionResult hasCharset(HttpResponse httpResponse, String expectedCharset) {
		return assertWith(httpResponse, new HasCharsetAssertion(expectedCharset));
	}

	/**
	 * Check that http response is expected mime type.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 */
	public AssertionResult hasMimeTypeIn(HttpResponse httpResponse, Iterable<String> expectedMimeType) {
		return assertWith(httpResponse, new HasMimeTypeAssertion(expectedMimeType));
	}

	/**
	 * Check that http response contains expected header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param headerName Header name.
	 * @param headerValue Header value.
	 * @return Assertion result.
	 */
	public AssertionResult isHeaderEqualTo(HttpResponse httpResponse, String headerName, String headerValue) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(headerName, headerValue));
	}

	/**
	 * Check that status code of http response has an expected status.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param status Expected status.
	 * @return Assertion result.
	 */
	public AssertionResult isStatusEqual(HttpResponse httpResponse, int status) {
		return assertWith(httpResponse, new StatusEqualAssertion(status));
	}

	/**
	 * Check that status code of http response is include between
	 * a lower bound and an upper bound (inclusive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param start Lower bound.
	 * @param end Upper bound.
	 * @return Assertion result.
	 */
	public AssertionResult isStatusBetween(HttpResponse httpResponse, int start, int end) {
		return assertWith(httpResponse, new StatusBetweenAssertion(start, end));
	}

	/**
	 * Check that status code of http response is not included between
	 * a lower bound and an upper bound (inclusive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param start Lower bound.
	 * @param end Upper bound.
	 * @return Assertion result.
	 */
	public AssertionResult isStatusOutOf(HttpResponse httpResponse, int start, int end) {
		return assertWith(httpResponse, new StatusOutOfAssertion(start, end));
	}

	/**
	 * Check that http response does not contains any cookies.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveCookie(HttpResponse httpResponse) {
		return assertWith(httpResponse, new DoesNotHaveCookieAssertion());
	}

	/**
	 * Check that http response does not contains cookie with given name (note that cookie name
	 * is case-sensitive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param name Cookie name.
	 * @return Assertion result.
	 */
	public AssertionResult doesNotHaveCookie(HttpResponse httpResponse, String name) {
		return assertWith(httpResponse, new DoesNotHaveCookieAssertion(name));
	}

	/**
	 * Check that http response contains cookie with given name (note that cookie name is
	 * case-sensitive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param name Cookie name.
	 * @return Assertion result.
	 */
	public AssertionResult hasCookie(HttpResponse httpResponse, String name) {
		return assertWith(httpResponse, new HasCookieAssertion(name));
	}

	/**
	 * Check that http response contains cookie with given name and value (note that cookie name
	 * is case-sensitive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param name Cookie name.
	 * @param value Cookie value.
	 * @return Assertion result.
	 */
	public AssertionResult hasCookie(HttpResponse httpResponse, String name, String value) {
		return assertWith(httpResponse, new HasCookieAssertion(name, value));
	}

	/**
	 * Check that HTTP response contains given cookie.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param cookie Cookie.
	 * @return Assertion result.
	 */
	public AssertionResult hasCookie(HttpResponse httpResponse, Cookie cookie) {
		return assertWith(httpResponse, new HasCookieAssertion(cookie));
	}

	private AssertionResult assertWith(HttpResponse httpResponse, Assertion<HttpResponse> assertion) {
		return assertion.handle(httpResponse);
	}
}
