/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.restassert.internal.assertions.http.mimetype;

import com.github.mjeanroy.restassert.error.http.ShouldHaveHeader;
import com.github.mjeanroy.restassert.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.mocks.HttpResponseMockBuilderImpl;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;

public class HasMimeTypeInTest extends AbstractMimeTypeInTest {

	@Override
	protected List<String> getMimeTypes() {
		return asList("text/css", "text/html");
	}

	@Override
	protected AssertionResult invoke(HttpResponse response) {
		return assertions.hasMimeTypeIn(response, getMimeTypes());
	}

	@Test
	public void it_should_fail_if_response_does_not_have_content_type() {
		HttpResponse rsp = new HttpResponseMockBuilderImpl().build();
		AssertionResult result = assertions.hasMimeTypeIn(rsp, getMimeTypes());
		checkError(result, ShouldHaveHeader.class, "Expecting response to have header %s", "Content-Type");
	}
}
