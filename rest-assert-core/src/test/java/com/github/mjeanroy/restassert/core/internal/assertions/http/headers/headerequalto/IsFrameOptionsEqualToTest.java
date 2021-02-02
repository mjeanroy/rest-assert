/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.assertions.http.headers.headerequalto;

import com.github.mjeanroy.restassert.core.data.FrameOptions;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Test;

import static com.github.mjeanroy.restassert.core.data.FrameOptions.allowFrom;
import static com.github.mjeanroy.restassert.core.data.FrameOptions.sameOrigin;
import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.X_FRAME_OPTIONS;
import static org.assertj.core.api.Assertions.assertThat;

public class IsFrameOptionsEqualToTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = X_FRAME_OPTIONS;
	private static final String NAME = HEADER.getName();
	private static final FrameOptions VALUE = FrameOptions.deny();
	private static final FrameOptions FAILED_VALUE = FrameOptions.sameOrigin();

	@Override
	protected Header getHeader() {
		return HEADER;
	}

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isFrameOptionsEqualTo(response, VALUE);
	}

	@Override
	protected boolean allowMultipleValues() {
		return true;
	}

	@Override
	String failValue() {
		return FAILED_VALUE.serializeValue();
	}

	@Test
	public void it_should_check_that_allow_from_value_match() {
		// GIVEN
		final String uri = "https://www.google.com";
		final String actual = "allow-from " + uri;
		final FrameOptions expected = allowFrom(uri);
		final HttpResponse httpResponse = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();

		// WHEN
		final AssertionResult r = assertions.isFrameOptionsEqualTo(httpResponse, expected);

		// THEN
		assertThat(r).isNotNull();
		assertThat(r.isSuccess()).isTrue();
		assertThat(r.isFailure()).isFalse();
	}

	@Test
	public void it_should_check_that_allow_from_value_does_not_match() {
		// GIVEN
		final String actual = "deny";
		final FrameOptions expected = sameOrigin();
		final HttpResponse httpResponse = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();

		// WHEN
		final AssertionResult r = assertions.isFrameOptionsEqualTo(httpResponse, expected);

		// THEN
		assertThat(r).isNotNull();
		assertThat(r.isSuccess()).isFalse();
		assertThat(r.isFailure()).isTrue();
	}
}
