/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.hamcrest.api.http.junitservers.headers.headerequalto;

import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.junit.servers.client.HttpResponse;
import org.hamcrest.MatcherAssert;

import static com.github.mjeanroy.restassert.hamcrest.api.http.JunitServersHttpResponseMatchers.isPragmaEqualTo;
import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.PRAGMA;

public class IsPragmaEqualToMatcherTest extends AbstractJunitServersHttpResponseHeaderEqualToMatcherTest {

	private static final String VALUE = PRAGMA.getValue();

	@Override
	protected Header getHeader() {
		return PRAGMA;
	}

	@Override
	protected void run(HttpResponse actual) {
		MatcherAssert.assertThat(actual, isPragmaEqualTo(VALUE));
	}
}
