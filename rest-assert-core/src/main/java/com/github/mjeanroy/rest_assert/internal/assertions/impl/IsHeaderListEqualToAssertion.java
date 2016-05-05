/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.assertions.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult;
import com.github.mjeanroy.rest_assert.utils.Predicate;

import static com.github.mjeanroy.rest_assert.error.http.ShouldHaveHeader.shouldHaveHeaderWithValue;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.utils.Utils.join;
import static com.github.mjeanroy.rest_assert.utils.Utils.notEmpty;
import static com.github.mjeanroy.rest_assert.utils.Utils.some;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

/**
 * Check that http response has at least one header with
 * a list of expected values (separated by ',').
 */
public class IsHeaderListEqualToAssertion extends AbstractHeaderEqualToAssertion implements HttpResponseAssertion {

	/**
	 * Expected header values.
	 */
	private final Set<String> values;

	/**
	 * Create assertion.
	 *
	 * @param name Header name.
	 */
	public IsHeaderListEqualToAssertion(String name, Iterable<String> values) {
		super(name);
		this.values = unmodifiableSet(toSet(notEmpty(values, "Values must not be empty")));
	}

	@Override
	AssertionResult doAssertion(List<String> values) {
		boolean found = some(values, new SetPredicate(this.values));
		return found ? success() : failure(shouldHaveHeaderWithValue(name, join(this.values, ", "), values));
	}

	private static Set<String> toSet(Iterable<String> values) {
		Set<String> set = new LinkedHashSet<>();
		for (String value : values) {
			String trimmedValue = value.trim();
			if (!trimmedValue.isEmpty()) {
				set.add(trimmedValue);
			}
		}

		return set;
	}

	private static class SetPredicate implements Predicate<String> {
		private final Set<String> expected;

		private SetPredicate(Set<String> expected) {
			this.expected = expected;
		}

		@Override
		public boolean apply(String input) {
			Set<String> actualValues = toSet(asList(input.split(",")));
			return actualValues.equals(expected);
		}
	}
}
