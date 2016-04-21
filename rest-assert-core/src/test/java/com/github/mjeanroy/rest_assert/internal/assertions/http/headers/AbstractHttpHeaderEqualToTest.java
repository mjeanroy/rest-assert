/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.assertions.http.headers;

import com.github.mjeanroy.rest_assert.error.http.ShouldHaveHeader;
import com.github.mjeanroy.rest_assert.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult;
import com.github.mjeanroy.rest_assert.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.mocks.HttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import org.junit.Before;
import org.junit.Test;

import static com.github.mjeanroy.rest_assert.tests.models.Header.header;

public abstract class AbstractHttpHeaderEqualToTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@Before
	public void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	public void it_should_pass_with_expected_header() {
		Header header = getHeader();
		AssertionResult result = invoke(newResponse(header));
		checkSuccess(result);
	}

	@Test
	public void it_should_pass_with_expected_header_and_list_of_values() {
		Header h1 = getHeader();
		Header h2 = header(h1.getName(), h1.getValue() + "foobar");
		AssertionResult result = invoke(newResponse(h1, h2));
		checkSuccess(result);
	}

	@Test
	public void it_should_fail_with_if_response_does_not_contain_header_with_expected_value() {
		final Header expectedHeader = getHeader();

		final String expectedName = expectedHeader.getName();
		final String actualValue = failValue();
		final Header header = header(expectedName, actualValue);

		AssertionResult result = invoke(newResponse(header));

		checkError(result,
				ShouldHaveHeader.class,
				"Expecting response to have header %s equal to %s but was %s",
				expectedHeader.getName(), expectedHeader.getValue(), header.getValue()
		);
	}

	@Test
	public void it_should_fail_with_if_response_does_not_contain_header() {
		AssertionResult result = invoke(newResponse(header("foo", "bar")));
		checkError(result, ShouldHaveHeader.class, "Expecting response to have header %s", getHeader().getName());
	}

	private HttpResponse newResponse(Header header, Header... other) {
		HttpResponseMockBuilder builder = new HttpResponseMockBuilder()
			.addHeader(header);

		for (Header o : other) {
			builder.addHeader(o.getName(), o.getValue());
		}

		return builder.build();
	}

	protected abstract Header getHeader();

	String failValue() {
		return getHeader().getValue() + "foo";
	}
}
