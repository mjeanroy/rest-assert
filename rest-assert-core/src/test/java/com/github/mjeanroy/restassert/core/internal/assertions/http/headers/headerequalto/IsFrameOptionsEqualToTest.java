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

	private static final FrameOptions VALUE = FrameOptions.parser().parse("deny");

	@Override
	protected Header getHeader() {
		return X_FRAME_OPTIONS;
	}

	@Override
	protected AssertionResult invoke(HttpResponse response) {
		return assertions.isFrameOptionsEqualTo(response, VALUE);
	}

	@Override
	protected boolean allowMultipleValues() {
		return true;
	}

	@Test
	public void it_should_check_that_allow_from_value_match() {
		String uri = "https://www.google.com";
		HttpResponse httpResponse = new HttpResponseBuilderImpl()
			.addHeader(X_FRAME_OPTIONS.getName(), "allow-from " + uri)
			.build();

		AssertionResult r = assertions.isFrameOptionsEqualTo(httpResponse, allowFrom(uri));

		assertThat(r).isNotNull();
		assertThat(r.isSuccess()).isTrue();
		assertThat(r.isFailure()).isFalse();
	}

	@Test
	public void it_should_check_that_allow_from_value_does_not_match() {
		HttpResponse httpResponse = new HttpResponseBuilderImpl()
			.addHeader(X_FRAME_OPTIONS.getName(), "deny")
			.build();

		AssertionResult r = assertions.isFrameOptionsEqualTo(httpResponse, sameOrigin());

		assertThat(r).isNotNull();
		assertThat(r.isSuccess()).isFalse();
		assertThat(r.isFailure()).isTrue();
	}
}
