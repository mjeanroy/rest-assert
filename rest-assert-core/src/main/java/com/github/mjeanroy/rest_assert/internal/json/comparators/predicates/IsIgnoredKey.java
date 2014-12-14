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

package com.github.mjeanroy.rest_assert.internal.json.comparators.predicates;

import com.github.mjeanroy.rest_assert.error.RestAssertJsonError;
import com.github.mjeanroy.rest_assert.utils.Predicate;

import java.util.Set;

/**
 * Predicate that check if error entry name is in a set
 * of ignoring keys.
 */
public class IsIgnoredKey implements Predicate<RestAssertJsonError> {

	/**
	 * Create new predicate.
	 *
	 * @param ignoringKeys Keys to check.
	 * @return Predicate.
	 */
	public static IsIgnoredKey isIgnored(Set<String> ignoringKeys) {
		return new IsIgnoredKey(ignoringKeys);
	}

	/**
	 * Keys to check.
	 */
	private final Set<String> ignoringKeys;

	// Use static factory
	private IsIgnoredKey(Set<String> ignoringKeys) {
		this.ignoringKeys = ignoringKeys;
	}

	@Override
	public boolean apply(RestAssertJsonError input) {
		return !ignoringKeys.contains(input.entryName());
	}
}
