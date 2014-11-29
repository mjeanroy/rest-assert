package com.github.mjeanroy.rest_assert.assertj.api;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.ning.http.client.Response;

import static com.github.mjeanroy.rest_assert.internal.data.bindings.AsyncHttpResponse.httpResponse;

/**
 * Entry point for assertion methods for Async-Http
 * library.
 */
public final class AsyncHttpAssertions {

	// Ensure non instantiation
	private AsyncHttpAssertions() {
	}

	/**
	 * Creates a new instance of {@link HttpResponseAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static HttpResponseAssert assertThat(Response actual) {
		HttpResponse httpResponse = httpResponse(actual);
		return new HttpResponseAssert(httpResponse);
	}
}
