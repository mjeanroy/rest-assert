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

import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.tests.builders.apache.ApacheHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.async.AsyncHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.google.GoogleHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.junitservers.JunitServersHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.ning.NingHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.ok.OkHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.spring.SpringMockMvcHttpResponseBuilder;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

final class HttpMatchers {

	private static final HttpMatcher<okhttp3.Response> OK_HTTP = generate(
		OkHttpResponseMatchers.class,
		okhttp3.Response.class,
		OkHttpResponseBuilder.class
	);

	private static final HttpMatcher<org.apache.http.HttpResponse> APACHE_HTTP = generate(
		ApacheHttpResponseMatchers.class,
		org.apache.http.HttpResponse.class,
		ApacheHttpResponseBuilder.class
	);

	private static final HttpMatcher<org.asynchttpclient.Response> ASYNC_HTTP = generate(
		AsyncHttpResponseMatchers.class,
		org.asynchttpclient.Response.class,
		AsyncHttpResponseBuilder.class
	);

	private static final HttpMatcher<com.ning.http.client.Response> NING_HTTP = generate(
		NingHttpResponseMatchers.class,
		com.ning.http.client.Response.class,
		NingHttpResponseBuilder.class
	);

	private static final HttpMatcher<com.google.api.client.http.HttpResponse> GOOGLE_HTTP = generate(
		GoogleHttpResponseMatchers.class,
		com.google.api.client.http.HttpResponse.class,
		GoogleHttpResponseBuilder.class
	);

	private static final HttpMatcher<com.github.mjeanroy.junit.servers.client.HttpResponse> JUNIT_SERVERS = generate(
		JunitServersHttpResponseMatchers.class,
		com.github.mjeanroy.junit.servers.client.HttpResponse.class,
		JunitServersHttpResponseBuilder.class
	);

	private static final HttpMatcher<com.github.mjeanroy.restassert.core.internal.data.HttpResponse> CORE_HTTP = generate(
		com.github.mjeanroy.restassert.hamcrest.api.http.HttpResponseMatchers.class,
		com.github.mjeanroy.restassert.core.internal.data.HttpResponse.class,
		HttpResponseBuilderImpl.class
	);

	private static final HttpMatcher<org.springframework.test.web.servlet.ResultActions> SPRING_MVC_MOCK = generate(
		SpringMockMvcHttpResponseMatchers.class,
		org.springframework.test.web.servlet.ResultActions.class,
		SpringMockMvcHttpResponseBuilder.class
	);

	private HttpMatchers() {
	}

	static HttpMatcher<okhttp3.Response> okHttpMatcher() {
		return OK_HTTP;
	}

	static HttpMatcher<org.apache.http.HttpResponse> apacheHttpMatcher() {
		return APACHE_HTTP;
	}

	static HttpMatcher<org.asynchttpclient.Response> asyncHttpMatcher() {
		return ASYNC_HTTP;
	}

	static HttpMatcher<com.ning.http.client.Response> ningHttpMatcher() {
		return NING_HTTP;
	}

	static HttpMatcher<com.google.api.client.http.HttpResponse> googleHttpMatcher() {
		return GOOGLE_HTTP;
	}

	static HttpMatcher<com.github.mjeanroy.junit.servers.client.HttpResponse> junitServersHttpMatcher() {
		return JUNIT_SERVERS;
	}

	static HttpMatcher<com.github.mjeanroy.restassert.core.internal.data.HttpResponse> coreHttpMatcher() {
		return CORE_HTTP;
	}

	static HttpMatcher<org.springframework.test.web.servlet.ResultActions> springMockMvcHttpMatcher() {
		return SPRING_MVC_MOCK;
	}

	@SuppressWarnings("unchecked")
	private static <T> HttpMatcher<T> generate(
		Class<?> klass,
		Class<T> implClass,
		Class<? extends HttpResponseBuilder<T>> httpResponseBuilderClass
	) {
		TypeDescription.Generic type = TypeDescription.Generic.Builder
			.parameterizedType(HttpMatcher.class, implClass)
			.build();

		Class<?> dynamicType = new ByteBuddy()
			.subclass(Object.class)
			.implement(type)

			// Proxy everything by default
			.method(
				ElementMatchers.not(
					ElementMatchers.named("hashCode")
						.or(ElementMatchers.named("equals"))
						.or(ElementMatchers.named("clone"))
				)
			)
			.intercept(MethodDelegation.to(klass))

			// Proxy the `httpResponseBuilder` instance.
			.method(ElementMatchers.named("httpResponseBuilder"))
			.intercept(MethodDelegation.toConstructor(httpResponseBuilderClass))

			.method(ElementMatchers.named("toString"))
			.intercept(FixedValue.value(klass.getSimpleName()))

			.make()
			.load(implClass.getClassLoader())
			.getLoaded();

		try {
			return (HttpMatcher<T>) dynamicType.getDeclaredConstructor().newInstance();
		}
		catch (Exception ex) {
			throw new AssertionError();
		}
	}
}
