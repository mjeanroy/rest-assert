package com.github.mjeanroy.restassert.core.internal.data.bindings.junitservers;

import com.github.mjeanroy.junit.servers.client.HttpHeader;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.AbstractHttpResponse;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Implementation to integrate junit-servers into rest-assert.
 */
public class JunitServersHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link HttpResponse} using instance of {@link com.github.mjeanroy.junit.servers.client.HttpResponse} as
	 * underlying implementation.
	 *
	 * @param response Original response instance..
	 * @return Http response that can be used with rest-assert.
	 */
	public static JunitServersHttpResponse create(com.github.mjeanroy.junit.servers.client.HttpResponse response) {
		return new JunitServersHttpResponse(response);
	}

	/**
	 * The original response.
	 */
	private final com.github.mjeanroy.junit.servers.client.HttpResponse response;

	/**
	 * Create response wrapper.
	 *
	 * @param response The original HTTP response.
	 */
	private JunitServersHttpResponse(com.github.mjeanroy.junit.servers.client.HttpResponse response) {
		this.response = response;
	}

	@Override
	protected String doGetContent() {
		return response.body();
	}

	@Override
	public int getStatus() {
		return response.status();
	}

	@Override
	public List<String> getHeader(String name) {
		HttpHeader header = response.getHeader(name);
		if (header == null) {
			return emptyList();
		}

		return unmodifiableList(header.getValues());
	}
}
