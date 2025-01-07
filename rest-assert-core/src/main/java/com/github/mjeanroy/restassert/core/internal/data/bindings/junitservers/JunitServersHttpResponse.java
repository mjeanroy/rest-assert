package com.github.mjeanroy.restassert.core.internal.data.bindings.junitservers;

import com.github.mjeanroy.junit.servers.client.HttpHeader;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.AbstractHttpResponse;

import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Implementation to integrate junit-servers into rest-assert.
 */
public class JunitServersHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link HttpResponse} using instance of {@link com.github.mjeanroy.junit.servers.client.HttpResponse},
	 * or returns {@code null} if {@code response} is {@code null}.
	 *
	 * @param response Original response instance..
	 * @return Http response that can be used with rest-assert.
	 */
	public static JunitServersHttpResponse create(com.github.mjeanroy.junit.servers.client.HttpResponse response) {
		return response == null ? null : new JunitServersHttpResponse(response);
	}

	/**
	 * The original response.
	 */
	private final com.github.mjeanroy.junit.servers.client.HttpResponse response;

	// Use static factory
	private JunitServersHttpResponse(com.github.mjeanroy.junit.servers.client.HttpResponse response) {
		this.response = notNull(response, "Response must not be null");
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
