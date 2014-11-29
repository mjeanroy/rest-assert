package com.github.mjeanroy.rest_assert.assertj.api;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

import static com.github.mjeanroy.rest_assert.internal.data.bindings.GoogleHttpResponse.httpResponse;

/**
 * Entry point for assertion methods for Google HttpClient
 * library.
 */
public final class GoogleHttpAssertions {

	// Ensure non instantiation
	private GoogleHttpAssertions() {
	}

	/**
	 * Creates a new instance of {@link HttpResponseAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static HttpResponseAssert assertThat(com.google.api.client.http.HttpResponse actual) {
		HttpResponse httpResponse = httpResponse(actual);
		return new HttpResponseAssert(httpResponse);
	}
}
