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

package com.github.mjeanroy.rest_assert.assertj.internal.cookie;

import static com.github.mjeanroy.rest_assert.assertj.tests.AssertJUtils.someInfo;
import static com.github.mjeanroy.rest_assert.tests.AssertionUtils.failBecauseExpectedAssertionErrorWasNotThrown;
import static com.github.mjeanroy.rest_assert.tests.TestData.newCookie;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.AssertionInfo;
import org.junit.Test;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;

public class Cookies_assertHasName_Test extends AbstractCookiesTest {

	@Test
	public void should_pass_if_status_code_is_ok() {
		Cookie cookie = cookie(expectedName());
		invoke(someInfo(), cookie);
	}

	@Test
	public void should_fail_if_name_is_not_expected() {
		final AssertionInfo info = someInfo();
		final String expectedName = expectedName();
		final String actualName = expectedName + "foo";
		final Cookie cookie = cookie(actualName);

		try {
			invoke(info, cookie);
			failBecauseExpectedAssertionErrorWasNotThrown();
		}
		catch (AssertionError e) {
			assertThat(e.getMessage())
					.isNotNull()
					.isNotEmpty()
					.isEqualTo(format("Expecting cookie to have name \"%s\" but was \"%s\"", expectedName, actualName));
		}
	}

	@Override
	protected void invoke(AssertionInfo info, Cookie cookie) {
		cookies.assertHasName(info, cookie, expectedName());
	}

	protected Cookie cookie(String name) {
		return newCookie(name);
	}

	protected String expectedName() {
		return "foo";
	}
}
