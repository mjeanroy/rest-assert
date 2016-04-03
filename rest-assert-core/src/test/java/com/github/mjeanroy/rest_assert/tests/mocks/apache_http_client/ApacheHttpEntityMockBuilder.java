/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.tests.mocks.apache_http_client;

import org.apache.http.HttpEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder to create mock instances of {@link HttpEntity} class.
 */
public class ApacheHttpEntityMockBuilder {

	/**
	 * Body content.
	 */
	private InputStream content;

	/**
	 * Set {@link #content}.
	 *
	 * @param content New {@link #content}.
	 * @return Current builder.
	 */
	public ApacheHttpEntityMockBuilder setContent(String content) {
		this.content = new ByteArrayInputStream(content.getBytes());
		return this;
	}

	/**
	 * Create mock instance of {@link HttpEntity} class.
	 *
	 * @return Mock instance.
	 */
	public HttpEntity build() {
		HttpEntity entity = mock(HttpEntity.class);

		try {
			when(entity.getContent()).thenReturn(content);
		} catch (IOException ex) {
			throw new AssertionError(ex);
		}

		return entity;
	}
}
