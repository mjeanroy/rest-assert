/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.unit.api.http;

import com.github.mjeanroy.restassert.core.data.CacheControl;
import com.github.mjeanroy.restassert.core.data.ContentEncoding;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy;
import com.github.mjeanroy.restassert.core.data.ContentType;
import com.github.mjeanroy.restassert.core.data.ContentTypeOptions;
import com.github.mjeanroy.restassert.core.data.FrameOptions;
import com.github.mjeanroy.restassert.core.data.MediaType;
import com.github.mjeanroy.restassert.core.data.RequestMethod;
import com.github.mjeanroy.restassert.core.data.StrictTransportSecurity;
import com.github.mjeanroy.restassert.core.data.XssProtection;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

public interface HttpAsserter<T> {

	HttpResponseBuilder<T> httpResponseBuilder();

	void assertIsStatusEqual(T actual, int status);

	void assertIsStatusEqual(String message, T actual, int status);

	void assertIsStatusBetween(T actual, int start, int end);

	void assertIsStatusBetween(String message, T actual, int start, int end);

	void assertIsStatusOutOf(T actual, int start, int end);

	void assertIsStatusOutOf(String message, T actual, int start, int end);

	// 2XX
	void assertIsOk(T actual);

	void assertIsOk(String message, T actual);

	void assertIsAccepted(T actual);

	void assertIsAccepted(String message, T actual);

	void assertIsCreated(T actual);

	void assertIsCreated(String message, T actual);

	void assertIsNoContent(T actual);

	void assertIsNoContent(String message, T actual);

	void assertIsPartialContent(T actual);

	void assertIsPartialContent(String message, T actual);

	void assertIsResetContent(T actual);

	void assertIsResetContent(String message, T actual);

	// 3XX
	void assertIsMovedPermanently(T actual);

	void assertIsMovedPermanently(String message, T actual);

	void assertIsMovedTemporarily(T actual);

	void assertIsMovedTemporarily(String message, T actual);

	void assertIsMultipleChoices(T actual);

	void assertIsMultipleChoices(String message, T actual);

	void assertIsNotModified(T actual);

	void assertIsNotModified(String message, T actual);

	void assertIsPermanentRedirect(T actual);

	void assertIsPermanentRedirect(String message, T actual);

	void assertIsSeeOther(T actual);

	void assertIsSeeOther(String message, T actual);

	void assertIsTemporaryRedirect(T actual);

	void assertIsTemporaryRedirect(String message, T actual);

	// 4XX
	void assertIsBadRequest(T actual);

	void assertIsBadRequest(String message, T actual);

	void assertIsForbidden(T actual);

	void assertIsForbidden(String message, T actual);

	void assertIsUnauthorized(T actual);

	void assertIsUnauthorized(String message, T actual);

	void assertIsConflict(T actual);

	void assertIsConflict(String message, T actual);

	void assertIsMethodNotAllowed(T actual);

	void assertIsMethodNotAllowed(String message, T actual);

	void assertIsGone(T actual);

	void assertIsGone(String message, T actual);

	void assertIsNotFound(T actual);

	void assertIsNotFound(String message, T actual);

	void assertIsNotAcceptable(T actual);

	void assertIsNotAcceptable(String message, T actual);

	void assertIsPreConditionFailed(T actual);

	void assertIsPreConditionFailed(String message, T actual);

	void assertIsRequestedRangeNotSatisfiable(T actual);

	void assertIsRequestedRangeNotSatisfiable(String message, T actual);

	void assertIsRequestEntityTooLarge(T actual);

	void assertIsRequestEntityTooLarge(String message, T actual);

	void assertIsRequestTimeout(T actual);

	void assertIsRequestTimeout(String message, T actual);

	void assertIsRequestUriTooLong(T actual);

	void assertIsRequestUriTooLong(String message, T actual);

	void assertIsUnsupportedMediaType(T actual);

	void assertIsUnsupportedMediaType(String message, T actual);

	// 5XX
	void assertIsInternalServerError(T actual);

	void assertIsInternalServerError(String message, T actual);

	void assertIsNotImplemented(T actual);

	void assertIsNotImplemented(String message, T actual);

	// Status range
	void assertIsSuccess(T actual);

	void assertIsSuccess(String message, T actual);

	void assertIsNotSuccess(T actual);

	void assertIsNotSuccess(String message, T actual);

	void assertIsRedirection(T actual);

	void assertIsRedirection(String message, T actual);

	void assertIsNotRedirection(T actual);

	void assertIsNotRedirection(String message, T actual);

	void assertIsClientError(T actual);

	void assertIsClientError(String message, T actual);

	void assertIsNotClientError(T actual);

	void assertIsNotClientError(String message, T actual);

	void assertIsServerError(T actual);

	void assertIsServerError(String message, T actual);

	void assertIsNotServerError(T actual);

	void assertIsNotServerError(String message, T actual);

	// Charsets
	void assertHasCharset(T actual, String charset);

	void assertHasCharset(String message, T actual, String charset);

	void assertHasCharset(T actual, Charset charset);

	void assertHasCharset(String message, T actual, Charset charset);

	void assertIsUtf8(T actual);

	void assertIsUtf8(String message, T actual);

	// Mime Types
	void assertHasMimeType(T actual, String mimeType);

	void assertHasMimeType(String message, T actual, String mimeType);

	void assertHasMimeType(T actual, MediaType mimeType);

	void assertHasMimeType(String message, T actual, MediaType mimeType);

	void assertIsCss(T actual);

	void assertIsCss(String message, T actual);

	void assertIsCsv(T actual);

	void assertIsCsv(String message, T actual);

	void assertIsHtml(T actual);

	void assertIsHtml(String message, T actual);

	void assertIsJavascript(T actual);

	void assertIsJavascript(String message, T actual);

	void assertIsJson(T actual);

	void assertIsJson(String message, T actual);

	void assertIsPdf(T actual);

	void assertIsPdf(String message, T actual);

	void assertIsText(T actual);

	void assertIsText(String message, T actual);

	void assertIsXml(T actual);

	void assertIsXml(String message, T actual);

	// Header equal to
	void assertIsHeaderEqualTo(T actual, String name, String value);

	void assertIsHeaderEqualTo(String message, T actual, String name, String value);

	void assertIsAccessControlAllowCredentialsEqualTo(T actual, boolean value);

	void assertIsAccessControlAllowCredentialsEqualTo(String message, T actual, boolean value);

	void assertIsAccessControlAllowHeadersEqualTo(T actual, String value, String... others);

	void assertIsAccessControlAllowHeadersEqualTo(String message, T actual, String value, String... others);

	void assertIsAccessControlAllowHeadersEqualTo(T actual, List<String> value);

	void assertIsAccessControlAllowHeadersEqualTo(String message, T actual, List<String> value);

	void assertIsAccessControlAllowMaxAgeEqualTo(T actual, long value);

	void assertIsAccessControlAllowMaxAgeEqualTo(String message, T actual, long value);

	void assertIsAccessControlAllowMethodsEqualTo(T actual, List<RequestMethod> methods);

	void assertIsAccessControlAllowMethodsEqualTo(String message, T actual, List<RequestMethod> methods);

	void assertIsAccessControlAllowMethodsEqualTo(T actual, RequestMethod method, RequestMethod... methods);

	void assertIsAccessControlAllowMethodsEqualTo(String message, T actual, RequestMethod method, RequestMethod... methods);

	void assertIsAccessControlAllowOriginEqualTo(T actual, String value);

	void assertIsAccessControlAllowOriginEqualTo(String message, T actual, String value);

	void assertIsAccessControlExposeHeadersEqualTo(T actual, List<String> value);

	void assertIsAccessControlExposeHeadersEqualTo(String message, T actual, List<String> value);

	void assertIsAccessControlExposeHeadersEqualTo(T actual, String value, String... others);

	void assertIsAccessControlExposeHeadersEqualTo(String message, T actual, String value, String... others);

	void assertIsCacheControlEqualTo(T actual, String value);

	void assertIsCacheControlEqualTo(String message, T actual, String value);

	void assertIsCacheControlEqualTo(T actual, CacheControl value);

	void assertIsCacheControlEqualTo(String message, T actual, CacheControl value);

	void assertIsContentDispositionEqualTo(T actual, String value);

	void assertIsContentDispositionEqualTo(String message, T actual, String value);

	void assertIsContentEncodingEqualTo(T actual, String value);

	void assertIsContentEncodingEqualTo(String message, T actual, String value);

	void assertIsContentEncodingEqualTo(T actual, ContentEncoding value);

	void assertIsContentEncodingEqualTo(String message, T actual, ContentEncoding value);

	void assertIsContentSecurityPolicyEqualTo(T actual, String value);

	void assertIsContentSecurityPolicyEqualTo(String message, T actual, String value);

	void assertIsContentSecurityPolicyEqualTo(T actual, ContentSecurityPolicy value);

	void assertIsContentSecurityPolicyEqualTo(String message, T actual, ContentSecurityPolicy value);

	void assertIsContentTypeEqualTo(T actual, String value);

	void assertIsContentTypeEqualTo(String message, T actual, String value);

	void assertIsContentTypeEqualTo(T actual, ContentType value);

	void assertIsContentTypeEqualTo(String message, T actual, ContentType value);

	void assertIsContentTypeOptionsEqualTo(T actual, String value);

	void assertIsContentTypeOptionsEqualTo(String message, T actual, String value);

	void assertIsContentTypeOptionsEqualTo(T actual, ContentTypeOptions value);

	void assertIsContentTypeOptionsEqualTo(String message, T actual, ContentTypeOptions value);

	void assertIsETagEqualTo(T actual, String value);

	void assertIsETagEqualTo(String message, T actual, String value);

	void assertIsExpiresEqualTo(T actual, String value);

	void assertIsExpiresEqualTo(String message, T actual, String value);

	void assertIsExpiresEqualTo(T actual, Date value);

	void assertIsExpiresEqualTo(String message, T actual, Date value);

	void assertIsFrameOptionsEqualTo(T actual, String value);

	void assertIsFrameOptionsEqualTo(String message, T actual, String value);

	void assertIsFrameOptionsEqualTo(T actual, FrameOptions value);

	void assertIsFrameOptionsEqualTo(String message, T actual, FrameOptions value);

	void assertIsGzipped(T actual);

	void assertIsGzipped(String message, T actual);

	void assertIsLastModifiedEqualTo(T actual, String value);

	void assertIsLastModifiedEqualTo(String message, T actual, String value);

	void assertIsLastModifiedEqualTo(T actual, Date value);

	void assertIsLastModifiedEqualTo(String message, T actual, Date value);

	void assertIsLocationEqualTo(T actual, String value);

	void assertIsLocationEqualTo(String message, T actual, String value);

	void assertIsPragmaEqualTo(T actual, String value);

	void assertIsPragmaEqualTo(String message, T actual, String value);

	void assertIsStrictTransportSecurityEqualTo(T actual, String value);

	void assertIsStrictTransportSecurityEqualTo(String message, T actual, String value);

	void assertIsStrictTransportSecurityEqualTo(T actual, StrictTransportSecurity value);

	void assertIsStrictTransportSecurityEqualTo(String message, T actual, StrictTransportSecurity value);

	void assertIsXssProtectionEqualTo(T actual, String value);

	void assertIsXssProtectionEqualTo(String message, T actual, String value);

	void assertIsXssProtectionEqualTo(T actual, XssProtection value);

	void assertIsXssProtectionEqualTo(String message, T actual, XssProtection value);

	// Has header
	void assertHasAccessControlAllowCredentials(T actual);

	void assertHasAccessControlAllowCredentials(String message, T actual);

	void assertHasAccessControlAllowHeaders(T actual);

	void assertHasAccessControlAllowHeaders(String message, T actual);

	void assertHasAccessControlAllowMaxAge(T actual);

	void assertHasAccessControlAllowMaxAge(String message, T actual);

	void assertHasAccessControlAllowMethods(T actual);

	void assertHasAccessControlAllowMethods(String message, T actual);

	void assertHasAccessControlAllowOrigin(T actual);

	void assertHasAccessControlAllowOrigin(String message, T actual);

	void assertHasAccessControlExposeHeaders(T actual);

	void assertHasAccessControlExposeHeaders(String message, T actual);

	void assertHasCacheControl(T actual);

	void assertHasCacheControl(String message, T actual);

	void assertHasContentDisposition(T actual);

	void assertHasContentDisposition(String message, T actual);

	void assertHasContentEncoding(T actual);

	void assertHasContentEncoding(String message, T actual);

	void assertHasContentLength(T actual);

	void assertHasContentLength(String message, T actual);

	void assertHasContentSecurityPolicy(T actual);

	void assertHasContentSecurityPolicy(String message, T actual);

	void assertHasContentTypeOptions(T actual);

	void assertHasContentTypeOptions(String message, T actual);

	void assertHasContentType(T actual);

	void assertHasContentType(String message, T actual);

	void assertHasETag(T actual);

	void assertHasETag(String message, T actual);

	void assertHasExpires(T actual);

	void assertHasExpires(String message, T actual);

	void assertHasFrameOptions(T actual);

	void assertHasFrameOptions(String message, T actual);

	void assertHasHeader(T actual, String name);

	void assertHasHeader(String message, T actual, String name);

	void assertHasLastModified(T actual);

	void assertHasLastModified(String message, T actual);

	void assertHasLocation(T actual);

	void assertHasLocation(String message, T actual);

	void assertHasPragma(T actual);

	void assertHasPragma(String message, T actual);

	void assertHasStrictTransportSecurity(T actual);

	void assertHasStrictTransportSecurity(String message, T actual);

	void assertHasXssProtection(T actual);

	void assertHasXssProtection(String message, T actual);

	// Does not have header
	void assertDoesNotHaveAccessControlAllowCredentials(T actual);

	void assertDoesNotHaveAccessControlAllowCredentials(String message, T actual);

	void assertDoesNotHaveAccessControlAllowHeaders(T actual);

	void assertDoesNotHaveAccessControlAllowHeaders(String message, T actual);

	void assertDoesNotHaveAccessControlAllowMaxAge(T actual);

	void assertDoesNotHaveAccessControlAllowMaxAge(String message, T actual);

	void assertDoesNotHaveAccessControlAllowMethods(T actual);

	void assertDoesNotHaveAccessControlAllowMethods(String message, T actual);

	void assertDoesNotHaveAccessControlAllowOrigin(T actual);

	void assertDoesNotHaveAccessControlAllowOrigin(String message, T actual);

	void assertDoesNotHaveAccessControlExposeHeaders(T actual);

	void assertDoesNotHaveAccessControlExposeHeaders(String message, T actual);

	void assertDoesNotHaveCacheControl(T actual);

	void assertDoesNotHaveCacheControl(String message, T actual);

	void assertDoesNotHaveContentDisposition(T actual);

	void assertDoesNotHaveContentDisposition(String message, T actual);

	void assertDoesNotHaveContentEncoding(T actual);

	void assertDoesNotHaveContentEncoding(String message, T actual);

	void assertDoesNotHaveContentSecurityPolicy(T actual);

	void assertDoesNotHaveContentSecurityPolicy(String message, T actual);

	void assertDoesNotHaveContentTypeOptions(T actual);

	void assertDoesNotHaveContentTypeOptions(String message, T actual);

	void assertDoesNotHaveETag(T actual);

	void assertDoesNotHaveETag(String message, T actual);

	void assertDoesNotHaveExpires(T actual);

	void assertDoesNotHaveExpires(String message, T actual);

	void assertDoesNotHaveFrameOptions(T actual);

	void assertDoesNotHaveFrameOptions(String message, T actual);

	void assertDoesNotHaveHeader(T actual, String name);

	void assertDoesNotHaveHeader(String message, T actual, String name);

	void assertDoesNotHaveLastModified(T actual);

	void assertDoesNotHaveLastModified(String message, T actual);

	void assertDoesNotHaveLocation(T actual);

	void assertDoesNotHaveLocation(String message, T actual);

	void assertDoesNotHavePragma(T actual);

	void assertDoesNotHavePragma(String message, T actual);

	void assertDoesNotHaveStrictTransportSecurity(T actual);

	void assertDoesNotHaveStrictTransportSecurity(String message, T actual);

	void assertDoesNotHaveXssProtection(T actual);

	void assertDoesNotHaveXssProtection(String message, T actual);

	// Cookies
	void assertDoesNotHaveCookie(T actual);

	void assertDoesNotHaveCookie(String message, T actual);

	void assertDoesNotHaveCookie(T actual, String name);

	void assertDoesNotHaveCookie(String message, T actual, String name);

	void assertHasCookie(T actual, String name, String value);

	void assertHasCookie(String message, T actual, String name, String value);

	void assertHasCookie(T actual, String name);

	void assertHasCookie(String message, T actual, String name);

	void assertHasCookie(T actual, Cookie cookie);

	void assertHasCookie(String message, T actual, Cookie cookie);
}
