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

package com.github.mjeanroy.restassert.tests.junit;

import org.junit.rules.ExternalResource;

import java.util.TimeZone;

/**
 * A JUnit {@link org.junit.Rule} that can be used to run unit test
 * on a specific TimeZone.
 */
public class TimeZoneRule extends ExternalResource {

	/**
	 * The TimeZone to use.
	 */
	private final TimeZone timeZone;

	/**
	 * The default TimeZone that was defined before updating before test.
	 */
	private final TimeZone defaultTimeZone;

	/**
	 * Create the JUnit Rule.
	 *
	 * @param tzId The TimeZone identifier.
	 */
	public TimeZoneRule(String tzId) {
		this.timeZone = TimeZone.getTimeZone(tzId);
		this.defaultTimeZone = TimeZone.getDefault();
	}

	@Override
	protected void before() throws Throwable {
		TimeZone.setDefault(timeZone);
	}

	@Override
	protected void after() {
		TimeZone.setDefault(defaultTimeZone);
	}
}
