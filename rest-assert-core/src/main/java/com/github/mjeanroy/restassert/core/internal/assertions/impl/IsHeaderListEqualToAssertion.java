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

package com.github.mjeanroy.restassert.core.internal.assertions.impl;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.common.Collections.Predicate;
import com.github.mjeanroy.restassert.core.internal.loggers.Logger;
import com.github.mjeanroy.restassert.core.internal.loggers.Loggers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.Collections.some;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.join;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notEmpty;

/**
 * Check that http response has at least one header with
 * a list of expected values (separated by ',').
 */
public class IsHeaderListEqualToAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Class logger.
	 */
	private static final Logger log = Loggers.getLogger(IsHeaderListEqualToAssertion.class);

	/**
	 * Expected header values.
	 */
	private final Set<String> values;

	/**
	 * Expected header values in lowercase (used for case insensitive comparison).
	 */
	private final Set<String> lowercaseValues;

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 */
	public IsHeaderListEqualToAssertion(String name, Iterable<String> values) {
		super(name);

		notEmpty(values, "Values must not be empty");
		this.values = new LinkedHashSet<>();
		this.lowercaseValues = new LinkedHashSet<>();
		initSet(values);
	}

	private void initSet(Iterable<String> values) {
		log.debug("Extract set of values from: {}", values);

		for (String value : values) {
			String trimmedValue = value.trim();
			if (!trimmedValue.isEmpty()) {
				log.debug("-> Adding: '{}'", trimmedValue);
				this.values.add(trimmedValue);
				this.lowercaseValues.add(trimmedValue.toLowerCase());
			} else {
				log.warn("-> Found an empty string, ignore it");
			}
		}
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		boolean found = some(values, new SetPredicate(this.lowercaseValues));
		return found ? success() : failure(shouldHaveHeaderWithValue(name, join(this.values, ", "), values));
	}

	private static class SetPredicate implements Predicate<String> {
		private final Set<String> expected;

		private SetPredicate(Set<String> expected) {
			this.expected = expected;
		}

		@Override
		public boolean apply(String input) {
			log.debug("Extracting list of values from '{}'", input);

			String[] inputs = input.split(",");
			Set<String> actualValues = new LinkedHashSet<>();
			for (String value : inputs) {
				String trimmedValue = value.trim();
				if (!trimmedValue.isEmpty()) {
					log.debug("-> Found: '{}'", trimmedValue);
					actualValues.add(trimmedValue.toLowerCase());
				} else {
					log.warn("-> Found empty value during parsing of header: '{}', ignore it", input);
				}
			}

			return actualValues.equals(expected);
		}
	}
}
