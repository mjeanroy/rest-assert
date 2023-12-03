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

final class HttpAsserters {

	private HttpAsserters() {
	}

	private static final HttpAsserter<okhttp3.Response> OK_HTTP = generate(
		OkHttpAssert.class,
		okhttp3.Response.class,
		OkHttpResponseBuilder.class
	);

	private static final HttpAsserter<org.apache.http.HttpResponse> APACHE_HTTP = generate(
		ApacheHttpAssert.class,
		org.apache.http.HttpResponse.class,
		ApacheHttpResponseBuilder.class
	);

	private static final HttpAsserter<org.asynchttpclient.Response> ASYNC_HTTP = generate(
		AsyncHttpAssert.class,
		org.asynchttpclient.Response.class,
		AsyncHttpResponseBuilder.class
	);

	private static final HttpAsserter<com.ning.http.client.Response> NING_HTTP = generate(
		NingHttpAssert.class,
		com.ning.http.client.Response.class,
		NingHttpResponseBuilder.class
	);

	private static final HttpAsserter<com.google.api.client.http.HttpResponse> GOOGLE_HTTP = generate(
		GoogleHttpAssert.class,
		com.google.api.client.http.HttpResponse.class,
		GoogleHttpResponseBuilder.class
	);

	private static final HttpAsserter<com.github.mjeanroy.junit.servers.client.HttpResponse> JUNIT_SERVERS = generate(
		JunitServersHttpAssert.class,
		com.github.mjeanroy.junit.servers.client.HttpResponse.class,
		JunitServersHttpResponseBuilder.class
	);

	private static final HttpAsserter<com.github.mjeanroy.restassert.core.internal.data.HttpResponse> CORE_HTTP = generate(
		HttpAssert.class,
		com.github.mjeanroy.restassert.core.internal.data.HttpResponse.class,
		HttpResponseBuilderImpl.class
	);

	private static final HttpAsserter<org.springframework.test.web.servlet.ResultActions> SPRING_MVC_MOCK = generate(
		SpringMockMvcHttpAssert.class,
		org.springframework.test.web.servlet.ResultActions.class,
		SpringMockMvcHttpResponseBuilder.class
	);

	static HttpAsserter<okhttp3.Response> okHttp() {
		return OK_HTTP;
	}

	static HttpAsserter<org.apache.http.HttpResponse> apacheHttp() {
		return APACHE_HTTP;
	}

	static HttpAsserter<org.asynchttpclient.Response> asyncHttp() {
		return ASYNC_HTTP;
	}

	static HttpAsserter<com.ning.http.client.Response> ningHttp() {
		return NING_HTTP;
	}

	static HttpAsserter<com.google.api.client.http.HttpResponse> googleHttp() {
		return GOOGLE_HTTP;
	}

	static HttpAsserter<com.github.mjeanroy.junit.servers.client.HttpResponse> junitServers() {
		return JUNIT_SERVERS;
	}

	static HttpAsserter<com.github.mjeanroy.restassert.core.internal.data.HttpResponse> coreHttp() {
		return CORE_HTTP;
	}

	static HttpAsserter<org.springframework.test.web.servlet.ResultActions> springMvcMock() {
		return SPRING_MVC_MOCK;
	}

	@SuppressWarnings("unchecked")
	private static <T> HttpAsserter<T> generate(
		Class<?> klass,
		Class<T> implClass,
		Class<? extends HttpResponseBuilder<T>> httpResponseBuilderClass
	) {
		TypeDescription.Generic type = TypeDescription.Generic.Builder
			.parameterizedType(HttpAsserter.class, implClass)
			.build();

		Class<?> dynamicType = new ByteBuddy()
			.subclass(Object.class)
			.implement(type)

			// Proxy all assertXX methods
			.method(ElementMatchers.nameStartsWith("assert"))
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
			return (HttpAsserter<T>) dynamicType.getDeclaredConstructor().newInstance();
		}
		catch (Exception ex) {
			throw new AssertionError();
		}
	}
}
