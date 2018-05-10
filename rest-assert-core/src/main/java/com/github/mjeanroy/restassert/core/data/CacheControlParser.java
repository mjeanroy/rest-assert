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

package com.github.mjeanroy.restassert.core.data;

import com.github.mjeanroy.restassert.core.data.CacheControl.Directive;
import com.github.mjeanroy.restassert.core.internal.data.AbstractHeaderParser;
import com.github.mjeanroy.restassert.core.internal.loggers.Logger;
import com.github.mjeanroy.restassert.core.internal.loggers.Loggers;

/**
 * Parser for {@link CacheControl} value.
 */
public class CacheControlParser extends AbstractHeaderParser<CacheControl> {

	/**
	 * Class Logger.
	 */
	private static final Logger log = Loggers.getLogger(StrictTransportSecurityParser.class);

	// Ensure non public instantiation.
	CacheControlParser() {
	}

	@Override
	protected CacheControl doParse(String value) {
		log.debug("Parsing Cache-Control value: '{}'", value);

		String[] parts = value.split(",");
		CacheControlBuilder builder = new CacheControlBuilder();
		for (String part : parts) {
			String partValue = part.trim();
			for (Directive directive : Directive.values()) {
				if (directive.match(partValue)) {
					log.debug("-> Found directive: '{}'", directive);
					log.debug("-> Parsing value: '{}'", partValue);
					directive.setValue(partValue, builder);
					break;
				}
			}
		}

		return builder.build();
	}
}
