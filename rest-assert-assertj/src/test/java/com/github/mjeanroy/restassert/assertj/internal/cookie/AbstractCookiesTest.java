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

package com.github.mjeanroy.restassert.assertj.internal.cookie;

import com.github.mjeanroy.restassert.assertj.internal.Cookies;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import org.assertj.core.api.AssertionInfo;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.assertj.tests.AssertJUtils.someInfo;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractCookiesTest {

	final Cookies cookies = Cookies.instance();

	@Test
	void should_pass() {
		Cookie cookie = success();
		run(someInfo(), cookie);
	}

	@Test
	void should_fail() {
		AssertionInfo info = someInfo();
		Cookie cookie = failure();

		try {
			run(info, cookie);
			failBecauseExpectedAssertionErrorWasNotThrown();
		}
		catch (AssertionError e) {
			assertThat(e.getMessage()).isEqualTo(message());
		}
	}

	protected abstract void run(AssertionInfo info, Cookie cookie);

	protected abstract Cookie success();

	protected abstract Cookie failure();

	protected abstract String message();
}
