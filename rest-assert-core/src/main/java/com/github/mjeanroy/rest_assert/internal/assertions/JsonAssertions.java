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
import com.github.mjeanroy.rest_assert.internal.data.JsonEntry;
import com.github.mjeanroy.rest_assert.internal.data.defaults.DefaultJsonEntry;
import com.github.mjeanroy.rest_assert.internal.json.comparators.DefaultJsonComparator;
import com.github.mjeanroy.rest_assert.internal.json.comparators.JsonComparator;
import com.github.mjeanroy.rest_assert.internal.json.comparators.JsonComparatorOptions;
import com.github.mjeanroy.rest_assert.internal.json.parsers.JsonParser;
import com.github.mjeanroy.rest_assert.internal.json.parsers.JsonParserStrategy;
import com.github.mjeanroy.rest_assert.reflect.Param;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.github.mjeanroy.rest_assert.error.CompositeError.composeErrors;
import static com.github.mjeanroy.rest_assert.error.json.ShouldHaveEntry.shouldHaveEntry;
import static com.github.mjeanroy.rest_assert.error.json.ShouldHaveEntryEqualTo.shouldHaveEntryEqualTo;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.rest_assert.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.rest_assert.internal.json.comparators.JsonComparatorOptions.builder;
import static com.github.mjeanroy.rest_assert.utils.Utils.readFileToString;
import static java.util.Collections.addAll;

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
	 * Create JSON entry object.
	 *
	 * @param key Entry key.
	 * @param value Entry value.
	 * @param <T> Type of value.
	 * @return The JSON entry.
	 */
	public static <T>JsonEntry<T> jsonEntry(String key, T value) {
		return new DefaultJsonEntry<>(key, value);
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
	 * Check that given json contains given entries.
	 *
	 * @param actual JSON object.
	 * @param key Entry to check.
	 * @param other Other entries to check.
	 * @return Assertion result.
	 */
	public AssertionResult contains(String actual, @Param("key") String key, @Param("other") String... other) {
		Set<String> entries = new LinkedHashSet<>();
		entries.add(key);
		addAll(entries, other);
		return contains(actual, entries);
	}

	/**
	 * Check that given json contains given entries.
	 *
	 * @param actual JSON object.
	 * @param keys Entries to check.
	 * @return Assertion result.
	 */
	public AssertionResult contains(String actual, @Param("keys") Iterable<String> keys) {
		Set<RestAssertError> errors = new LinkedHashSet<>();

		for (String e : keys) {
			if (!hasEntry(actual, e)) {
				errors.add(shouldHaveEntry(e));
			}
		}

		return errors.isEmpty() ?
			success() :
			failure(composeErrors(errors));
	}

	/**
	 * Check that given json contains given entries.
	 *
	 * @param actual JSON object.
	 * @param entry Entry to check.
	 * @param other Other entries to check.
	 * @return Assertion result.
	 */
	public AssertionResult containsEntries(String actual, @Param("entry") JsonEntry<?> entry, @Param("other") JsonEntry<?>... other) {
		Set<JsonEntry<?>> entries = new LinkedHashSet<>();
		entries.add(entry);
		addAll(entries, other);
		return containsEntries(actual, entries);
	}

	private AssertionResult containsEntries(String actual, @Param("entry") Iterable<JsonEntry<?>> entries) {
		Set<RestAssertError> errors = new LinkedHashSet<>();

		for (JsonEntry e : entries) {
			String key = e.getKey();
			if (!hasEntry(actual, key)) {
				errors.add(shouldHaveEntry(key));
			} else {
				Object expectedValue = e.getValue();
				Object actualValue = getEntry(actual, key);
				if (!expectedValue.equals(actualValue)) {
					errors.add(shouldHaveEntryEqualTo(key, actualValue, expectedValue));
				}
			}
		}

		return errors.isEmpty() ?
			success() :
			failure(composeErrors(errors));
	}

	private boolean hasEntry(String actual, String entry) {
		try {
			getEntry(actual, entry);
			return true;
		} catch (PathNotFoundException ex) {
			return false;
		}
	}

	private <T> T getEntry(String actual, String entry) {
		String path = toJsonPath(entry);
		return JsonPath.read(actual, path);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual   Actual representation.
	 * @param expected Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, @Param("expected") String expected) {
		return doComparison(actual, expected, builder().build());
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param file   Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, @Param("file") File file) {
		return isEqualTo(actual, readFileToString(file.toPath()));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param path   Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, @Param("path") Path path) {
		return isEqualTo(actual, readFileToString(path));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param uri    Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, @Param("uri") URI uri) {
		return isEqualTo(actual, new File(uri));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param url    Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, @Param("url") URL url) {
		try {
			return isEqualTo(actual, new File(url.toURI()));
		} catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual   Actual representation.
	 * @param expected Expected representation.
	 * @param entries  Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, @Param("expected") String expected, @Param("entries") Iterable<String> entries) {
		JsonComparatorOptions options = builder()
				.ignoreKeys(entries)
				.build();

		return doComparison(actual, expected, options);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual  Actual representation.
	 * @param file    Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, @Param("file") File file, @Param("entries") Iterable<String> entries) {
		return isEqualToIgnoring(actual, readFileToString(file.toPath()), entries);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual  Actual representation.
	 * @param path    Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, @Param("path") Path path, @Param("entries") Iterable<String> entries) {
		return isEqualToIgnoring(actual, readFileToString(path), entries);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual  Actual representation.
	 * @param uri     Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, @Param("uri") URI uri, @Param("entries") Iterable<String> entries) {
		return isEqualToIgnoring(actual, new File(uri), entries);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual  Actual representation.
	 * @param url     Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, @Param("url") URL url, @Param("entries") Iterable<String> entries) {
		try {
			return isEqualToIgnoring(actual, new File(url.toURI()), entries);
		} catch (URISyntaxException ex) {
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

	private static String toJsonPath(String path) {
		if (!path.startsWith("$.")) {
			return "$." + path;
		}

		return path;
	}
}
