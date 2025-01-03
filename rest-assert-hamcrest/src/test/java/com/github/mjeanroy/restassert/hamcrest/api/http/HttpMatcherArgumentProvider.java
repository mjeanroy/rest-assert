/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.hamcrest.api.http;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public final class HttpMatcherArgumentProvider implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
		return Stream.of(
			Arguments.of(HttpMatchers.okHttpMatcher()),
			Arguments.of(HttpMatchers.apacheHttpMatcher()),
			Arguments.of(HttpMatchers.asyncHttpMatcher()),
			Arguments.of(HttpMatchers.ningHttpMatcher()),
			Arguments.of(HttpMatchers.googleHttpMatcher()),
			Arguments.of(HttpMatchers.junitServersHttpMatcher()),
			Arguments.of(HttpMatchers.coreHttpMatcher()),
			Arguments.of(HttpMatchers.springMockMvcHttpMatcher())
		);
	}
}
