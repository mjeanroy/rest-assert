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

package com.github.mjeanroy.restassert.core.internal.loggers;

import com.github.mjeanroy.restassert.core.internal.common.ClassUtils;

/**
 * Static Logger Factory.
 */
public final class Loggers {

	// Ensure non instantiation.
	private Loggers() {
	}

	/**
	 * Create logger, returns a no-op logger if slf4j is not available in the
	 * classpath, or a logger delegating to slf4j otherwise.
	 *
	 * @param klass Logger name.
	 * @return The logger.
	 */
	public static Logger getLogger(Class<?> klass) {
		if (ClassUtils.isPresent("org.slf4j.Logger")) {
			return new Slf4jLogger(klass);
		}

		// Fallback.
		return new NoOpLogger();
	}
}
