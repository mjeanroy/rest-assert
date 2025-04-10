/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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
import com.github.mjeanroy.restassert.core.data.ContentEncoding;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy;
import com.github.mjeanroy.restassert.core.data.ContentType;
import com.github.mjeanroy.restassert.core.data.ContentTypeOptions;
import com.github.mjeanroy.restassert.core.data.Cookie;
import com.github.mjeanroy.restassert.core.data.FrameOptions;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.core.data.MediaType;
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
import com.github.mjeanroy.restassert.core.internal.data.HttpStatusCodes;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.Collections.toList;
import static com.github.mjeanroy.restassert.core.internal.common.Dates.parseHttpDate;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.ACCESS_CONTROL_ALLOW_MAX_AGE;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.CACHE_CONTROL;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.CONTENT_DISPOSITION;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.CONTENT_ENCODING;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.CONTENT_LENGTH;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.CONTENT_SECURITY_POLICY;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.CONTENT_TYPE;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.ETAG;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.EXPIRES;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.LAST_MODIFIED;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.LOCATION;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.PRAGMA;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.STRICT_TRANSPORT_SECURITY;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.X_CONTENT_TYPE_OPTIONS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.X_FRAME_OPTIONS;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.X_XSS_PROTECTION;
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
import static com.github.mjeanroy.restassert.core.internal.error.common.ShouldNotBeNull.shouldNotBeNull;
import static java.util.Arrays.asList;
import static java.util.stream.StreamSupport.stream;

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
	private static final Function<String, String[]> STRING_SPLIT = (input) -> input.split(",");

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
	 * Check that status code of http response is {@code "MULTIPLE CHOICES"} status (i.e is strictly equals to {@code 300}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.1">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.1</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/300">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/300</a>
	 * @see <a href="https://httpstatuses.com/300">https://httpstatuses.com/300</a>
	 */
	public AssertionResult isMultipleChoices(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.MULTIPLE_CHOICES);
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
	 * Check that status code of http response is {@code "SEE OTHER"} status (i.e is strictly equals to {@code 303}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.4">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/303">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/303</a>
	 * @see <a href="https://httpstatuses.com/303">https://httpstatuses.com/304</a>
	 */
	public AssertionResult isSeeOther(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.SEE_OTHER);
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
	 * Check that status code of http response is {@code "TEMPORARY REDIRECT"} status (i.e is strictly equals to {@code 307}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.8">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.8</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/307">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/307</a>
	 * @see <a href="https://httpstatuses.com/307">https://httpstatuses.com/307</a>
	 */
	public AssertionResult isTemporaryRedirect(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.TEMPORARY_REDIRECT);
	}

	/**
	 * Check that status code of http response is {@code "PERMANENT REDIRECT"} status (i.e is strictly equals to {@code 308}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7538">https://tools.ietf.org/html/rfc7538</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/308">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/308</a>
	 * @see <a href="https://httpstatuses.com/308">https://httpstatuses.com/308</a>
	 */
	public AssertionResult isPermanentRedirect(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.PERMANENT_REDIRECT);
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
	 * Check that status code of http response is {@code "GONE"} status (i.e is strictly equals to {@code 410}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/411">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/411</a>
	 * @see <a href="https://httpstatuses.com/411">https://httpstatuses.com/411</a>
	 */
	public AssertionResult isGone(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.GONE);
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
	 * Check that status code of http response is {@code "REQUEST ENTITY TOO LARGE"} status (i.e is strictly equals to {@code 413}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/413">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/413</a>
	 * @see <a href="https://httpstatuses.com/413">https://httpstatuses.com/413</a>
	 */
	public AssertionResult isRequestEntityTooLarge(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.REQUEST_ENTITY_TOO_LARGE);
	}

	/**
	 * Check that status code of http response is {@code "REQUEST URI TOO LONG"} status (i.e is strictly equals to {@code 414}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/414">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/414</a>
	 * @see <a href="https://httpstatuses.com/414">https://httpstatuses.com/414</a>
	 */
	public AssertionResult isRequestUriTooLong(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.REQUEST_URI_TOO_LONG);
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
	 * Check that status code of http response is {@code "REQUEST TIMEOUT"} status (i.e is strictly equals to {@code 408}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/409">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/409</a>
	 * @see <a href="https://httpstatuses.com/409">https://httpstatuses.com/409</a>
	 */
	public AssertionResult isRequestTimeout(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.REQUEST_TIMEOUT);
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
	 * Check that status code of http response is {@code "REQUESTED RANGE NOT SATISFIABLE"} status (i.e is strictly equals to {@code 416}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/416">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/416</a>
	 * @see <a href="https://httpstatuses.com/416">https://httpstatuses.com/416</a>
	 */
	public AssertionResult isRequestedRangeNotSatisfiable(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.REQUESTED_RANGE_NOT_SATISFIABLE);
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
	 * Check that status code of HTTP response is strictly equals to an expected status.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param status Expected status.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://httpstatuses.com">https://httpstatuses.com</a>
	 */
	public AssertionResult isStatusEqual(HttpResponse httpResponse, int status) {
		return assertWith(httpResponse, new StatusEqualAssertion(status));
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
	 * Check that status code of HTTP response is included between a lower bound and
	 * an upper bound (inclusive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param start Lower bound.
	 * @param end Upper bound.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors</a>
	 */
	public AssertionResult isStatusBetween(HttpResponse httpResponse, int start, int end) {
		return assertWith(httpResponse, new StatusBetweenAssertion(start, end));
	}

	/**
	 * Check that status code of HTTP response is not included between a lower bound and
	 * an upper bound (inclusive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param start Lower bound.
	 * @param end Upper bound.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4">https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">https://developer.mozilla.org/en-US/docs/Web/HTTP/Status</a>
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors">https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors</a>
	 */
	public AssertionResult isStatusOutOf(HttpResponse httpResponse, int start, int end) {
		return assertWith(httpResponse, new StatusOutOfAssertion(start, end));
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
	 * @param etagValue Expected {@code "ETag"} value.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/ETag">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/ETag</a>
	 */
	public AssertionResult isETagEqualTo(HttpResponse httpResponse, String etagValue) {
		return isHeaderEqualTo(httpResponse, ETAG.getName(), etagValue, false);
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
	 * According to the specification, comparison will be case-insensitive, and charset options may be
	 * quoted or not. So following values are all equivalent:
	 *
	 * <ul>
	 *   <li>Content-Type: application/json; charset=utf-8</li>
	 *   <li>Content-Type: APPLICATION/JSON; charset=UTF-8</li>
	 *   <li>Content-Type: application/json; charset='utf-8'</li>
	 *   <li>Content-Type: application/json; charset="utf-8"</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentTypeValue Expected {@code "Content-Type} value.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isContentTypeEqualTo(HttpResponse httpResponse, String contentTypeValue) {
		return isContentTypeEqualTo(httpResponse, ContentType.parser().parse(contentTypeValue));
	}

	/**
	 * Check that HTTP response contains {@code "Content-Type"} header with expected value.
	 *
	 * Here is an example to create a {@code "Content-Type"} using the {@link ContentType} factories:
	 *
	 * <pre><code>
	 *   // Produce "application/json; charset=utf-8" header.
	 *   public ContentType getApplicationJson() {
	 *     return ContentType.contentType(
	 *       MediaType.application("json"),
	 *       StandardCharsets.UTF_8
	 *     );
	 *   }
	 * </code></pre>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentTypeValue Expected {@code "Content-Type} value.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isContentTypeEqualTo(HttpResponse httpResponse, ContentType contentTypeValue) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CONTENT_TYPE.getName(), contentTypeValue, ContentType.parser()));
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
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding</a>
	 */
	public AssertionResult doesNotHaveContentEncoding(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CONTENT_ENCODING.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Content-Encoding"} header with
	 * expected value.
	 *
	 * Note that according to the specification, comparison is <strong>case-insensitive</strong>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentEncodingValue Expected {@code "Content-Encoding} value.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding</a>
	 */
	public AssertionResult isContentEncodingEqualTo(HttpResponse httpResponse, String contentEncodingValue) {
		ContentEncoding contentEncoding = ContentEncoding.parser().parse(contentEncodingValue);
		return isContentEncodingEqualTo(httpResponse, contentEncoding);
	}

	/**
	 * Check that HTTP response contains {@code "Content-Encoding"} header with
	 * expected value.
	 *
	 * Note that according to the specification, comparison is <strong>case-insensitive</strong>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentEncoding Expected {@code "Content-Encoding} value.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding</a>
	 */
	public AssertionResult isContentEncodingEqualTo(HttpResponse httpResponse, ContentEncoding contentEncoding) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CONTENT_ENCODING.getName(), contentEncoding, ContentEncoding.parser()));
	}

	/**
	 * Check that http response contains {@code "Content-Encoding"} header with {@code "gzip"}
	 * value (meaning that response body has been gzipped).
	 *
	 * This is a shortcut for checking that {@code "Content-Encoding"} is equal to {@code "gzip"}.
	 *
	 * Note that according to specification, comparison is <strong>case-insensitive</strong> (so {@code "gzip"}
	 * or {@code "GZIP"} is equivalent).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11</a>
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5">https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.5</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding</a>
	 */
	public AssertionResult isGzipped(HttpResponse httpResponse) {
		return isContentEncodingEqualTo(httpResponse, ContentEncoding.gzip());
	}

	/**
	 * Check that HTTP response contains {@code "Content-Length"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length</a>
	 * @see <a href="https://tools.ietf.org/html/rfc7230#section-3.3.2">https://tools.ietf.org/html/rfc7230#section-3.3.2</a>
	 */
	public AssertionResult hasContentLength(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_LENGTH.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Location"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.2">https://tools.ietf.org/html/rfc7231#section-7.1.2v</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Location">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Location</a>
	 */
	public AssertionResult hasLocation(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LOCATION.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Location"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.2">https://tools.ietf.org/html/rfc7231#section-7.1.2v</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Location">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Location</a>
	 */
	public AssertionResult doesNotHaveLocation(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, LOCATION.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Location"} header with
	 * expected value.
	 *
	 * Note that, even if URI values should be case insensitive, comparison is <strong>case sensitive</strong> (so
	 * URI {@code "https://google.com"} is not equivalent to {@code "HTTPS://GOOGLE.COM"}).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param locationValue Expected {@code "Location"} value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.2">https://tools.ietf.org/html/rfc7231#section-7.1.2v</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Location">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Location</a>
	 */
	public AssertionResult isLocationEqualTo(HttpResponse httpResponse, String locationValue) {
		return isHeaderEqualTo(httpResponse, LOCATION.getName(), locationValue, false);
	}

	/**
	 * Check that HTTP response contains {@code "Last-Modified"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.2">https://tools.ietf.org/html/rfc7232#section-2.2</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Last-Modified">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Last-Modified</a>
	 */
	public AssertionResult hasLastModified(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LAST_MODIFIED.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Last-Modified"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.2">https://tools.ietf.org/html/rfc7232#section-2.2</a>
	 * @see <a href="https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Last-Modified">https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/Last-Modified</a>
	 */
	public AssertionResult doesNotHaveLastModified(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, LAST_MODIFIED.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Last-Modified"} header with
	 * expected value.
	 *
	 * Three date-time format are supported, according to the <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">specification</a>:
	 *
	 * <ul>
	 *   <li>The (preferred) format defined by RFC 5322, for example: {@code "Sun, 06 Nov 1994 08:49:37 GMT"}.</li>
	 *   <li>The (obsolete) format defined by RFC 850, for example: {@code "Sunday, 06-Nov-94 08:49:37 GMT"}.</li>
	 *   <li>The (obsolete) ANSI C's asctime() format, for example: {@code "Sun Nov  6 08:49:37 1994"}.</li>
	 * </ul>
	 *
	 * Note that, unless specific reason, first format defined by RFC 5322 should be used.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param lastModifiedValue Expected {@code "Last-Modified"} value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.2">https://tools.ietf.org/html/rfc7232#section-2.2</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Last-Modified">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Last-Modified</a>
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">https://tools.ietf.org/html/rfc7231#section-7.1.1.1</a>
	 * @see <a href="https://tools.ietf.org/html/rfc5322#section-3.3">https://tools.ietf.org/html/rfc5322#section-3.3</a>
	 * @see <a href="https://tools.ietf.org/html/rfc850">https://tools.ietf.org/html/rfc850</a>
	 */
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, String lastModifiedValue) {
		return isLastModifiedEqualTo(httpResponse, parseHttpDate(lastModifiedValue));
	}

	/**
	 * Check that HTTP response contains {@code "Last-Modified"} header with
	 * expected {@link Date} value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param lastModifiedDate Expected {@code "Last-Modified"} value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.2">https://tools.ietf.org/html/rfc7232#section-2.2</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Last-Modified">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Last-Modified</a>
	 */
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, Date lastModifiedDate) {
		return assertWith(httpResponse, new IsDateHeaderEqualToAssertion(LAST_MODIFIED.getName(), lastModifiedDate));
	}

	/**
	 * Check that HTTP response contains {@code "Expires"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.3">https://tools.ietf.org/html/rfc7234#section-5.3</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires</a>
	 */
	public AssertionResult hasExpires(HttpResponse httpResponse) {
		return hasHeader(httpResponse, EXPIRES.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Expires"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.3">https://tools.ietf.org/html/rfc7234#section-5.3</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires</a>
	 */
	public AssertionResult doesNotHaveExpires(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, EXPIRES.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Expires"} header with
	 * expected value.
	 *
	 * Three date-time format are supported (for {@code expiresValue} parameter or for header
	 * value), according to the <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">specification</a>:
	 *
	 * <ul>
	 *   <li>The (preferred) format defined by RFC 5322, for example: {@code "Sun, 06 Nov 1994 08:49:37 GMT"}.</li>
	 *   <li>The (obsolete) format defined by RFC 850, for example: {@code "Sunday, 06-Nov-94 08:49:37 GMT"}.</li>
	 *   <li>The (obsolete) ANSI C's asctime() format, for example: {@code "Sun Nov  6 08:49:37 1994"}.</li>
	 * </ul>
	 *
	 * Note that:
	 *
	 * <ul>
	 *   <li>Unless specific reason, the first (preferred) format should be used.</li>
	 *   <li>This is not a "strict" comparison: {@code expiresValue} parameter and header value does not need to use the same pattern.</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expiresValue Expected {@code "Expires"} value.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires</a>
	 * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.3">https://tools.ietf.org/html/rfc7234#section-5.3</a>
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">https://tools.ietf.org/html/rfc7231#section-7.1.1.1</a>
	 * @see <a href="https://tools.ietf.org/html/rfc5322#section-3.3">https://tools.ietf.org/html/rfc5322#section-3.3</a>
	 * @see <a href="https://tools.ietf.org/html/rfc850">https://tools.ietf.org/html/rfc850</a>
	 */
	public AssertionResult isExpiresEqualTo(HttpResponse httpResponse, String expiresValue) {
		return isExpiresEqualTo(httpResponse, parseHttpDate(expiresValue));
	}

	/**
	 * Check that HTTP response contains {@code "Expires"} header with
	 * expected {@link Date} value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expiresValue Expected {@code "Expires"} value.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires</a>
	 * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.3">https://tools.ietf.org/html/rfc7234#section-5.3</a>
	 */
	public AssertionResult isExpiresEqualTo(HttpResponse httpResponse, Date expiresValue) {
		return assertWith(httpResponse, new IsDateHeaderEqualToAssertion(EXPIRES.getName(), expiresValue));
	}

	/**
	 * Check that HTTP response contains {@code "Cache-Control"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7234#page-21">https://tools.ietf.org/html/rfc7234#page-21</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control</a>
	 */
	public AssertionResult hasCacheControl(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CACHE_CONTROL.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Cache-Control"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7234#page-21">https://tools.ietf.org/html/rfc7234#page-21</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control</a>
	 */
	public AssertionResult doesNotHaveCacheControl(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CACHE_CONTROL.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Cache-Control"} header with expected value.
	 *
	 * Note that, according to the <a href="https://tools.ietf.org/html/rfc7234#page-21">specification</a>:
	 *
	 * <ul>
	 *   <li>Comparison is <strong>case-insensitive</strong>.</li>
	 *   <li>Header directives does not need to be in the same order</li>
	 * </ul>
	 *
	 * For example, following values are equivalent:
	 *
	 * <ul>
	 *   <li>{@code "no-cache, no-store, must-revalidate"}</li>
	 *   <li>{@code "NO-CACHE, NO-STORE, MUST-REVALIDATE"}</li>
	 *   <li>{@code "no-cache, must-revalidate, no-store"}</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param cacheControl Expected {@code "Cache-Control"} value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7234#page-21">https://tools.ietf.org/html/rfc7234#page-21</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control</a>
	 */
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, String cacheControl) {
		return isCacheControlEqualTo(httpResponse, CacheControl.parser().parse(cacheControl));
	}

	/**
	 * Check that HTTP response contains {@code "Cache-Control"} header with expected value.
	 *
	 * You can build {@link CacheControl} using {@link com.github.mjeanroy.restassert.core.data.CacheControlBuilder} to get
	 * a fluent API, for example:
	 *
	 * <pre><code>
	 *   private CacheControl createHeader() {
	 *     return CacheControl.builder()
	 *       .visibility(Visibility.PUBLIC)
	 *       .noTransform()
	 *       .noStore()
	 *       .build();
	 *   }
	 * </code></pre>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param cacheControl Expected {@code "Cache-Control"} value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7234#page-21">https://tools.ietf.org/html/rfc7234#page-21</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control</a>
	 */
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, CacheControl cacheControl) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CACHE_CONTROL.getName(), cacheControl, CacheControl.parser()));
	}

	/**
	 * Check that HTTP response contains {@code "X-Frame-Options"} header, no matter what values.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7034">https://tools.ietf.org/html/rfc7034</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options</a>
	 */
	public AssertionResult hasFrameOptions(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_FRAME_OPTIONS.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "X-Frame-Options"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7034">https://tools.ietf.org/html/rfc7034</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options</a>
	 */
	public AssertionResult doesNotHaveFrameOptions(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_FRAME_OPTIONS.getName());
	}

	/**
	 * Check that HTTP response contains {@code "X-Frame-Options"} header with
	 * expected value.
	 *
	 * Note that, according to <a href="https://tools.ietf.org/html/rfc7034">specification</a>:
	 *
	 * <ul>
	 *   <li>Header value is <strong>case-insensitive</strong>, so {@code "deny"} or {@code "DENY"} is equivalent.</li>
	 *   <li>Using a different value than {@code "DENY"}, {@code "SAMEORIGIN"} or {@code "ALLOW-FROM"} is not permitted, so test will fail as it is probably a mistake.</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param frameOptions Expected {@code "X-Frame-Options"} header value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7034">https://tools.ietf.org/html/rfc7034</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options</a>
	 */
	public AssertionResult isFrameOptionsEqualTo(HttpResponse httpResponse, String frameOptions) {
		return isFrameOptionsEqualTo(httpResponse, FrameOptions.parser().parse(frameOptions));
	}

	/**
	 * Check that HTTP response contains {@code "X-Frame-Options"} header with
	 * expected {@link FrameOptions} value.
	 *
	 * You can build {@link FrameOptions} using one of the static factory available:
	 *
	 * <pre><code>
	 *   private FrameOptions createDeny() {
	 *     return FrameOptions.deny();
	 *   }
	 *
	 *   private FrameOptions createSameOrigin() {
	 *     return FrameOptions.sameOrigin();
	 *   }
	 *
	 *   private FrameOptions createAllowFrom() {
	 *     return FrameOptions.allowFrom("https://example.com");
	 *   }
	 * </code></pre>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param frameOptions Expected {@code "X-Frame-Options"} header value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7034">https://tools.ietf.org/html/rfc7034</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options</a>
	 */
	public AssertionResult isFrameOptionsEqualTo(HttpResponse httpResponse, FrameOptions frameOptions) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_FRAME_OPTIONS.getName(), frameOptions, FrameOptions.parser()));
	}

	/**
	 * Check that HTTP response contains {@code "X-Content-Type-Options"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#x-content-type-options-header">https://fetch.spec.whatwg.org/#x-content-type-options-header</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options</a>
	 */
	public AssertionResult hasContentTypeOptions(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_CONTENT_TYPE_OPTIONS.getName());
	}

	/**
	 * Check that http response <strong>does not</strong> contains {@code "X-Content-Type-Options"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#x-content-type-options-header">https://fetch.spec.whatwg.org/#x-content-type-options-header</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options</a>
	 */
	public AssertionResult doesNotHaveContentTypeOptions(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_CONTENT_TYPE_OPTIONS.getName());
	}

	/**
	 * Check that HTTP response contains {@code "X-Content-Type-Options"} header with
	 * expected value.
	 *
	 * Note that, according to the <a href="https://fetch.spec.whatwg.org/#x-content-type-options-header">specification</a>:
	 *
	 * <ul>
	 *   <li>Header value is <strong>case-insensitive</strong>, so {@code "nosniff"} or {@code "NOSNIFF"} are equivalent.</li>
	 *   <li>Non authorized value (currently, only {@code "nosniff"} is allowed) will fail since it is probably a mistake.</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentTypeOptions Expected {@code "X-Content-Type-Options"} header value.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#x-content-type-options-header">https://fetch.spec.whatwg.org/#x-content-type-options-header</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options</a>
	 */
	public AssertionResult isContentTypeOptionsEqualTo(HttpResponse httpResponse, String contentTypeOptions) {
		return isContentTypeOptionsEqualTo(httpResponse, ContentTypeOptions.parser().parse(contentTypeOptions));
	}

	/**
	 * Check that HTTP response contains {@code "X-Content-Type-Options"} header with
	 * expected {@link ContentTypeOptions} value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentTypeOptions Expected {@code "X-Content-Type-Options"} header value.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#x-content-type-options-header">https://fetch.spec.whatwg.org/#x-content-type-options-header</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options</a>
	 */
	public AssertionResult isContentTypeOptionsEqualTo(HttpResponse httpResponse, ContentTypeOptions contentTypeOptions) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_CONTENT_TYPE_OPTIONS.getName(), contentTypeOptions, ContentTypeOptions.parser()));
	}

	/**
	 * Check that HTTP response contains {@code "Content-Security-Policy"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://w3c.github.io/webappsec-csp/">https://w3c.github.io/webappsec-csp/</a>
	 * @see <a href="https://w3c.github.io/webappsec-csp/2/">https://w3c.github.io/webappsec-csp/2/</a>
	 * @see <a href="https://www.w3.org/TR/CSP1/">https://www.w3.org/TR/CSP1/</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy</a>
	 */
	public AssertionResult hasContentSecurityPolicy(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_SECURITY_POLICY.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Content-Security-Policy"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://w3c.github.io/webappsec-csp/">https://w3c.github.io/webappsec-csp/</a>
	 * @see <a href="https://w3c.github.io/webappsec-csp/2/">https://w3c.github.io/webappsec-csp/2/</a>
	 * @see <a href="https://www.w3.org/TR/CSP1/">https://www.w3.org/TR/CSP1/</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy</a>
	 */
	public AssertionResult doesNotHaveContentSecurityPolicy(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CONTENT_SECURITY_POLICY.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Content-Security-Policy"} header with
	 * expected value.
	 *
	 * Note that according to latest <a href="https://w3c.github.io/webappsec-csp/">specification</a>:
	 *
	 * <ul>
	 *   <li>Directive name are <strong>case-insensitive</strong>.</li>
	 *   <li>Directives order does not matter.</li>
	 * </ul>
	 *
	 * So for example, following values are equivalent:
	 *
	 * <ul>
	 *   <li>{@code "default-src 'none'; script-src 'self' 'unsafe-inline'}</li>
	 *   <li>{@code "default-src 'none'; script-src 'unsafe-inline' 'self'}</li>
	 *   <li>{@code "script-src 'self' 'unsafe-inline'; default-src 'none';}</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentSecurityPolicy Cache-Control value.
	 * @return Assertion result.
	 * @see <a href="https://w3c.github.io/webappsec-csp/">https://w3c.github.io/webappsec-csp/</a>
	 * @see <a href="https://w3c.github.io/webappsec-csp/2/">https://w3c.github.io/webappsec-csp/2/</a>
	 * @see <a href="https://www.w3.org/TR/CSP1/">https://www.w3.org/TR/CSP1/</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy</a>
	 */
	public AssertionResult isContentSecurityPolicyEqualTo(HttpResponse httpResponse, String contentSecurityPolicy) {
		ContentSecurityPolicy csp = ContentSecurityPolicy.parser().parse(contentSecurityPolicy);
		return isContentSecurityPolicyEqualTo(httpResponse, csp);
	}

	/**
	 * Check that HTTP response contains {@code "Content-Security-Policy"} header with
	 * expected {@link ContentSecurityPolicy} value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentSecurityPolicy Cache-Control value.
	 * @return Assertion result.
	 * @see <a href="https://w3c.github.io/webappsec-csp/">https://w3c.github.io/webappsec-csp/</a>
	 * @see <a href="https://w3c.github.io/webappsec-csp/2/">https://w3c.github.io/webappsec-csp/2/</a>
	 * @see <a href="https://www.w3.org/TR/CSP1/">https://www.w3.org/TR/CSP1/</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy</a>
	 */
	public AssertionResult isContentSecurityPolicyEqualTo(HttpResponse httpResponse, ContentSecurityPolicy contentSecurityPolicy) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CONTENT_SECURITY_POLICY.getName(), contentSecurityPolicy, ContentSecurityPolicy.parser()));
	}

	/**
	 * Check that HTTP response contains {@code "X-XSS-Protection"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection</a>
	 */
	public AssertionResult hasXssProtection(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_XSS_PROTECTION.getName());
	}

	/**
	 * Check that http response <strong>does not</strong> contains {@code "X-XSS-Protection"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection</a>
	 */
	public AssertionResult doesNotHaveXssProtection(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_XSS_PROTECTION.getName());
	}

	/**
	 * Check that HTTP response contains {@code "X-XSS-Protection"} header with
	 * expected value.
	 *
	 * Please, note that:
	 *
	 * <ul>
	 *   <li>Header value is <strong>case-insensitive</strong>.</li>
	 *   <li>A non-authorize value will fail, as it is probably a mistake.</li>
	 * </ul>
	 *
	 * Following values are equivalent:
	 *
	 * <ul>
	 *   <li>{@code "1; mode=block"}</li>
	 *   <li>{@code "1; MODE=BLOCK"}</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param xssProtection Expected {@code "X-XSS-Protection"} header value.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection</a>
	 */
	public AssertionResult isXssProtectionEqualTo(HttpResponse httpResponse, String xssProtection) {
		return isXssProtectionEqualTo(httpResponse, XssProtection.parser().parse(xssProtection));
	}

	/**
	 * Check that HTTP response contains {@code "X-XSS-Protection"} header
	 * with {@link XssProtection} value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param xssProtection Expected {@code "X-XSS-Protection"} header value.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection</a>
	 */
	public AssertionResult isXssProtectionEqualTo(HttpResponse httpResponse, XssProtection xssProtection) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_XSS_PROTECTION.getName(), xssProtection, XssProtection.parser()));
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Origin"} header, no matter
	 * what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-origin">https://fetch.spec.whatwg.org/#http-access-control-allow-origin</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin</a>
	 */
	public AssertionResult hasAccessControlAllowOrigin(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Access-Control-Allow-Origin"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-origin">https://fetch.spec.whatwg.org/#http-access-control-allow-origin</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin</a>
	 */
	public AssertionResult doesNotHaveAccessControlAllowOrigin(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Origin"} header
	 * with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param accessControlAllowOrigin Header value.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-origin">https://fetch.spec.whatwg.org/#http-access-control-allow-origin</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin</a>
	 */
	public AssertionResult isAccessControlAllowOriginEqualTo(HttpResponse httpResponse, String accessControlAllowOrigin) {
		return isHeaderEqualTo(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName(), accessControlAllowOrigin, false);
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Headers"} header, no matter what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-headers">https://fetch.spec.whatwg.org/#http-access-control-allow-headers</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers</a>
	 */
	public AssertionResult hasAccessControlAllowHeaders(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_HEADERS.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Access-Control-Allow-Headers"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-headers">https://fetch.spec.whatwg.org/#http-access-control-allow-headers</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers</a>
	 */
	public AssertionResult doesNotHaveAccessControlAllowHeaders(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_HEADERS.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Headers"} header with
	 * expected values (note that order does not matter).
	 *
	 * Note that since header names are case-insensitive, assertion is also <strong>case-insensitive</strong>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param value Header value.
	 * @param other Other (optional) values.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-headers">https://fetch.spec.whatwg.org/#http-access-control-allow-headers</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers</a>
	 */
	public AssertionResult isAccessControlAllowHeadersEqualTo(HttpResponse httpResponse, String value, String... other) {
		return isAccessControlAllowHeadersEqualTo(httpResponse, toList(value, other).stream());
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Headers"} header with expected
	 * list of values.
	 *
	 * Note that since header names are case-insensitive, assertion is also <strong>case-insensitive</strong>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param accessControlAllowHeaders Header value.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-headers">https://fetch.spec.whatwg.org/#http-access-control-allow-headers</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers</a>
	 */
	public AssertionResult isAccessControlAllowHeadersEqualTo(HttpResponse httpResponse, Iterable<String> accessControlAllowHeaders) {
		return isAccessControlAllowHeadersEqualTo(httpResponse, stream(accessControlAllowHeaders.spliterator(), false));
	}

	private AssertionResult isAccessControlAllowHeadersEqualTo(HttpResponse httpResponse, Stream<String> accessControlAllowHeaders) {
		Iterable<String> values = accessControlAllowHeaders
			.map(STRING_SPLIT)
			.flatMap(Arrays::stream)
			.collect(Collectors.toList());

		return assertWith(httpResponse, new IsHeaderListEqualToAssertion(ACCESS_CONTROL_ALLOW_HEADERS.getName(), values));
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Expose-Headers"} header, no matter
	 * what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-expose-headers">https://fetch.spec.whatwg.org/#http-access-control-expose-headers</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers</a>
	 */
	public AssertionResult hasAccessControlExposeHeaders(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_EXPOSE_HEADERS.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Access-Control-Expose-Headers"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-expose-headers">https://fetch.spec.whatwg.org/#http-access-control-expose-headers</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers</a>
	 */
	public AssertionResult doesNotHaveAccessControlExposeHeaders(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_EXPOSE_HEADERS.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Expose-Headers"} header with expected
	 * value(s).
	 *
	 * Note that since header names are case-insensitive, assertion is also <strong>case-insensitive</strong>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param value Header value.
	 * @param other Other (optional) values.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-expose-headers">https://fetch.spec.whatwg.org/#http-access-control-expose-headers</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers</a>
	 */
	public AssertionResult isAccessControlExposeHeadersEqualTo(HttpResponse httpResponse, String value, String... other) {
		return isAccessControlExposeHeadersEqualTo(httpResponse, toList(value, other).stream());
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Headers"} header with expected
	 * list of value.
	 *
	 * Note that since header names are case-insensitive, assertion is also <strong>case-insensitive</strong>.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param accessControlExposeHeaders Header value.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-expose-headers">https://fetch.spec.whatwg.org/#http-access-control-expose-headers</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers</a>
	 */
	public AssertionResult isAccessControlExposeHeadersEqualTo(HttpResponse httpResponse, Iterable<String> accessControlExposeHeaders) {
		return isAccessControlExposeHeadersEqualTo(httpResponse, stream(accessControlExposeHeaders.spliterator(), false));
	}

	private AssertionResult isAccessControlExposeHeadersEqualTo(HttpResponse httpResponse, Stream<String> accessControlExposeHeaders) {
		Iterable<String> values = accessControlExposeHeaders
			.map(STRING_SPLIT)
			.flatMap(Arrays::stream)
			.collect(Collectors.toList());

		return assertWith(httpResponse, new IsHeaderListEqualToAssertion(ACCESS_CONTROL_EXPOSE_HEADERS.getName(), values));
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Methods"} header, no matter
	 * what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-methods">https://fetch.spec.whatwg.org/#http-access-control-allow-methods</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods</a>
	 */
	public AssertionResult hasAccessControlAllowMethods(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_METHODS.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Access-Control-Allow-Methods"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-methods">https://fetch.spec.whatwg.org/#http-access-control-allow-methods</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods</a>
	 */
	public AssertionResult doesNotHaveAccessControlAllowMethods(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_METHODS.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Methods"} header with
	 * expected values.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param method HTTP method.
	 * @param other Other, optional, HTTP method.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-methods">https://fetch.spec.whatwg.org/#http-access-control-allow-methods</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods</a>
	 */
	public AssertionResult isAccessControlAllowMethodsEqualTo(HttpResponse httpResponse, RequestMethod method, RequestMethod... other) {
		return isAccessControlAllowMethodsEqualTo(httpResponse, toList(method, other));
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Methods"} header with
	 * expected values (note that order of methods does not matter).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param methods HTTP Methods.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-methods">https://fetch.spec.whatwg.org/#http-access-control-allow-methods</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods</a>
	 */
	public AssertionResult isAccessControlAllowMethodsEqualTo(HttpResponse httpResponse, Iterable<RequestMethod> methods) {
		List<String> verbs = stream(methods.spliterator(), false).map(RequestMethod::verb).collect(Collectors.toList());
		return assertWith(httpResponse, new IsHeaderListEqualToAssertion(ACCESS_CONTROL_ALLOW_METHODS.getName(), verbs));
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Credentials"} header, no matter
	 * what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-credentials">https://fetch.spec.whatwg.org/#http-access-control-allow-credentials</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials</a>
	 */
	public AssertionResult hasAccessControlAllowCredentials(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_CREDENTIALS.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Access-Control-Allow-Credentials"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-allow-credentials">https://fetch.spec.whatwg.org/#http-access-control-allow-credentials</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials</a>
	 */
	public AssertionResult doesNotHaveAccessControlAllowCredentials(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_CREDENTIALS.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Credentials"} header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param flag Flag value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6266">https://tools.ietf.org/html/rfc6266</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition</a>
	 */
	public AssertionResult isAccessControlAllowCredentialsEqualTo(HttpResponse httpResponse, boolean flag) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(ACCESS_CONTROL_ALLOW_CREDENTIALS.getName(), Boolean.toString(flag), false));
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Max-Age"} header, no matter
	 * what value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-max-age">https://fetch.spec.whatwg.org/#http-access-control-max-age</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age</a>
	 */
	public AssertionResult hasAccessControlAllowMaxAge(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_MAX_AGE.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Access-Control-Allow-Max-Age"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-max-age">https://fetch.spec.whatwg.org/#http-access-control-max-age</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age</a>
	 */
	public AssertionResult doesNotHaveAccessControlAllowMaxAge(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_MAX_AGE.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Access-Control-Allow-Max-Age"} header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param maxAge Max age (in seconds).
	 * @return Assertion result.
	 * @see <a href="https://fetch.spec.whatwg.org/#http-access-control-max-age">https://fetch.spec.whatwg.org/#http-access-control-max-age</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age</a>
	 */
	public AssertionResult isAccessControlAllowMaxAgeEqualTo(HttpResponse httpResponse, long maxAge) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(ACCESS_CONTROL_ALLOW_MAX_AGE.getName(), Long.toString(maxAge), false));
	}

	/**
	 * Check that HTTP response contains {@code "Strict-Transport-Security"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6797">https://tools.ietf.org/html/rfc6797</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security</a>
	 */
	public AssertionResult hasStrictTransportSecurity(HttpResponse httpResponse) {
		return hasHeader(httpResponse, STRICT_TRANSPORT_SECURITY.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Strict-Transport-Security"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6797">https://tools.ietf.org/html/rfc6797</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security</a>
	 */
	public AssertionResult doesNotHaveStrictTransportSecurity(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, STRICT_TRANSPORT_SECURITY.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Strict-Transport-Security"} header with expected value.
	 *
	 * Note that, according to the <a href="https://fetch.spec.whatwg.org/#x-content-type-options-header">specification</a>:
	 *
	 * <ul>
	 *   <li>Directive order does not matter.</li>
	 *   <li>Directive value (such as {@code "max-age"}) may be quoted, or not.</li>
	 *   <li>Directive names are case-insensitive.</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param strictTransportSecurity Strict-Transport-Security value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6797">https://tools.ietf.org/html/rfc6797</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security</a>
	 */
	public AssertionResult isStrictTransportSecurityEqualTo(HttpResponse httpResponse, String strictTransportSecurity) {
		StrictTransportSecurity sts = StrictTransportSecurity.parser().parse(strictTransportSecurity);
		return isStrictTransportSecurityEqualTo(httpResponse, sts);
	}

	/**
	 * Check that HTTP response contains {@code "Strict-Transport-Security"} header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param strictTransportSecurity Strict-Transport-Security value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6797">https://tools.ietf.org/html/rfc6797</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security</a>
	 */
	public AssertionResult isStrictTransportSecurityEqualTo(HttpResponse httpResponse, StrictTransportSecurity strictTransportSecurity) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(STRICT_TRANSPORT_SECURITY.getName(), strictTransportSecurity, StrictTransportSecurity.parser()));
	}

	/**
	 * Check that HTTP response contains {@code "Pragma"} header, no matter what values.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.4">https://tools.ietf.org/html/rfc7234#section-5.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Pragma">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Pragma</a>
	 */
	public AssertionResult hasPragma(HttpResponse httpResponse) {
		return hasHeader(httpResponse, PRAGMA.getName());
	}

	/**
	 * Check that HTTP response <strong>does not</strong> contains {@code "Pragma"} header.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.4">https://tools.ietf.org/html/rfc7234#section-5.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Pragma">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Pragma</a>
	 */
	public AssertionResult doesNotHavePragma(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, PRAGMA.getName());
	}

	/**
	 * Check that HTTP response contains {@code "Pragma"} header with expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param pragma Pragma value.
	 * @return Assertion result.
	 */
	public AssertionResult isPragmaEqualTo(HttpResponse httpResponse, String pragma) {
		return isHeaderEqualTo(httpResponse, PRAGMA.getName(), pragma, false);
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
	 * Check that HTTP response contains {@code "Content-Disposition"} header with
	 * expected value.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param contentDispositionValue Expected value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6266">https://tools.ietf.org/html/rfc6266</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition</a>
	 */
	public AssertionResult isContentDispositionEqualTo(HttpResponse httpResponse, String contentDispositionValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_DISPOSITION.getName(), contentDispositionValue);
	}

	/**
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "application/json"}.
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
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "text/css"}.
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
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "text/plain"}.
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
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "text/csv"}.
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
	 * Check that HTTP response has a mime-type equals (ignoring case) to {@code "application/pdf"}.
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
	 * Check that HTTP response has {@code UTF-8} charset (the response charset is the one specified in
	 * the {@code "Content-Type"} header).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult isUtf8(HttpResponse httpResponse) {
		return hasCharset(httpResponse, StandardCharsets.UTF_8);
	}

	/**
	 * Check that http response is {@code "application/javascript"} or {@code "text/javascript"}.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 * @see <a href="https://mathiasbynens.be/demo/javascript-mime-type">https://mathiasbynens.be/demo/javascript-mime-type</a>
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
	 * Check that HTTP response contains expected header with expected value.
	 *
	 * Note that:
	 * <ul>
	 *   <li>Header name is case-insensitive.</li>
	 *   <li>Value <strong>comparison is strict</strong>: the exact string values will be compared.</li>
	 * </ul>
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param headerName Header name.
	 * @param headerValue Header value.
	 * @return Assertion result.
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers</a>
	 */
	public AssertionResult isHeaderEqualTo(HttpResponse httpResponse, String headerName, String headerValue) {
		return isHeaderEqualTo(httpResponse, headerName, headerValue, false);
	}

	private AssertionResult isHeaderEqualTo(HttpResponse httpResponse, String headerName, String headerValue, boolean caseInsensitive) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(headerName, headerValue, caseInsensitive));
	}

	/**
	 * Check that http response is of expected mime type (a.k.a media type).
	 *
	 * Note that comparison is <strong>case insensitive</strong>, so calling assertion with {@code "application/json"} is
	 * equivalent to {@code "APPLICATION/JSON"}.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult hasMimeType(HttpResponse httpResponse, String expectedMimeType) {
		MediaType expected = MediaType.parser().parse(expectedMimeType);
		return assertWith(httpResponse, new HasMimeTypeAssertion(expected));
	}

	/**
	 * Check that HTTP response is of expected mime type (a.k.a media type).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expectedMimeType Expected mime type.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult hasMimeType(HttpResponse httpResponse, MediaType expectedMimeType) {
		return assertWith(httpResponse, new HasMimeTypeAssertion(expectedMimeType));
	}

	private AssertionResult hasMimeTypeIn(HttpResponse httpResponse, List<MediaType> expectedMimeTypes) {
		return assertWith(httpResponse, new HasMimeTypeAssertion(expectedMimeTypes));
	}

	/**
	 * Check that HTTP response has expected charset (the response charset is the one specified in
	 * * the {@code "Content-Type"} header).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expectedCharset Expected charset.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult hasCharset(HttpResponse httpResponse, Charset expectedCharset) {
		return hasCharset(httpResponse, expectedCharset.name().toLowerCase());
	}

	/**
	 * Check that HTTP response has expected charset (the response charset is the one specified in
	 * the {@code "Content-Type"} header).
	 *
	 * Note that charset is case-insensitive, so calling the assertion with {@code "utf-8"} or {@code "UTF-8"} produce the same
	 * result.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param expectedCharset Expected charset.
	 * @return Assertion result.
	 * @see <a href="https://www.w3.org/Protocols/rfc1341/4_Content-Type.html">https://www.w3.org/Protocols/rfc1341/4_Content-Type.html</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type</a>
	 */
	public AssertionResult hasCharset(HttpResponse httpResponse, String expectedCharset) {
		return assertWith(httpResponse, new HasCharsetAssertion(expectedCharset));
	}

	/**
	 * Check that HTTP response does not contains any cookies.
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6265#section-5.4">https://tools.ietf.org/html/rfc6265#section-5.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies">https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies</a>
	 */
	public AssertionResult doesNotHaveCookie(HttpResponse httpResponse) {
		return assertWith(httpResponse, new DoesNotHaveCookieAssertion());
	}

	/**
	 * Check that HTTP response does not contains cookie with given name (note that cookie name
	 * is case-sensitive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param name Cookie name.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6265#section-5.4">https://tools.ietf.org/html/rfc6265#section-5.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies">https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies</a>
	 */
	public AssertionResult doesNotHaveCookie(HttpResponse httpResponse, String name) {
		return assertWith(httpResponse, new DoesNotHaveCookieAssertion(name));
	}

	/**
	 * Check that HTTP response contains cookie with given name (note that cookie name is
	 * case-sensitive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param name Cookie name.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6265#section-5.4">https://tools.ietf.org/html/rfc6265#section-5.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies">https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies</a>
	 */
	public AssertionResult hasCookie(HttpResponse httpResponse, String name) {
		return assertWith(httpResponse, new HasCookieAssertion(name));
	}

	/**
	 * Check that HTTP response contains cookie with given name and value (note that cookie name
	 * is case-sensitive).
	 *
	 * @param httpResponse HTTP response to be tested.
	 * @param name Cookie name.
	 * @param value Cookie value.
	 * @return Assertion result.
	 * @see <a href="https://tools.ietf.org/html/rfc6265#section-5.4">https://tools.ietf.org/html/rfc6265#section-5.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies">https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies</a>
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
	 * @see <a href="https://tools.ietf.org/html/rfc6265#section-5.4">https://tools.ietf.org/html/rfc6265#section-5.4</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie">https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies">https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies</a>
	 */
	public AssertionResult hasCookie(HttpResponse httpResponse, Cookie cookie) {
		return assertWith(httpResponse, new HasCookieAssertion(cookie));
	}

	private static AssertionResult assertWith(HttpResponse httpResponse, Assertion<HttpResponse> assertion) {
		AssertionResult r = isNotNull(httpResponse);
		if (r.isFailure()) {
			return r;
		}

		return assertion.handle(httpResponse);
	}

	private static AssertionResult isNotNull(HttpResponse httpResponse) {
		return httpResponse == null ? failure(shouldNotBeNull("HTTP Response")) : success();
	}
}
