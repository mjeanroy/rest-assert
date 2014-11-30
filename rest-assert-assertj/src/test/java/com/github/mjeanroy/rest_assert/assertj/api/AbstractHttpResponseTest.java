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
 * furnished to do so, subject to the following httpResponses:
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

package com.github.mjeanroy.rest_assert.assertj.api;

import com.github.mjeanroy.rest_assert.assertj.internal.HttpResponses;
import org.junit.Before;
import org.junit.Test;

import static org.apache.commons.lang3.reflect.FieldUtils.writeField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public abstract class AbstractHttpResponseTest {

	protected HttpResponses httpResponses;

	protected HttpResponseAssert assertions;

	@Before
	public void setUp() throws Exception {
		httpResponses = mock(HttpResponses.class);
		assertions = createAssertions();
		inject();
	}

	@Test
	public void it_should_invoke_internal_api() throws Exception {
		invoke();
		verifyApiCall();
		verifyNoMoreInteractions(httpResponses);
	}

	@Test
	public void it_should_return_instance() {
		HttpResponseAssert result = invoke();
		assertThat(result)
				.isNotNull()
				.isSameAs(assertions);
	}

	protected void inject() throws Exception {
		writeField(assertions, "assertions", httpResponses, true);
	}

	protected abstract HttpResponseAssert createAssertions();

	protected abstract HttpResponseAssert invoke();

	protected abstract void verifyApiCall();
}
