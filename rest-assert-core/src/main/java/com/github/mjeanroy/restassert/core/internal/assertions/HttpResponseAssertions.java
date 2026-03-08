/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2026 Mickael Jeanroy
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

/// Reusable Assertions of http response.
/// This class is implemented as a singleton object.
/// This class is thread safe.
public final class HttpResponseAssertions {

	/// Singleton object.
	private static final HttpResponseAssertions INSTANCE = new HttpResponseAssertions();
	private static final Function<String, String[]> STRING_SPLIT = (input) -> input.split(",");

	/// Get singleton object.
	///
	/// @return Singleton object.
	public static HttpResponseAssertions instance() {
		return INSTANCE;
	}

	// Private constructor to ensure singleton
	private HttpResponseAssertions() {
	}

	/// Check that status code of http response is `"OK"` (i.e is strictly equals to `200`).
	///
	/// For additional details on `OK` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/200)
	/// - [httpstatuse](https://httpstatuses.com/200")
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isOk(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.OK);
	}

	/// Check that status code of http response is `"CREATED"` (i.e is strictly equals to `201`).
	///
	/// For additional details on `CREATED` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/201)
	/// - [httpstatuse](https://httpstatuses.com/201)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isCreated(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.CREATED);
	}

	/// Check that status code of http response is `"ACCEPTED"` status (i.e is strictly equals to `202`).
	///
	/// For additional details on `ACCEPTED` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/202)
	/// - [httpstatuse](https://httpstatuses.com/202)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isAccepted(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.ACCEPTED);
	}

	/// Check that status code of http response is `"NO CONTENT"` status (i.e is strictly equals to `204`).
	///
	/// For additional details on `NO CONTENT` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/204)
	/// - [httpstatuse](https://httpstatuses.com/204)
	///
	/// @param httpResponse Http response.
	/// @return Assertion result.
	public AssertionResult isNoContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NO_CONTENT);
	}

	/// Check that status code of http response is `"PARTIAL CONTENT"` status (i.e is strictly equals to `206`).
	///
	/// For additional details on `PARTIAL CONTENT` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/206)
	/// - [httpstatuse](https://httpstatuses.com/206)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isPartialContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.PARTIAL_CONTENT);
	}

	/// Check that status code of http response is `"RESET CONTENT"` status (i.e is strictly equals to `205`).
	///
	/// For additional details on `RESET CONTENT` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/205)
	/// - [httpstatuse](https://httpstatuses.com/205)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isResetContent(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.RESET_CONTENT);
	}

	/// Check that status code of http response is `"MULTIPLE CHOICES"` status (i.e is strictly equals to `300`).
	///
	/// For additional details on `MULTIPLE CHOICES` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.1)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/300)
	/// - [httpstatuse](https://httpstatuses.com/300)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isMultipleChoices(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.MULTIPLE_CHOICES);
	}

	/// Check that status code of http response is `"MOVED PERMANENTLY"` status (i.e is strictly equals to `301`).
	///
	/// For additional details on `MOVED PERMANENTLY` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/301)
	/// - [httpstatuse](https://httpstatuses.com/301)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isMovedPermanently(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.MOVED_PERMANENTLY);
	}

	/// Check that status code of http response is `"MOVED TEMPORARILY"` status (i.e is strictly equals to `302`).
	///
	/// For additional details on `MOVED TEMPORARILY` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/302)
	/// - [httpstatuse](https://httpstatuses.com/302)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isMovedTemporarily(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.MOVED_TEMPORARILY);
	}

	/// Check that status code of http response is `"SEE OTHER"` status (i.e is strictly equals to `303`).
	///
	/// For additional details on `SEE OTHER` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/303)
	/// - [httpstatuse](https://httpstatuses.com/303)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isSeeOther(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.SEE_OTHER);
	}

	/// Check that status code of http response is `"NOT MODIFIED"` status (i.e is strictly equals to `304`).
	///
	/// For additional details on `NOT MODIFIED` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/304)
	/// - [httpstatuse](https://httpstatuses.com/304)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isNotModified(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_MODIFIED);
	}

	/// Check that status code of http response is `"TEMPORARY REDIRECT"` status (i.e is strictly equals to `307`).
	///
	/// For additional details on `TEMPORARY REDIRECT` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.8)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/307)
	/// - [httpstatuse](https://httpstatuses.com/307)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isTemporaryRedirect(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.TEMPORARY_REDIRECT);
	}

	/// Check that status code of http response is `"PERMANENT REDIRECT"` status (i.e is strictly equals to `308`).
	///
	/// For additional details on `PERMANENT REDIRECT` status, check following documentations:
	/// - [RFC 7538](https://tools.ietf.org/html/rfc7538)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/308)
	/// - [httpstatuse](https://httpstatuses.com/308)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isPermanentRedirect(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.PERMANENT_REDIRECT);
	}

	/// Check that status code of http response is `"UNAUTHORIZED"` status (i.e is strictly equals to `401`).
	///
	/// For additional details on `UNAUTHORIZED` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/401)
	/// - [httpstatuse](https://httpstatuses.com/401)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isUnauthorized(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.UNAUTHORIZED);
	}

	/// Check that status code of http response is `"FORBIDDEN"` status (i.e is strictly equals to `403`).
	///
	/// For additional details on `FORBIDDEN` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/403)
	/// - [httpstatuse](https://httpstatuses.com/403)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isForbidden(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.FORBIDDEN);
	}

	/// Check that status code of http response is `"BAD REQUEST"` status (i.e is strictly equals to `400`).
	///
	/// For additional details on `BAD REQUEST` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400)
	/// - [httpstatuse](https://httpstatuses.com/400)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isBadRequest(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.BAD_REQUEST);
	}

	/// Check that status code of http response is `"NOT ACCEPTABLE"` status (i.e is strictly equals to `406`).
	///
	/// For additional details on `NOT ACCEPTABLE` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/406)
	/// - [httpstatuse](https://httpstatuses.com/406)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isNotAcceptable(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_ACCEPTABLE);
	}

	/// Check that status code of http response is `"NOT FOUND"` status (i.e is strictly equals to `404`).
	///
	/// For additional details on `NOT FOUND` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404)
	/// - [httpstatuse](https://httpstatuses.com/404)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isNotFound(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_FOUND);
	}

	/// Check that status code of http response is `"INTERNAL SERVER ERROR"` status (i.e is strictly equals to `500`).
	///
	/// For additional details on `INTERNAL SERVER ERROR` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/500)
	/// - [httpstatuse](https://httpstatuses.com/500)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isInternalServerError(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.INTERNAL_SERVER_ERROR);
	}

	/// Check that status code of http response is `"GONE"` status (i.e is strictly equals to `410`).
	///
	/// For additional details on `GONE` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/411)
	/// - [httpstatuse](https://httpstatuses.com/411)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isGone(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.GONE);
	}

	/// Check that status code of http response is `"PRE CONDITION FAILED"` status (i.e is strictly equals to `412`).
	///
	/// For additional details on `PRE CONDITION FAILED` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/412)
	/// - [httpstatuse](https://httpstatuses.com/412)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isPreConditionFailed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.PRE_CONDITION_FAILED);
	}

	/// Check that status code of http response is `"REQUEST ENTITY TOO LARGE"` status (i.e is strictly equals to `413`).
	///
	/// For additional details on `REQUEST ENTITY TOO LARGE` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/413)
	/// - [httpstatuse](https://httpstatuses.com/413)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isRequestEntityTooLarge(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.REQUEST_ENTITY_TOO_LARGE);
	}

	/// Check that status code of http response is `"REQUEST URI TOO LONG"` status (i.e is strictly equals to `414`).
	///
	/// For additional details on `REQUEST URI TOO LONG` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/414)
	/// - [httpstatuse](https://httpstatuses.com/414)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isRequestUriTooLong(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.REQUEST_URI_TOO_LONG);
	}

	/// Check that status code of http response is `"METHOD NOT ALLOWED` status (i.e is strictly equals to `405`).
	///
	/// For additional details on `METHOD NOT ALLOWED` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/405)
	/// - [httpstatuse](https://httpstatuses.com/405)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isMethodNotAllowed(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.METHOD_NOT_ALLOWED);
	}

	/// Check that status code of http response is `"REQUEST TIMEOUT"` status (i.e is strictly equals to `408`).
	///
	/// For additional details on `REQUEST TIMEOUT` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/409)
	/// - [httpstatuse](https://httpstatuses.com/409)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isRequestTimeout(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.REQUEST_TIMEOUT);
	}

	/// Check that status code of http response is `"CONFLICT"` status (i.e is strictly equals to `406`).
	///
	/// For additional details on `CONFLICT` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/406)
	/// - [httpstatuse](https://httpstatuses.com/406)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isConflict(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.CONFLICT);
	}

	/// Check that status code of http response is `"UNSUPPORTED MEDIA TYPE"` status (i.e is strictly equals to `415`).
	///
	/// For additional details on `UNSUPPORTED MEDIA TYPE` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/415)
	/// - [httpstatuse](https://httpstatuses.com/415)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isUnsupportedMediaType(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.UNSUPPORTED_MEDIA_TYPE);
	}

	/// Check that status code of http response is `"REQUESTED RANGE NOT SATISFIABLE"` status (i.e is strictly equals to `416`).
	///
	/// For additional details on `REQUESTED RANGE NOT SATISFIABLE` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/416)
	/// - [httpstatuse](https://httpstatuses.com/416)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isRequestedRangeNotSatisfiable(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.REQUESTED_RANGE_NOT_SATISFIABLE);
	}

	/// Check that status code of http response is `"NOT IMPLEMENTED"` status (i.e is strictly equals to `501`).
	///
	/// For additional details on `NOT IMPLEMENTED` status, check following documentations:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/501)
	/// - [httpstatuse](https://httpstatuses.com/501)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isNotImplemented(HttpResponse httpResponse) {
		return isStatusEqual(httpResponse, HttpStatusCodes.NOT_IMPLEMENTED);
	}

	/// Check that status code of HTTP response is strictly equals to an expected status.
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param status Expected status.
	/// @return Assertion result.
	public AssertionResult isStatusEqual(HttpResponse httpResponse, int status) {
		return assertWith(httpResponse, new StatusEqualAssertion(status));
	}

	/// Check that status code of HTTP response is a "SUCCESS" status a.k.a 2XX status.
	///
	/// Note that this assertion will check that the HTTP response status is between `200` and `299` (inclusive),
	/// it does not check that the status code is in the list of status described
	/// in [RFC 2516 official specification](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2).
	///
	/// For a complete list of "official" status codes, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
	/// - [Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#2xx_Success)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isSuccess(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 200, 299);
	}

	/// Check that status code of HTTP response is **not** a "SUCCESS" status, a.k.a 2XX status.
	///
	/// Note that this assertion will check that the HTTP response status is **strictly** not
	/// between `200` and `299`.
	///
	/// For a complete list of "official" status codes, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
	/// - [Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#2xx_Success)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isNotSuccess(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 200, 299);
	}

	/// Check that status code of HTTP response is a "REDIRECTION" status, a.k.a 3XX status.
	///
	/// Note that this assertion will check that HTTP response status is between `300` and `399` (inclusive),
	/// it does not check that the status code is in the list of status described
	/// in [RFC 2616 official specification](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3).
	///
	/// For a complete list of "official" status codes, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
	/// - [Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#3xx_Redirection)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isRedirection(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 300, 399);
	}

	/// Check that status code of HTTP response is **not** a "REDIRECTION" status, a.k.a 3XX status.
	///
	/// Note that this assertion will only check that the HTTP response status is **strictly** not
	/// between `300` and `399`.
	///
	/// For a complete list of "official" status codes, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
	/// - [Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#3xx_Redirection)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isNotRedirection(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 300, 399);
	}

	/// Check that status code of HTTP response is a "SERVER ERROR" status, a.k.a 5XX status.
	///
	/// Note that this assertion will check that HTTP response status is between `500` and `599` (inclusive),
	/// it does not check that the status code is in the list of status described
	/// in [RFC 2616 official specification](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5).
	///
	/// For a complete list of "official" status codes, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
	/// - [Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#5xx_Server_errors)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isServerError(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 500, 599);
	}

	/// Check that status code of HTTP response is **not** a "SERVER ERROR" status, a.k.a 5XX status.
	///
	/// Note that this assertion will only check that the HTTP response status is **strictly** not
	/// between `500` and `599`.
	///
	/// For a complete list of "official" status codes, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
	/// - [Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#5xx_Server_errors)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isNotServerError(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 500, 599);
	}

	/// Check that status code of HTTP response is a "CLIENT ERROR" status, a.k.a 4XX status.
	///
	/// Note that this assertion will check that HTTP response status is between `400` and `499` (inclusive),
	/// it does not check that the status code is in the list of status described
	/// in [RFC 2616 >official specification](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4).
	///
	/// For a complete list of "official" status codes, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
	/// - [Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isClientError(HttpResponse httpResponse) {
		return isStatusBetween(httpResponse, 400, 499);
	}

	/// Check that status code of HTTP response is **not** a "CLIENT ERROR" status, a.k.a 4XX status.
	///
	/// Note that this assertion will only check that the HTTP response status is **strictly** not
	/// between `400` and `499`.
	///
	/// For a complete list of "official" status codes, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
	/// - [Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#4xx_Client_errors)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isNotClientError(HttpResponse httpResponse) {
		return isStatusOutOf(httpResponse, 400, 499);
	}

	/// Check that status code of HTTP response is included between a lower bound and
	/// an upper bound (inclusive).
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param start Lower bound.
	/// @param end Upper bound.
	/// @return Assertion result.
	public AssertionResult isStatusBetween(HttpResponse httpResponse, int start, int end) {
		return assertWith(httpResponse, new StatusBetweenAssertion(start, end));
	}

	/// Check that status code of HTTP response is not included between a lower bound and
	/// an upper bound (inclusive).
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param start Lower bound.
	/// @param end Upper bound.
	/// @return Assertion result.
	public AssertionResult isStatusOutOf(HttpResponse httpResponse, int start, int end) {
		return assertWith(httpResponse, new StatusOutOfAssertion(start, end));
	}

	/// Check that HTTP response contains `"ETag"` header, no matter what value.
	///
	/// For additional details about the `Etag` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ETag)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasETag(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ETAG.getName());
	}

	/// Check that HTTP response **does not** contains `"ETag"` header.
	///
	/// For additional details about the `Etag` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ETag)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveETag(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ETAG.getName());
	}

	/// Check that HTTP response contains `"ETag"` header with expected value.
	///
	/// For additional details about the `Etag` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ETag)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param etagValue Expected `"ETag"` value.
	/// @return Assertion result.
	public AssertionResult isETagEqualTo(HttpResponse httpResponse, String etagValue) {
		return isHeaderEqualTo(httpResponse, ETAG.getName(), etagValue, false);
	}

	/// Check that HTTP response contains `"Content-Type"` header, no matter what value.
	///
	/// For additional details about the `Content-Type` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Type)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasContentType(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_TYPE.getName());
	}

	/// Check that HTTP response contains `"Content-Type"` header with expected value.
	///
	/// According to the specification, comparison will be **case-insensitive**, and charset options may be
	/// quoted or not. So following values are all equivalent:
	/// - Content-Type: application/json; charset=utf-8
	/// - Content-Type: APPLICATION/JSON; charset=UTF-8
	/// - Content-Type: application/json; charset='utf-8'
	/// - Content-Type: application/json; charset="utf-8"
	///
	/// For additional details about the `Content-Type` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Type)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentTypeValue Expected `"Content-Type` value.
	/// @return Assertion result.
	public AssertionResult isContentTypeEqualTo(HttpResponse httpResponse, String contentTypeValue) {
		return isContentTypeEqualTo(httpResponse, ContentType.parser().parse(contentTypeValue));
	}

	/// Check that HTTP response contains `"Content-Type"` header with expected value.
	/// Here is an example to create a `"Content-Type"` using the [ContentType] factories:
	///
	/// ```
	///   // Produce "application/json; charset=utf-8" header.
	///   public ContentType getApplicationJson() {
	///     return ContentType.contentType(
	///       MediaType.application("json"),
	///       StandardCharsets.UTF_8
	///     );
	///   }
	/// ```
	///
	/// For additional details about the `Content-Type` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Type)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentTypeValue Expected `"Content-Type` value.
	/// @return Assertion result.
	public AssertionResult isContentTypeEqualTo(HttpResponse httpResponse, ContentType contentTypeValue) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CONTENT_TYPE.getName(), contentTypeValue, ContentType.parser()));
	}

	/// Check that HTTP response contains `"Content-Encoding"` header, no matter what value.
	///
	/// For additional details about the `Content-Encoding` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Encoding)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasContentEncoding(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_ENCODING.getName());
	}

	/// Check that HTTP response **does not** contains `"Content-Encoding"` header.
	///
	/// For additional details about the `Content-Encoding` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Encoding)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveContentEncoding(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CONTENT_ENCODING.getName());
	}

	/// Check that HTTP response contains `"Content-Encoding"` header with
	/// expected value.
	///
	/// Note that according to the specification, comparison is **case-insensitive**.
	///
	/// For additional details about the `Content-Encoding` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Encoding)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentEncodingValue Expected `"Content-Encoding` value.
	/// @return Assertion result.
	public AssertionResult isContentEncodingEqualTo(HttpResponse httpResponse, String contentEncodingValue) {
		ContentEncoding contentEncoding = ContentEncoding.parser().parse(contentEncodingValue);
		return isContentEncodingEqualTo(httpResponse, contentEncoding);
	}

	/// Check that HTTP response contains `"Content-Encoding"` header with
	/// expected value.
	///
	/// For additional details about the `Content-Encoding` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Encoding)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentEncoding Expected `"Content-Encoding` value.
	/// @return Assertion result.
	public AssertionResult isContentEncodingEqualTo(HttpResponse httpResponse, ContentEncoding contentEncoding) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CONTENT_ENCODING.getName(), contentEncoding, ContentEncoding.parser()));
	}

	/// Check that http response contains `"Content-Encoding"` header with `"gzip"`
	/// value (meaning that response body has been gzipped).
	///
	/// This is a shortcut for checking that `"Content-Encoding"` is equal to `"gzip"`.
	///
	/// Note that according to specification, comparison is **case-insensitive** (so `"gzip"`
	/// or `"GZIP"` is equivalent).
	///
	/// For additional details about the `Content-Encoding` header, check:
	/// - [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Encoding)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isGzipped(HttpResponse httpResponse) {
		return isContentEncodingEqualTo(httpResponse, ContentEncoding.gzip());
	}

	/// Check that HTTP response contains `"Content-Length"` header, no matter what value.
	///
	/// For additional details about the `Content-Length` header, check:
	/// - [RFC 7230](https://tools.ietf.org/html/rfc7230#section-3.3.2)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Length)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasContentLength(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_LENGTH.getName());
	}

	/// Check that HTTP response contains `"Location"` header, no matter what value.
	///
	/// For additional details about the `Location` header, check:
	/// - [RFC 7231](https://tools.ietf.org/html/rfc7231#section-7.1.2)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Location)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasLocation(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LOCATION.getName());
	}

	/// Check that HTTP response **does not** contains `"Location"` header.
	///
	/// For additional details about the `Location` header, check:
	/// - [RFC 7231](https://tools.ietf.org/html/rfc7231#section-7.1.2)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Location)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveLocation(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, LOCATION.getName());
	}

	/// Check that HTTP response contains `"Location"` header with
	/// expected value.
	///
	/// Note that, even if URI values should be case insensitive, comparison is **case sensitive** (so
	/// URI `"https://domain.tld"` is not equivalent to `"HTTPS://DOMAIN.TLD"`).
	///
	/// For additional details about the `Location` header, check:
	/// - [RFC 7231](https://tools.ietf.org/html/rfc7231#section-7.1.2)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Location)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param locationValue Expected `"Location"` value.
	/// @return Assertion result.
	public AssertionResult isLocationEqualTo(HttpResponse httpResponse, String locationValue) {
		return isHeaderEqualTo(httpResponse, LOCATION.getName(), locationValue, false);
	}

	/// Check that HTTP response contains `"Last-Modified"` header, no matter what value.
	///
	/// For additional details about the `Last-Modified` header, check:
	/// - [RFC 7232](https://tools.ietf.org/html/rfc7232#section-2.2)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Last-Modified)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasLastModified(HttpResponse httpResponse) {
		return hasHeader(httpResponse, LAST_MODIFIED.getName());
	}

	/// Check that HTTP response **does not** contains `"Last-Modified"` header.
	///
	/// For additional details about the `Last-Modified` header, check:
	/// - [RFC 7232](https://tools.ietf.org/html/rfc7232#section-2.2)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Last-Modified)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveLastModified(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, LAST_MODIFIED.getName());
	}

	/// Check that HTTP response contains `"Last-Modified"` header with
	/// expected value.
	///
	/// Three date-time format are supported, according to the [RFC 7231](https://tools.ietf.org/html/rfc7231#section-7.1.1.1):
	/// - The (preferred) format defined by [RFC 5322](https://tools.ietf.org/html/rfc5322#section-3.3), for example: `"Sun, 06 Nov 1994 08:49:37 GMT"`.
	/// - The (obsolete) format defined by [RFC 850](https://tools.ietf.org/html/rfc850), for example: `"Sunday, 06-Nov-94 08:49:37 GMT"`.
	/// - The (obsolete) ANSI C's asctime() format, for example: `"Sun Nov6 08:49:37 1994"`.
	///
	/// Note that, unless specific reason, first format defined by RFC 5322 should be used.
	///
	/// For additional details about the `Last-Modified` header, check:
	/// - [RFC 7232](https://tools.ietf.org/html/rfc7232#section-2.2)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Last-Modified)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param lastModifiedValue Expected `"Last-Modified"` value.
	/// @return Assertion result.
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, String lastModifiedValue) {
		return isLastModifiedEqualTo(httpResponse, parseHttpDate(lastModifiedValue));
	}

	/// Check that HTTP response contains `"Last-Modified"` header with
	/// expected [Date] value.
	///
	/// For additional details about the `Last-Modified` header, check:
	/// - [RFC 7232](https://tools.ietf.org/html/rfc7232#section-2.2)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Last-Modified)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param lastModifiedDate Expected `"Last-Modified"` value.
	/// @return Assertion result.
	public AssertionResult isLastModifiedEqualTo(HttpResponse httpResponse, Date lastModifiedDate) {
		return assertWith(httpResponse, new IsDateHeaderEqualToAssertion(LAST_MODIFIED.getName(), lastModifiedDate));
	}

	/// Check that HTTP response contains `"Expires"` header, no matter what value.
	///
	/// For additional details about the `Expires` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.3)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Expires)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasExpires(HttpResponse httpResponse) {
		return hasHeader(httpResponse, EXPIRES.getName());
	}

	/// Check that HTTP response **does not** contains `"Expires"` header.
	///
	/// For additional details about the `Expires` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.3)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Expires)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveExpires(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, EXPIRES.getName());
	}

	/// Check that HTTP response contains `"Expires"` header with
	/// expected value.
	///
	/// Three date-time format are supported (for `expiresValue` parameter or for header
	/// value), according to the [RFC 7231](https://tools.ietf.org/html/rfc7231#section-7.1.1.1):
	/// - The (preferred) format defined by [RFC 5322](https://tools.ietf.org/html/rfc5322#section-3.3), for example: `"Sun, 06 Nov 1994 08:49:37 GMT"`.
	/// - The (obsolete) format defined by [RFC 850](https://tools.ietf.org/html/rfc850), for example: `"Sunday, 06-Nov-94 08:49:37 GMT"`.
	/// - The (obsolete) ANSI C's asctime() format, for example: `"Sun Nov6 08:49:37 1994"`.
	///
	/// Note that:
	/// - Unless specific reason, the first (preferred) format should be used.
	/// - This is not a "strict" comparison: `expiresValue` parameter and header value does not need to use the same pattern.
	///
	/// For additional details about the `Expires` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.3)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Expires)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param expiresValue Expected `"Expires"` value.
	/// @return Assertion result.
	public AssertionResult isExpiresEqualTo(HttpResponse httpResponse, String expiresValue) {
		return isExpiresEqualTo(httpResponse, parseHttpDate(expiresValue));
	}

	/// Check that HTTP response contains `"Expires"` header with
	/// expected [Date] value.
	///
	/// For additional details about the `Expires` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.3)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Expires)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param expiresValue Expected `"Expires"` value.
	/// @return Assertion result.
	public AssertionResult isExpiresEqualTo(HttpResponse httpResponse, Date expiresValue) {
		return assertWith(httpResponse, new IsDateHeaderEqualToAssertion(EXPIRES.getName(), expiresValue));
	}

	/// Check that HTTP response contains `"Cache-Control"` header, no matter what value.
	///
	/// For additional details about the `Cache-Control` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#page-21)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Cache-Control)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasCacheControl(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CACHE_CONTROL.getName());
	}

	/// Check that HTTP response **does not** contains `"Cache-Control"` header.
	///
	/// For additional details about the `Cache-Control` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#page-21)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Cache-Control)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveCacheControl(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CACHE_CONTROL.getName());
	}

	/// Check that HTTP response contains `"Cache-Control"` header with expected value.
	///
	/// Note that, according to the [RFC 7234](https://tools.ietf.org/html/rfc7234#page-21):
	/// - Comparison is **case-insensitive**.
	/// - Header directives does not need to be in the same order
	///
	/// For example, following values are equivalent:
	/// - `"no-cache, no-store, must-revalidate"`
	/// - `"NO-CACHE, NO-STORE, MUST-REVALIDATE"`
	/// - `"no-cache, must-revalidate, no-store"`
	///
	/// For additional details about the `Cache-Control` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#page-21)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Cache-Control)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param cacheControl Expected `"Cache-Control"` value.
	/// @return Assertion result.
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, String cacheControl) {
		return isCacheControlEqualTo(httpResponse, CacheControl.parser().parse(cacheControl));
	}

	/// Check that HTTP response contains `"Cache-Control"` header with expected value.
	///
	/// You can build [CacheControl] using [com.github.mjeanroy.restassert.core.data.CacheControlBuilder] to get
	/// a fluent API, for example:
	///
	/// ```
	///   private CacheControl createHeader() {
	///     return CacheControl.builder()
	///       .visibility(Visibility.PUBLIC)
	///       .noTransform()
	///       .noStore()
	///       .build();
	///   }
	/// ```
	///
	/// For additional details about the `Cache-Control` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#page-21)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Cache-Control)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param cacheControl Expected `"Cache-Control"` value.
	/// @return Assertion result.
	public AssertionResult isCacheControlEqualTo(HttpResponse httpResponse, CacheControl cacheControl) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CACHE_CONTROL.getName(), cacheControl, CacheControl.parser()));
	}

	/// Check that HTTP response contains `"X-Frame-Options"` header, no matter what values.
	///
	/// For additional details about the `X-Frame-Options` header, check:
	/// - [RFC 7034](https://tools.ietf.org/html/rfc7034)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-Frame-Options)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasFrameOptions(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_FRAME_OPTIONS.getName());
	}

	/// Check that HTTP response **does not** contains `"X-Frame-Options"` header.
	///
	/// For additional details about the `X-Frame-Options` header, check:
	/// - [RFC 7034](https://tools.ietf.org/html/rfc7034)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-Frame-Options)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveFrameOptions(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_FRAME_OPTIONS.getName());
	}

	/// Check that HTTP response contains `"X-Frame-Options"` header with
	/// expected value.
	///
	/// Note that, according to <a href="https://tools.ietf.org/html/rfc7034">specification</a>:
	/// - Header value is **case-insensitive**, so `"deny"` or `"DENY"` is equivalent.
	/// - Using a different value than `"DENY"`, `"SAMEORIGIN"` or `"ALLOW-FROM"` is not permitted, so test will fail as it is probably a mistake.
	///
	/// For additional details about the `X-Frame-Options` header, check:
	/// - [RFC 7034](https://tools.ietf.org/html/rfc7034)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-Frame-Options)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param frameOptions Expected `"X-Frame-Options"` header value.
	/// @return Assertion result.
	public AssertionResult isFrameOptionsEqualTo(HttpResponse httpResponse, String frameOptions) {
		return isFrameOptionsEqualTo(httpResponse, FrameOptions.parser().parse(frameOptions));
	}

	/// Check that HTTP response contains `"X-Frame-Options"` header with
	/// expected [FrameOptions] value.
	///
	/// You can build [FrameOptions] using one of the static factory available:
	///
	/// ```
	///   private FrameOptions createDeny() {
	///     return FrameOptions.deny();
	///   }
	///
	///   private FrameOptions createSameOrigin() {
	///     return FrameOptions.sameOrigin();
	///   }
	///
	///   private FrameOptions createAllowFrom() {
	///     return FrameOptions.allowFrom("https://domain.tld");
	///   }
	/// ```
	///
	/// For additional details about the `X-Frame-Options` header, check:
	/// - [RFC 7034](https://tools.ietf.org/html/rfc7034)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-Frame-Options)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param frameOptions Expected `"X-Frame-Options"` header value.
	/// @return Assertion result.
	public AssertionResult isFrameOptionsEqualTo(HttpResponse httpResponse, FrameOptions frameOptions) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_FRAME_OPTIONS.getName(), frameOptions, FrameOptions.parser()));
	}

	/// Check that HTTP response contains `"X-Content-Type-Options"` header, no matter what value.
	///
	/// For additional details about the `X-Content-Type-Options` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#x-content-type-options-header)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-Content-Type-Options)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasContentTypeOptions(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_CONTENT_TYPE_OPTIONS.getName());
	}

	/// Check that http response **does not** contains `"X-Content-Type-Options"` header.
	///
	/// For additional details about the `X-Content-Type-Options` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#x-content-type-options-header)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-Content-Type-Options)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveContentTypeOptions(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_CONTENT_TYPE_OPTIONS.getName());
	}

	/// Check that HTTP response contains `"X-Content-Type-Options"` header with
	/// expected value.
	///
	/// Note that, according to the [specification](https://fetch.spec.whatwg.org/#x-content-type-options-header):
	/// - Header value is **case-insensitive**, so `"nosniff"` or `"NOSNIFF"` are equivalent.
	/// - Non authorized value (currently, only `"nosniff"` is allowed) will fail since it is probably a mistake.
	///
	/// For additional details about the `X-Content-Type-Options` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#x-content-type-options-header)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-Content-Type-Options)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentTypeOptions Expected `"X-Content-Type-Options"` header value.
	/// @return Assertion result.
	public AssertionResult isContentTypeOptionsEqualTo(HttpResponse httpResponse, String contentTypeOptions) {
		return isContentTypeOptionsEqualTo(httpResponse, ContentTypeOptions.parser().parse(contentTypeOptions));
	}

	/// Check that HTTP response contains `"X-Content-Type-Options"` header with
	/// expected [ContentTypeOptions] value.
	///
	/// For additional details about the `X-Content-Type-Options` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#x-content-type-options-header)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-Content-Type-Options)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentTypeOptions Expected `"X-Content-Type-Options"` header value.
	/// @return Assertion result.
	public AssertionResult isContentTypeOptionsEqualTo(HttpResponse httpResponse, ContentTypeOptions contentTypeOptions) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_CONTENT_TYPE_OPTIONS.getName(), contentTypeOptions, ContentTypeOptions.parser()));
	}

	/// Check that HTTP response contains `"Content-Security-Policy"` header, no matter what value.
	///
	/// For additional details about the `Content-Security-Policy` header, check:
	/// - [WHATWG CSP](https://w3c.github.io/webappsec-csp/)
	/// - [WHATWG CSP2](https://w3c.github.io/webappsec-csp/2/)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Security-Policy)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasContentSecurityPolicy(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_SECURITY_POLICY.getName());
	}

	/// Check that HTTP response **does not** contains `"Content-Security-Policy"` header.
	///
	/// For additional details about the `Content-Security-Policy` header, check:
	/// - [WHATWG CSP](https://w3c.github.io/webappsec-csp/)
	/// - [WHATWG CSP2](https://w3c.github.io/webappsec-csp/2/)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Security-Policy)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveContentSecurityPolicy(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CONTENT_SECURITY_POLICY.getName());
	}

	/// Check that HTTP response contains `"Content-Security-Policy"` header with
	/// expected value.
	///
	/// Note that according to latest [specification](https://w3c.github.io/webappsec-csp/):
	/// - Directive name are **case-insensitive**.
	/// - Directives order does not matter.
	///
	/// So for example, following values are equivalent:
	/// - `"default-src 'none'; script-src 'self' 'unsafe-inline'`
	/// - `"default-src 'none'; script-src 'unsafe-inline' 'self'`
	/// - `"script-src 'self' 'unsafe-inline'; default-src 'none';`
	///
	/// For additional details about the `Content-Security-Policy` header, check:
	/// - [WHATWG CSP](https://w3c.github.io/webappsec-csp/)
	/// - [WHATWG CSP2](https://w3c.github.io/webappsec-csp/2/)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Security-Policy)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentSecurityPolicy Cache-Control value.
	/// @return Assertion result.
	public AssertionResult isContentSecurityPolicyEqualTo(HttpResponse httpResponse, String contentSecurityPolicy) {
		ContentSecurityPolicy csp = ContentSecurityPolicy.parser().parse(contentSecurityPolicy);
		return isContentSecurityPolicyEqualTo(httpResponse, csp);
	}

	/// Check that HTTP response contains `"Content-Security-Policy"` header with
	/// expected [ContentSecurityPolicy] value.
	///
	/// For additional details about the `Content-Security-Policy` header, check:
	/// - [WHATWG CSP](https://w3c.github.io/webappsec-csp/)
	/// - [WHATWG CSP2](https://w3c.github.io/webappsec-csp/2/)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Security-Policy)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentSecurityPolicy Cache-Control value.
	/// @return Assertion result.
	public AssertionResult isContentSecurityPolicyEqualTo(HttpResponse httpResponse, ContentSecurityPolicy contentSecurityPolicy) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(CONTENT_SECURITY_POLICY.getName(), contentSecurityPolicy, ContentSecurityPolicy.parser()));
	}

	/// Check that HTTP response contains `"X-XSS-Protection"` header, no matter what value.
	///
	/// For additional details about the `X-XSS-Protection` header, check [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-XSS-Protection)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasXssProtection(HttpResponse httpResponse) {
		return hasHeader(httpResponse, X_XSS_PROTECTION.getName());
	}

	/// Check that http response **does not** contains `"X-XSS-Protection"` header.
	///
	/// For additional details about the `X-XSS-Protection` header, check [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-XSS-Protection)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveXssProtection(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, X_XSS_PROTECTION.getName());
	}

	/// Check that HTTP response contains `"X-XSS-Protection"` header with
	/// expected value.
	///
	/// Please, note that:
	/// - Header value is **case-insensitive**.
	/// - A non-authorize value will fail, as it is probably a mistake.
	///
	/// Following values are equivalent:
	/// - `"1; mode=block"`
	/// - `"1; MODE=BLOCK"`
	///
	/// For additional details about the `X-XSS-Protection` header, check [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-XSS-Protection)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param xssProtection Expected `"X-XSS-Protection"` header value.
	/// @return Assertion result.
	public AssertionResult isXssProtectionEqualTo(HttpResponse httpResponse, String xssProtection) {
		return isXssProtectionEqualTo(httpResponse, XssProtection.parser().parse(xssProtection));
	}

	/// Check that HTTP response contains `"X-XSS-Protection"` header
	/// with [XssProtection] value.
	///
	/// For additional details about the `X-XSS-Protection` header, check [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/X-XSS-Protection)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param xssProtection Expected `"X-XSS-Protection"` header value.
	/// @return Assertion result.
	public AssertionResult isXssProtectionEqualTo(HttpResponse httpResponse, XssProtection xssProtection) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(X_XSS_PROTECTION.getName(), xssProtection, XssProtection.parser()));
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Origin"` header, no matter
	/// what value.
	///
	/// For additional details about the `Access-Control-Allow-Origin` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-origin)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Origin)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasAccessControlAllowOrigin(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName());
	}

	/// Check that HTTP response **does not** contains `"Access-Control-Allow-Origin"` header.
	///
	/// For additional details about the `Access-Control-Allow-Origin` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-origin)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Origin)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveAccessControlAllowOrigin(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName());
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Origin"` header
	/// with expected value.
	///
	/// For additional details about the `Access-Control-Allow-Origin` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-origin)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Origin)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param accessControlAllowOrigin Header value.
	/// @return Assertion result.
	public AssertionResult isAccessControlAllowOriginEqualTo(HttpResponse httpResponse, String accessControlAllowOrigin) {
		return isHeaderEqualTo(httpResponse, ACCESS_CONTROL_ALLOW_ORIGIN.getName(), accessControlAllowOrigin, false);
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Headers"` header, no matter what value.
	///
	/// For additional details about the `Access-Control-Allow-Headers` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-headers)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Headers)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasAccessControlAllowHeaders(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_HEADERS.getName());
	}

	/// Check that HTTP response **does not** contains `"Access-Control-Allow-Headers"` header.
	///
	/// For additional details about the `Access-Control-Allow-Headers` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-headers)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Headers)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveAccessControlAllowHeaders(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_HEADERS.getName());
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Headers"` header with
	/// expected values (note that order does not matter).
	///
	/// Note that since header names are case-insensitive, assertion is also **case-insensitive**.
	///
	/// For additional details about the `Access-Control-Allow-Headers` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-headers)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Headers)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param value Header value.
	/// @param other Other (optional) values.
	/// @return Assertion result.
	public AssertionResult isAccessControlAllowHeadersEqualTo(HttpResponse httpResponse, String value, String... other) {
		return isAccessControlAllowHeadersEqualTo(httpResponse, toList(value, other).stream());
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Headers"` header with expected
	/// list of values.
	///
	/// Note that since header names are case-insensitive, assertion is also **case-insensitive**.
	///
	/// For additional details about the `Access-Control-Allow-Headers` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-headers)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Headers)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param accessControlAllowHeaders Header value.
	/// @return Assertion result.
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

	/// Check that HTTP response contains `"Access-Control-Expose-Headers"` header, no matter
	/// what value.
	///
	/// For additional details about the `Access-Control-Expose-Headers` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-expose-headers)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Expose-Headers)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasAccessControlExposeHeaders(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_EXPOSE_HEADERS.getName());
	}

	/// Check that HTTP response **does not** contains `"Access-Control-Expose-Headers"` header.
	///
	/// For additional details about the `Access-Control-Expose-Headers` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-expose-headers)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Expose-Headers)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveAccessControlExposeHeaders(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_EXPOSE_HEADERS.getName());
	}

	/// Check that HTTP response contains `"Access-Control-Expose-Headers"` header with expected
	/// value(s).
	///
	/// Note that since header names are case-insensitive, assertion is also **case-insensitive**.
	///
	/// For additional details about the `Access-Control-Expose-Headers` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-expose-headers)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Expose-Headers)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param value Header value.
	/// @param other Other (optional) values.
	/// @return Assertion result.
	public AssertionResult isAccessControlExposeHeadersEqualTo(HttpResponse httpResponse, String value, String... other) {
		return isAccessControlExposeHeadersEqualTo(httpResponse, toList(value, other).stream());
	}

	/// Check that HTTP response contains `"Access-Control-Expose-Headers"` header with expected
	/// list of value.
	///
	/// Note that since header names are case-insensitive, assertion is also **case-insensitive**.
	///
	/// For additional details about the `Access-Control-Expose-Headers` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-expose-headers)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Expose-Headers)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param accessControlExposeHeaders Header value.
	/// @return Assertion result.
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

	/// Check that HTTP response contains `"Access-Control-Allow-Methods"` header, no matter
	/// what value.
	///
	/// For additional details about the `Access-Control-Allow-Methods` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-methods)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Methods)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasAccessControlAllowMethods(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_METHODS.getName());
	}

	/// Check that HTTP response **does not** contains `"Access-Control-Allow-Methods"` header.
	///
	/// For additional details about the `Access-Control-Allow-Methods` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-methods)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Methods)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveAccessControlAllowMethods(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_METHODS.getName());
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Methods"` header with
	/// expected values.
	///
	/// For additional details about the `Access-Control-Allow-Methods` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-methods)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Methods)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param method HTTP method.
	/// @param other Other, optional, HTTP method.
	/// @return Assertion result.
	public AssertionResult isAccessControlAllowMethodsEqualTo(HttpResponse httpResponse, RequestMethod method, RequestMethod... other) {
		return isAccessControlAllowMethodsEqualTo(httpResponse, toList(method, other));
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Methods"` header with
	/// expected values (note that order of methods does not matter).
	///
	/// For additional details about the `Access-Control-Allow-Methods` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-methods)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Methods)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param methods HTTP Methods.
	/// @return Assertion result.
	public AssertionResult isAccessControlAllowMethodsEqualTo(HttpResponse httpResponse, Iterable<RequestMethod> methods) {
		List<String> verbs = stream(methods.spliterator(), false).map(RequestMethod::verb).collect(Collectors.toList());
		return assertWith(httpResponse, new IsHeaderListEqualToAssertion(ACCESS_CONTROL_ALLOW_METHODS.getName(), verbs));
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Credentials"` header, no matter
	/// what value.
	///
	/// For additional details about the `Access-Control-Allow-Credentials` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-credentials)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasAccessControlAllowCredentials(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_CREDENTIALS.getName());
	}

	/// Check that HTTP response **does not** contains `"Access-Control-Allow-Credentials"` header.
	///
	/// For additional details about the `Access-Control-Allow-Credentials` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-credentials)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveAccessControlAllowCredentials(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_CREDENTIALS.getName());
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Credentials"` header with
	/// expected value.
	///
	/// For additional details about the `Access-Control-Allow-Credentials` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-credentials)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param flag Flag value.
	/// @return Assertion result.
	public AssertionResult isAccessControlAllowCredentialsEqualTo(HttpResponse httpResponse, boolean flag) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(ACCESS_CONTROL_ALLOW_CREDENTIALS.getName(), Boolean.toString(flag), false));
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Max-Age"` header, no matter
	/// what value.
	///
	/// For additional details about the `Access-Control-Allow-Max-Age` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-max-age)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Max-Age)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasAccessControlAllowMaxAge(HttpResponse httpResponse) {
		return hasHeader(httpResponse, ACCESS_CONTROL_ALLOW_MAX_AGE.getName());
	}

	/// Check that HTTP response **does not** contains `"Access-Control-Allow-Max-Age"` header.
	///
	/// For additional details about the `Access-Control-Allow-Max-Age` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-max-age)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Max-Age)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveAccessControlAllowMaxAge(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, ACCESS_CONTROL_ALLOW_MAX_AGE.getName());
	}

	/// Check that HTTP response contains `"Access-Control-Allow-Max-Age"` header with
	/// expected value.
	///
	/// For additional details about the `Access-Control-Allow-Max-Age` header, check:
	/// - [WHATWG](https://fetch.spec.whatwg.org/#http-access-control-allow-max-age)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Access-Control-Allow-Max-Age)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param maxAge Max age (in seconds).
	/// @return Assertion result.
	public AssertionResult isAccessControlAllowMaxAgeEqualTo(HttpResponse httpResponse, long maxAge) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(ACCESS_CONTROL_ALLOW_MAX_AGE.getName(), Long.toString(maxAge), false));
	}

	/// Check that HTTP response contains `"Strict-Transport-Security"` header.
	///
	/// For additional details about the `Strict-Transport-Security` header, check:
	/// - [RFC 6797](https://tools.ietf.org/html/rfc6797)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Strict-Transport-Security)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasStrictTransportSecurity(HttpResponse httpResponse) {
		return hasHeader(httpResponse, STRICT_TRANSPORT_SECURITY.getName());
	}

	/// Check that HTTP response **does not** contains `"Strict-Transport-Security"` header.
	///
	/// For additional details about the `Strict-Transport-Security` header, check:
	/// - [RFC 6797](https://tools.ietf.org/html/rfc6797)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Strict-Transport-Security)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveStrictTransportSecurity(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, STRICT_TRANSPORT_SECURITY.getName());
	}

	/// Check that HTTP response contains `"Strict-Transport-Security"` header with expected value.
	///
	/// Note that, according to the [specification](https://tools.ietf.org/html/rfc6797):
	/// - Directive order does not matter.
	/// - Directive value (such as `"max-age"`) may be quoted, or not.
	/// - Directive names are case-insensitive.
	///
	/// For additional details about the `Strict-Transport-Security` header, check:
	/// - [RFC 6797](https://tools.ietf.org/html/rfc6797)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Strict-Transport-Security)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param strictTransportSecurity Strict-Transport-Security value.
	/// @return Assertion result.
	/// For additional details about the `Strict-Transport-Security` header, check:
	/// - [RFC 6797](https://tools.ietf.org/html/rfc6797)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Strict-Transport-Security)
	public AssertionResult isStrictTransportSecurityEqualTo(HttpResponse httpResponse, String strictTransportSecurity) {
		StrictTransportSecurity sts = StrictTransportSecurity.parser().parse(strictTransportSecurity);
		return isStrictTransportSecurityEqualTo(httpResponse, sts);
	}

	/// Check that HTTP response contains `"Strict-Transport-Security"` header with expected value.
	///
	/// For additional details about the `Strict-Transport-Security` header, check:
	/// - [RFC 6797](https://tools.ietf.org/html/rfc6797)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Strict-Transport-Security)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param strictTransportSecurity Strict-Transport-Security value.
	/// @return Assertion result.
	public AssertionResult isStrictTransportSecurityEqualTo(HttpResponse httpResponse, StrictTransportSecurity strictTransportSecurity) {
		return assertWith(httpResponse, new IsHeaderMatchingAssertion(STRICT_TRANSPORT_SECURITY.getName(), strictTransportSecurity, StrictTransportSecurity.parser()));
	}

	/// Check that HTTP response contains `"Pragma"` header, no matter what values.
	///
	/// For additional details about the `Pragma` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.4)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Pragma)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasPragma(HttpResponse httpResponse) {
		return hasHeader(httpResponse, PRAGMA.getName());
	}

	/// Check that HTTP response **does not** contains `"Pragma"` header.
	///
	/// For additional details about the `Pragma` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.4)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Pragma)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHavePragma(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, PRAGMA.getName());
	}

	/// Check that HTTP response contains `"Pragma"` header with expected value.
	///
	/// For additional details about the `Pragma` header, check:
	/// - [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.4)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Pragma)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param pragma Pragma value.
	/// @return Assertion result.
	public AssertionResult isPragmaEqualTo(HttpResponse httpResponse, String pragma) {
		return isHeaderEqualTo(httpResponse, PRAGMA.getName(), pragma, false);
	}

	/// Check that HTTP response contains `"Content-Disposition"` header, no matter what value.
	///
	/// For additional details about the `Content-Disposition` header, check:
	/// - [RFC 6266](https://tools.ietf.org/html/rfc6266)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Disposition)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult hasContentDisposition(HttpResponse httpResponse) {
		return hasHeader(httpResponse, CONTENT_DISPOSITION.getName());
	}

	/// Check that HTTP response **does not** contains `"Content-Disposition"` header.
	///
	/// For additional details about the `Content-Disposition` header, check:
	/// - [RFC 6266](https://tools.ietf.org/html/rfc6266)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Disposition)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveContentDisposition(HttpResponse httpResponse) {
		return doesNotHaveHeader(httpResponse, CONTENT_DISPOSITION.getName());
	}

	/// Check that HTTP response contains `"Content-Disposition"` header with
	/// expected value.
	///
	/// For additional details about the `Content-Disposition` header, check:
	/// - [RFC 6266](https://tools.ietf.org/html/rfc6266)
	/// - [MDN](https://developer.mozilla.org/en/docs/Web/HTTP/Headers/Content-Disposition)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param contentDispositionValue Expected value.
	/// @return Assertion result.
	public AssertionResult isContentDispositionEqualTo(HttpResponse httpResponse, String contentDispositionValue) {
		return isHeaderEqualTo(httpResponse, CONTENT_DISPOSITION.getName(), contentDispositionValue);
	}

	/// Check that HTTP response has a mime-type equals (ignoring case) to `"application/json"`.
	///
	/// The mime type of an HTTP response is specified in the `"Content-Type"` header, and is
	/// case-insensitive.
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isJson(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, JSON);
	}

	/// Check that HTTP response has a mime-type corresponding to an XML document, meaning one of the following
	/// values (ignoring case):
	/// - `"application/xml"`
	/// - `"text/xml"`
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isXml(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(APPLICATION_XML, TEXT_XML));
	}

	/// Check that HTTP response has a mime-type equals (ignoring case) to `"text/css"`.
	///
	/// The mime type of an HTTP response is specified in the `"Content-Type"` header, and is
	/// case-insensitive.
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isCss(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, CSS);
	}

	/// Check that HTTP response has a mime-type equals (ignoring case) to `"text/plain"`.
	///
	/// The mime type of an HTTP response is specified in the `"Content-Type"` header, and is
	/// case-insensitive.
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isText(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, TEXT_PLAIN);
	}

	/// Check that HTTP response has a mime-type equals (ignoring case) to `"text/csv"`.
	///
	/// The mime type of an HTTP response is specified in the `"Content-Type"` header, and is
	/// case-insensitive.
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isCsv(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, CSV);
	}

	/// Check that HTTP response has a mime-type equals (ignoring case) to `"application/pdf"`.
	///
	/// The mime type of an HTTP response is specified in the `"Content-Type"` header, and is
	/// case-insensitive.
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isPdf(HttpResponse httpResponse) {
		return hasMimeType(httpResponse, PDF);
	}

	/// Check that HTTP response has a mime-type corresponding to an HTML document, meaning one of
	/// the following values (ignoring case):
	/// - `"text/html"`
	/// - `"application/xhtml+xml"`
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isHtml(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(TEXT_HTML, XHTML));
	}

	/// Check that HTTP response has `UTF-8` charset (the response charset is the one specified in
	/// the `"Content-Type"` header).
	///
	/// For additional details about `Content-Type` header, check:
	/// - [RFC 1341](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isUtf8(HttpResponse httpResponse) {
		return hasCharset(httpResponse, StandardCharsets.UTF_8);
	}

	/// Check that http response is `"application/javascript"` or `"text/javascript"`.
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult isJavascript(HttpResponse httpResponse) {
		return hasMimeTypeIn(httpResponse, asList(
			APPLICATION_JAVASCRIPT,
			TEXT_JAVASCRIPT
		));
	}

	/// Check that HTTP response contains expected header (note that header name are case-insensitive).
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param headerName Header name.
	/// @return Assertion result.
	public AssertionResult hasHeader(HttpResponse httpResponse, String headerName) {
		return assertWith(httpResponse, new HasHeaderAssertion(headerName));
	}

	/// Check that HTTP response **does not** contains expected header (note that header name are
	/// case-insensitive).
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param headerName Header name.
	/// @return Assertion result.
	public AssertionResult doesNotHaveHeader(HttpResponse httpResponse, String headerName) {
		return assertWith(httpResponse, new DoesNotHaveHeaderAssertion(headerName));
	}

	/// Check that HTTP response contains expected header with expected value.
	/// Note that:
	/// - Header name is case-insensitive.
	/// - Value **comparison is strict**: the exact string values will be compared.
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param headerName Header name.
	/// @param headerValue Header value.
	/// @return Assertion result.
	public AssertionResult isHeaderEqualTo(HttpResponse httpResponse, String headerName, String headerValue) {
		return isHeaderEqualTo(httpResponse, headerName, headerValue, false);
	}

	private AssertionResult isHeaderEqualTo(HttpResponse httpResponse, String headerName, String headerValue, boolean caseInsensitive) {
		return assertWith(httpResponse, new IsHeaderEqualToAssertion(headerName, headerValue, caseInsensitive));
	}

	/// Check that http response is of expected mime type (a.k.a media type).
	///
	/// Note that comparison is **case insensitive**, so calling assertion with `"application/json"` is
	/// equivalent to `"APPLICATION/JSON"`.
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param expectedMimeType Expected mime type.
	/// @return Assertion result.
	public AssertionResult hasMimeType(HttpResponse httpResponse, String expectedMimeType) {
		MediaType expected = MediaType.parser().parse(expectedMimeType);
		return assertWith(httpResponse, new HasMimeTypeAssertion(expected));
	}

	/// Check that HTTP response is of expected mime type (a.k.a media type).
	///
	/// For additional details about Mime Types, check:
	/// - [Content-Type specification](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	/// - [List of mime-types](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param expectedMimeType Expected mime type.
	/// @return Assertion result.
	public AssertionResult hasMimeType(HttpResponse httpResponse, MediaType expectedMimeType) {
		return assertWith(httpResponse, new HasMimeTypeAssertion(expectedMimeType));
	}

	private AssertionResult hasMimeTypeIn(HttpResponse httpResponse, List<MediaType> expectedMimeTypes) {
		return assertWith(httpResponse, new HasMimeTypeAssertion(expectedMimeTypes));
	}

	/// Check that HTTP response has expected charset (the response charset is the one specified in
	/// the `"Content-Type"` header).
	///
	/// For additional details about `Content-Type` header, check:
	/// - [RFC 1341](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param expectedCharset Expected charset.
	/// @return Assertion result.
	public AssertionResult hasCharset(HttpResponse httpResponse, Charset expectedCharset) {
		return hasCharset(httpResponse, expectedCharset.name().toLowerCase());
	}

	/// Check that HTTP response has expected charset (the response charset is the one specified in
	/// the `"Content-Type"` header).
	///
	/// Note that charset is case-insensitive, so calling the assertion with `"utf-8"` or `"UTF-8"` produce the same
	/// result.
	///
	/// For additional details about `Content-Type` header, check:
	/// - [RFC 1341](https://www.w3.org/Protocols/rfc1341/4_Content-Type.html)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param expectedCharset Expected charset.
	/// @return Assertion result.
	public AssertionResult hasCharset(HttpResponse httpResponse, String expectedCharset) {
		return assertWith(httpResponse, new HasCharsetAssertion(expectedCharset));
	}

	/// Check that HTTP response does not contains any cookies.
	///
	/// For additional details about Cookies, check:
	/// - [RFC 6265](https://tools.ietf.org/html/rfc6265#section-5.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @return Assertion result.
	public AssertionResult doesNotHaveCookie(HttpResponse httpResponse) {
		return assertWith(httpResponse, new DoesNotHaveCookieAssertion());
	}

	/// Check that HTTP response does not contains cookie with given name (note that cookie name
	/// is case-sensitive).
	///
	/// For additional details about Cookies, check:
	/// - [RFC 6265](https://tools.ietf.org/html/rfc6265#section-5.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param name Cookie name.
	/// @return Assertion result.
	public AssertionResult doesNotHaveCookie(HttpResponse httpResponse, String name) {
		return assertWith(httpResponse, new DoesNotHaveCookieAssertion(name));
	}

	/// Check that HTTP response contains cookie with given name (note that cookie name is
	/// case-sensitive).
	///
	/// For additional details about Cookies, check:
	/// - [RFC 6265](https://tools.ietf.org/html/rfc6265#section-5.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param name Cookie name.
	/// @return Assertion result.
	public AssertionResult hasCookie(HttpResponse httpResponse, String name) {
		return assertWith(httpResponse, new HasCookieAssertion(name));
	}

	/// Check that HTTP response contains cookie with given name and value (note that cookie name
	/// is case-sensitive).
	///
	/// For additional details about Cookies, check:
	/// - [RFC 6265](https://tools.ietf.org/html/rfc6265#section-5.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param name Cookie name.
	/// @param value Cookie value.
	/// @return Assertion result.
	public AssertionResult hasCookie(HttpResponse httpResponse, String name, String value) {
		return assertWith(httpResponse, new HasCookieAssertion(name, value));
	}

	/// Check that HTTP response contains given cookie.
	///
	/// For additional details about Cookies, check:
	/// - [RFC 6265](https://tools.ietf.org/html/rfc6265#section-5.4)
	/// - [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie)
	///
	/// @param httpResponse HTTP response to be tested.
	/// @param cookie Cookie.
	/// @return Assertion result.
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
