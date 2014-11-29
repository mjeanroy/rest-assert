package com.github.mjeanroy.rest_assert.assertj.api;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

import static com.github.mjeanroy.rest_assert.internal.data.bindings.ApacheHttpResponse.httpResponse;

/**
 * Entry point for assertion methods for Apache HttpClient
 * library.
 */
public final class ApacheHttpAssertions {

	// Ensure non instantiation
	private ApacheHttpAssertions() {
	}

	/**
	 * Creates a new instance of {@link com.github.mjeanroy.rest_assert.assertj.api.HttpResponseAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static HttpResponseAssert assertThat(org.apache.http.HttpResponse actual) {
		HttpResponse httpResponse = httpResponse(actual);
		return new HttpResponseAssert(httpResponse);
	}
}
