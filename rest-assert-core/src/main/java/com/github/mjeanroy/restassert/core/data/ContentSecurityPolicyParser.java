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

/**
 * Parser for {@link ContentSecurityPolicy} value.
 */
public class ContentSecurityPolicyParser extends AbstractHeaderParser<ContentSecurityPolicy> {

	/**
	 * Class logger.
	 */
	private static final Logger log = Loggers.getLogger(ContentSecurityPolicyParser.class);

	// Ensure non public instantiation.
	ContentSecurityPolicyParser() {
	}

	@Override
	protected ContentSecurityPolicy doParse(String value) {
		log.debug("Parsing Content-Security-Policy value: '{}'", value);

		String[] directives = value.split(";");
		ContentSecurityPolicyBuilder builder = new ContentSecurityPolicyBuilder();

		Set<ContentSecurityPolicy.SourceDirective> foundDirectives = new HashSet<>();

		for (String directive : directives) {
			StringBuilder nameBuilder = new StringBuilder();
			StringBuilder valueBuilder = new StringBuilder();

			StringBuilder current = nameBuilder;
			for (char c : directive.trim().toCharArray()) {
				if (Character.isWhitespace(c)) {
					current = valueBuilder;
				}

				current.append(c);
			}

			String currentName = nameBuilder.toString().trim();
			String currentValue = valueBuilder.toString().trim();

			log.debug("-> Found directive: '{} {}'", currentName, currentValue);

			// Check if directive has name.
			if (currentName.isEmpty()) {
				log.error("Directive name is empty, fail");
				throw new IllegalArgumentException(String.format("Header %s is not a valid Content-Security-Policy value", value));
			}

			ContentSecurityPolicy.SourceDirective dir = ContentSecurityPolicy.SourceDirective.byName(currentName);
			if (foundDirectives.contains(dir)) {
				log.warn("Directive {} has already been parsed, ignore duplicated");
				continue;
			}

			foundDirectives.add(dir);

			// Check if name is valid.
			if (dir == null) {
				log.error("Cannot find a matching for directive '{}', fail", currentName);
				throw new IllegalArgumentException(String.format("Cannot parse Content-Security-Policy value since directive %s seems not valid", currentName));
			}

			log.debug("  - Found directive: {}", dir);
			log.debug("  - Parse directive value: '{}'", currentValue);
			dir.parse(currentValue, builder);
		}

		return builder.build();
	}
}
