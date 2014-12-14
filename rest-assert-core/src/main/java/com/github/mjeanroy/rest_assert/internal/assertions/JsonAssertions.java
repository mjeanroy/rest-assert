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

package com.github.mjeanroy.rest_assert.internal.assertions;

import com.github.mjeanroy.rest_assert.error.RestAssertError;
import com.github.mjeanroy.rest_assert.internal.json.comparators.DefaultJsonComparator;
import com.github.mjeanroy.rest_assert.internal.json.comparators.JsonComparator;
import com.github.mjeanroy.rest_assert.internal.json.comparators.JsonComparatorOptions;
import com.github.mjeanroy.rest_assert.internal.json.parsers.JsonParser;
import com.github.mjeanroy.rest_assert.internal.json.parsers.JsonParserStrategy;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static com.github.mjeanroy.rest_assert.error.CompositeError.composeErrors;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.internal.json.comparators.JsonComparatorOptions.builder;
import static com.github.mjeanroy.rest_assert.utils.Utils.readFileToString;

/**
 * Set of reusable assertions on json
 * values.
 */
public final class JsonAssertions {

	/**
	 * Singleton object.
	 */
	private static JsonAssertions INSTANCE = new JsonAssertions();

	/**
	 * Get singleton object.
	 *
	 * @return Singleton object.
	 */
	public static JsonAssertions instance() {
		return INSTANCE;
	}

	/**
	 * Internal json parser.
	 */
	private final JsonParser parser;

	// Private constructor to ensure singleton
	private JsonAssertions() {
		this.parser = JsonParserStrategy.AUTO.build();
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param expected Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, String expected) {
		return doComparison(actual, expected, builder().build());
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param file Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, File file) {
		return isEqualTo(actual, readFileToString(file));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param path Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, Path path) {
		return isEqualTo(actual, readFileToString(path.toFile()));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param uri Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, URI uri) {
		return isEqualTo(actual, new File(uri));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param url Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, URL url) {
		try {
			return isEqualTo(actual, new File(url.toURI()));
		}
		catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param expected Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, String expected, Collection<String> entries) {
		JsonComparatorOptions options = builder()
				.ignoreKeys(entries)
				.build();

		return doComparison(actual, expected, options);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param file Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, File file, Collection<String> entries) {
		return isEqualToIgnoring(actual, readFileToString(file), entries);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param path Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, Path path, Collection<String> entries) {
		return isEqualToIgnoring(actual, readFileToString(path.toFile()), entries);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param uri Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, URI uri, Collection<String> entries) {
		return isEqualToIgnoring(actual, new File(uri), entries);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param url Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, URL url, Collection<String> entries) {
		try {
			return isEqualToIgnoring(actual, new File(url.toURI()), entries);
		}
		catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}

	private AssertionResult doComparison(String actual, String expected, JsonComparatorOptions options) {
		JsonComparator comparator = new DefaultJsonComparator(parser, options);
		List<RestAssertError> errors = comparator.compare(actual, expected);
		return errors.isEmpty() ?
				success() :
				failure(composeErrors(errors));
	}
}
