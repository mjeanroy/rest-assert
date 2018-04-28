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

package com.github.mjeanroy.restassert.unit.api.http.spring.headers.headerequalto;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.X_FRAME_OPTIONS;

import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.unit.api.http.SpringMockMvcHttpAssert;
import org.springframework.test.web.servlet.ResultActions;

public class AssertIsFrameOptionsEqualToStringTest extends AbstractSpringMockMvcHttpHeaderEqualToTest {

	private static final String VALUE = X_FRAME_OPTIONS.getValue();

	@Override
	protected Header getHeader() {
		return X_FRAME_OPTIONS;
	}

	@Override
	protected void invoke(ResultActions actual) {
		SpringMockMvcHttpAssert.assertIsFrameOptionsEqualTo(actual, VALUE);
	}

	@Override
	protected void invoke(String message, ResultActions actual) {
		SpringMockMvcHttpAssert.assertIsFrameOptionsEqualTo(message, actual, VALUE);
	}
}
