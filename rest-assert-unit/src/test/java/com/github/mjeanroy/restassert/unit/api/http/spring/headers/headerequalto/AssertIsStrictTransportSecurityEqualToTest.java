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

import com.github.mjeanroy.restassert.core.data.StrictTransportSecurity;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.unit.api.http.SpringMockMvcHttpAssert;
import org.springframework.test.web.servlet.ResultActions;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.STRICT_TRANSPORT_SECURITY;

public class AssertIsStrictTransportSecurityEqualToTest extends AbstractSpringMockMvcHttpHeaderEqualToTest {

	private static final StrictTransportSecurity VALUE = StrictTransportSecurity.builder(31536000)
		.includeSubDomains()
		.build();

	private static final String FAILED_VALUE = StrictTransportSecurity.builder(31536000)
		.preload()
		.build()
		.serializeValue();

	@Override
	protected Header getHeader() {
		return STRICT_TRANSPORT_SECURITY;
	}

	@Override
	protected void invoke(ResultActions actual) {
		SpringMockMvcHttpAssert.assertIsStrictTransportSecurityEqualTo(actual, VALUE);
	}

	@Override
	protected void invoke(String message, ResultActions actual) {
		SpringMockMvcHttpAssert.assertIsStrictTransportSecurityEqualTo(message, actual, VALUE);
	}

	@Override
	protected String failValue() {
		return FAILED_VALUE;
	}
}
