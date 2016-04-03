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

package com.github.mjeanroy.rest_assert.api.http.mime_type.google_http;

import com.github.mjeanroy.rest_assert.api.http.mime_type.AbstractMimeTypeInTest;
import com.github.mjeanroy.rest_assert.tests.mocks.google_http.GoogleHttpHeadersMockBuilder;
import com.github.mjeanroy.rest_assert.tests.mocks.google_http.GoogleHttpResponseMockBuilder;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import com.google.api.client.http.HttpResponse;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpResponse.class)
public abstract class AbstractGoogleMimeTypeInTest extends AbstractMimeTypeInTest<HttpResponse> {

	@Override
	protected HttpResponse newResponse(Header header) {
		return new GoogleHttpResponseMockBuilder()
			.setHeaders(new GoogleHttpHeadersMockBuilder()
				.addHeader(header.getName(), header.getValue())
				.build())
			.build();
	}
}
