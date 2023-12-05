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

package com.github.mjeanroy.restassert.hamcrest.api.http;

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
import org.hamcrest.TypeSafeMatcher;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

public interface HttpMatcher<T> {

	HttpResponseBuilder<T> httpResponseBuilder();

	TypeSafeMatcher<T> hasCharset(String expectedCharset);

	TypeSafeMatcher<T> hasCharset(Charset expectedCharset);

	TypeSafeMatcher<T> isUtf8();

	TypeSafeMatcher<T> isAccepted();

	TypeSafeMatcher<T> isBadRequest();

	TypeSafeMatcher<T> isConflict();

	TypeSafeMatcher<T> isCreated();

	TypeSafeMatcher<T> isForbidden();

	TypeSafeMatcher<T> isGone();

	TypeSafeMatcher<T> isInternalServerError();

	TypeSafeMatcher<T> isMethodNotAllowed();

	TypeSafeMatcher<T> isMovedPermanently();

	TypeSafeMatcher<T> isMovedTemporarily();

	TypeSafeMatcher<T> isMultipleChoices();

	TypeSafeMatcher<T> isNoContent();

	TypeSafeMatcher<T> isNotAcceptable();

	TypeSafeMatcher<T> isNotFound();

	TypeSafeMatcher<T> isNotImplemented();

	TypeSafeMatcher<T> isNotModified();

	TypeSafeMatcher<T> isOk();

	TypeSafeMatcher<T> isPartialContent();

	TypeSafeMatcher<T> isPermanentRedirect();

	TypeSafeMatcher<T> isPreConditionFailed();

	TypeSafeMatcher<T> isRequestedRangeNotSatisfiable();

	TypeSafeMatcher<T> isRequestEntityTooLarge();

	TypeSafeMatcher<T> isRequestTimeout();

	TypeSafeMatcher<T> isRequestUriTooLong();

	TypeSafeMatcher<T> isResetContent();

	TypeSafeMatcher<T> isSeeOther();

	TypeSafeMatcher<T> isStatusEqual(int status);

	TypeSafeMatcher<T> isTemporaryRedirect();

	TypeSafeMatcher<T> isUnauthorized();

	TypeSafeMatcher<T> isUnsupportedMediaType();

	TypeSafeMatcher<T> isClientError();

	TypeSafeMatcher<T> isRedirection();

	TypeSafeMatcher<T> isServerError();

	TypeSafeMatcher<T> isStatusBetween(int start, int end);

	TypeSafeMatcher<T> isSuccess();

	TypeSafeMatcher<T> isNotClientError();

	TypeSafeMatcher<T> isNotRedirection();

	TypeSafeMatcher<T> isNotServerError();

	TypeSafeMatcher<T> isNotSuccess();

	TypeSafeMatcher<T> isStatusOutOf(int start, int end);

	TypeSafeMatcher<T> hasAccessControlAllowCredentials();

	TypeSafeMatcher<T> hasAccessControlAllowHeaders();

	TypeSafeMatcher<T> hasAccessControlAllowMaxAge();

	TypeSafeMatcher<T> hasAccessControlAllowMethods();

	TypeSafeMatcher<T> hasAccessControlAllowOrigin();

	TypeSafeMatcher<T> hasAccessControlExposeHeaders();

	TypeSafeMatcher<T> hasCacheControl();

	TypeSafeMatcher<T> hasContentDisposition();

	TypeSafeMatcher<T> hasContentEncoding();

	TypeSafeMatcher<T> hasContentLength();

	TypeSafeMatcher<T> hasContentSecurityPolicy();

	TypeSafeMatcher<T> hasContentType();

	TypeSafeMatcher<T> hasContentTypeOptions();

	TypeSafeMatcher<T> hasETag();

	TypeSafeMatcher<T> hasExpires();

	TypeSafeMatcher<T> hasFrameOptions();

	TypeSafeMatcher<T> hasHeader(String name);

	TypeSafeMatcher<T> hasLastModified();

	TypeSafeMatcher<T> hasLocation();

	TypeSafeMatcher<T> hasPragma();

	TypeSafeMatcher<T> hasStrictTransportSecurity();

	TypeSafeMatcher<T> hasXssProtection();

	TypeSafeMatcher<T> doesNotHaveAccessControlAllowCredentials();

	TypeSafeMatcher<T> doesNotHaveAccessControlAllowHeaders();

	TypeSafeMatcher<T> doesNotHaveAccessControlAllowMaxAge();

	TypeSafeMatcher<T> doesNotHaveAccessControlAllowMethods();

	TypeSafeMatcher<T> doesNotHaveAccessControlAllowOrigin();

	TypeSafeMatcher<T> doesNotHaveAccessControlExposeHeaders();

	TypeSafeMatcher<T> doesNotHaveCacheControl();

	TypeSafeMatcher<T> doesNotHaveContentDisposition();

	TypeSafeMatcher<T> doesNotHaveContentEncoding();

	TypeSafeMatcher<T> doesNotHaveContentSecurityPolicy();

	TypeSafeMatcher<T> doesNotHaveContentTypeOptions();

	TypeSafeMatcher<T> doesNotHaveETag();

	TypeSafeMatcher<T> doesNotHaveExpires();

	TypeSafeMatcher<T> doesNotHaveFrameOptions();

	TypeSafeMatcher<T> doesNotHaveHeader(String name);

	TypeSafeMatcher<T> doesNotHaveLastModified();

	TypeSafeMatcher<T> doesNotHaveLocation();

	TypeSafeMatcher<T> doesNotHavePragma();

	TypeSafeMatcher<T> doesNotHaveStrictTransportSecurity();

	TypeSafeMatcher<T> doesNotHaveXssProtection();

	TypeSafeMatcher<T> isAccessControlAllowCredentialsEqualTo(boolean value);

	TypeSafeMatcher<T> isAccessControlAllowHeadersEqualTo(List<String> value);

	TypeSafeMatcher<T> isAccessControlAllowHeadersEqualTo(String value, String... other);

	TypeSafeMatcher<T> isAccessControlAllowMaxAgeEqualTo(long value);

	TypeSafeMatcher<T> isAccessControlAllowMethodsEqualTo(List<RequestMethod> methods);

	TypeSafeMatcher<T> isAccessControlAllowMethodsEqualTo(RequestMethod method, RequestMethod... others);

	TypeSafeMatcher<T> isAccessControlAllowOriginEqualTo(String value);

	TypeSafeMatcher<T> isAccessControlExposeHeadersEqualTo(List<String> value);

	TypeSafeMatcher<T> isAccessControlExposeHeadersEqualTo(String value, String... other);

	TypeSafeMatcher<T> isCacheControlEqualTo(CacheControl value);

	TypeSafeMatcher<T> isCacheControlEqualTo(String value);

	TypeSafeMatcher<T> isContentDispositionEqualTo(String value);

	TypeSafeMatcher<T> isContentEncodingEqualTo(ContentEncoding value);

	TypeSafeMatcher<T> isContentEncodingEqualTo(String value);

	TypeSafeMatcher<T> isContentSecurityPolicyEqualTo(ContentSecurityPolicy value);

	TypeSafeMatcher<T> isContentSecurityPolicyEqualTo(String value);

	TypeSafeMatcher<T> isContentTypeEqualTo(ContentType value);

	TypeSafeMatcher<T> isContentTypeEqualTo(String value);

	TypeSafeMatcher<T> isContentTypeOptionsEqualTo(ContentTypeOptions value);

	TypeSafeMatcher<T> isContentTypeOptionsEqualTo(String value);

	TypeSafeMatcher<T> isETagEqualTo(String value);

	TypeSafeMatcher<T> isExpiresEqualTo(String value);

	TypeSafeMatcher<T> isExpiresEqualTo(Date value);

	TypeSafeMatcher<T> isFrameOptionsEqualTo(FrameOptions value);

	TypeSafeMatcher<T> isFrameOptionsEqualTo(String value);

	TypeSafeMatcher<T> isGzipped();

	TypeSafeMatcher<T> isHeaderEqualTo(String name, String value);

	TypeSafeMatcher<T> isLastModifiedEqualTo(String value);

	TypeSafeMatcher<T> isLastModifiedEqualTo(Date value);

	TypeSafeMatcher<T> isLocationEqualTo(String value);

	TypeSafeMatcher<T> isPragmaEqualTo(String value);

	TypeSafeMatcher<T> isStrictTransportSecurityEqualTo(StrictTransportSecurity value);

	TypeSafeMatcher<T> isStrictTransportSecurityEqualTo(String value);

	TypeSafeMatcher<T> isXssProtectionEqualTo(XssProtection value);

	TypeSafeMatcher<T> isXssProtectionEqualTo(String value);

	TypeSafeMatcher<T> hasMimeType(MediaType value);

	TypeSafeMatcher<T> hasMimeType(String value);

	TypeSafeMatcher<T> isCss();

	TypeSafeMatcher<T> isCsv();

	TypeSafeMatcher<T> isHtml();

	TypeSafeMatcher<T> isJavascript();

	TypeSafeMatcher<T> isJson();

	TypeSafeMatcher<T> isPdf();

	TypeSafeMatcher<T> isText();

	TypeSafeMatcher<T> isXml();

	TypeSafeMatcher<T> doesNotHaveCookie();

	TypeSafeMatcher<T> doesNotHaveCookie(String name);

	TypeSafeMatcher<T> hasCookie(String name);

	TypeSafeMatcher<T> hasCookie(String name, String value);

	TypeSafeMatcher<T> hasCookie(Cookie cookie);
}
