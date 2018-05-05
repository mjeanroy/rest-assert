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

import com.github.mjeanroy.restassert.core.internal.data.AbstractHeaderParser;
import com.github.mjeanroy.restassert.core.internal.loggers.Logger;
import com.github.mjeanroy.restassert.core.internal.loggers.Loggers;

import java.util.HashSet;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.data.StrictTransportSecurity.Directive;

/**
 * Parser for {@link StrictTransportSecurity} value.
 */
public class StrictTransportSecurityParser extends AbstractHeaderParser<StrictTransportSecurity> {

	/**
	 * Class Logger.
	 */
	private static final Logger log = Loggers.getLogger(StrictTransportSecurityParser.class);

	// Ensure non public instantiation.
	StrictTransportSecurityParser() {
	}

	@Override
	protected StrictTransportSecurity doParse(String value) {
		log.debug("Parsing value: '{}'", value);
		String[] parts = value.split(";");

		StrictTransportSecurityBuilder builder = new StrictTransportSecurityBuilder();
		Set<Directive> foundDirectives = new HashSet<>();

		for (String part : parts) {
			String[] directiveNameValue = part.split("=");
			String directiveName = directiveNameValue[0].trim();

			log.debug("-> Parsing part: '{}'", part);
			log.debug("  --> Name: '{}'", directiveName);

			Directive directive = Directive.byName(directiveName);
			if (directive == null) {
				log.error("Directive name '{}' should not appear in Strict-Transport-Security value", directiveName);
				throw new IllegalArgumentException(String.format("Cannot parse Strict-Transport-Security, directive %s is unknown", directiveName));
			}

			if (foundDirectives.contains(directive)) {
				log.warn("  --> Directive '{}' has already been parsed, ignore it", directiveName);
				continue;
			}

			// Mark directive.
			foundDirectives.add(directive);

			String directiveValue = directiveNameValue.length == 2 ? directiveNameValue[1].trim() : null;

			log.debug("  --> Parsing value: '{}'", directiveValue);
			directive.parse(directiveValue, builder);
		}

		return builder.build();
	}
}
