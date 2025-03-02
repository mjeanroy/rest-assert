/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.assertions;

import com.github.mjeanroy.restassert.core.data.JsonEntry;
import com.github.mjeanroy.restassert.core.internal.common.Ios;
import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import com.github.mjeanroy.restassert.core.internal.json.JsonType;
import com.github.mjeanroy.restassert.core.internal.json.comparators.DefaultJsonComparator;
import com.github.mjeanroy.restassert.core.internal.json.comparators.JsonComparator;
import com.github.mjeanroy.restassert.core.internal.json.parsers.JsonParser;
import com.github.mjeanroy.restassert.core.internal.json.parsers.JsonParsers;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.failure;
import static com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult.success;
import static com.github.mjeanroy.restassert.core.internal.common.Files.readFileToString;
import static com.github.mjeanroy.restassert.core.internal.common.Ios.readUrl;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.isEmpty;
import static com.github.mjeanroy.restassert.core.internal.common.Strings.trimToNull;
import static com.github.mjeanroy.restassert.core.internal.error.CompositeError.composeErrors;
import static com.github.mjeanroy.restassert.core.internal.error.common.ShouldNotBeNull.shouldNotBeNull;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldBeTypeOf.shouldBeTypeOf;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntry.shouldHaveEntry;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntryEqualTo.shouldHaveEntryEqualTo;
import static java.util.Collections.addAll;

/**
 * Set of reusable assertions on json
 * values.
 */
public final class JsonAssertions {

	/**
	 * Singleton object.
	 */
	private static final JsonAssertions INSTANCE = new JsonAssertions();

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
	 * @return The JSON entry.
	 * @throws NullPointerException If {@code key} is {code null}.
	 */
	public static JsonEntry jsonEntry(String key, Object value) {
		return JsonEntry.of(key, value);
	}

	/**
	 * Internal json parser.
	 */
	private final JsonParser parser;

	// Private constructor to ensure singleton
	private JsonAssertions() {
		this.parser = JsonParsers.getParser();
	}

	/**
	 * Check that given json is not null.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isNotNull(String actual) {
		return actual == null ? failure(shouldNotBeNull("json")) : success();
	}

	/**
	 * Check that given json is a JSON string type.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isString(String actual) {
		return isType(actual, JsonType.STRING);
	}

	/**
	 * Check that given json is a JSON string type.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isStringEntry(String actual, String path) {
		return isEntryWithType(actual, path, JsonType.STRING);
	}

	/**
	 * Check that given json is a JSON number type.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isNumberEntry(String actual, String path) {
		return isEntryWithType(actual, path, JsonType.NUMBER);
	}

	/**
	 * Check that given json is a JSON boolean type.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isBooleanEntry(String actual, String path) {
		return isEntryWithType(actual, path, JsonType.BOOLEAN);
	}

	/**
	 * Check that given json is a JSON array type.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isArrayEntry(String actual, String path) {
		return isEntryWithType(actual, path, JsonType.ARRAY);
	}

	/**
	 * Check that given json is a JSON object type.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isObjectEntry(String actual, String path) {
		return isEntryWithType(actual, path, JsonType.OBJECT);
	}

	private AssertionResult isEntryWithType(String actual, String path, JsonType expectedType) {
		AssertionResult result = contains(actual, path);
		if (result.isFailure()) {
			return result;
		}

		Object parsedValue = getEntry(actual, path);
		return isType(actual, path, parsedValue, expectedType);
	}

	/**
	 * Check that given json is a JSON number.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isNumber(String actual) {
		return isType(actual, JsonType.NUMBER);
	}

	/**
	 * Check that given json is a JSON boolean.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isBoolean(String actual) {
		return isType(actual, JsonType.BOOLEAN);
	}

	/**
	 * Check that given json is a JSON array.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isArray(String actual) {
		return isType(actual, JsonType.ARRAY);
	}

	/**
	 * Check that given json is a JSON object.
	 *
	 * @param actual JSON.
	 * @return Assertion result.
	 */
	public AssertionResult isObject(String actual) {
		return isType(actual, JsonType.OBJECT);
	}

	private AssertionResult isType(String actual, JsonType expectedType) {
		String trimmedActual = trimToNull(actual.trim());

		if (trimmedActual == null) {
			return failure(shouldNotBeNull("json"));
		}

		Object parsedValue = parser.parse(trimmedActual);
		JsonType actualType = JsonType.getType(parsedValue);
		if (actualType == expectedType) {
			return success();
		}

		return failure(
			shouldBeTypeOf(actual, expectedType, actualType)
		);
	}

	private AssertionResult isType(String actual, String path, Object parsedValue, JsonType expectedType) {
		JsonType actualType = JsonType.getType(parsedValue);
		if (actualType == expectedType) {
			return success();
		}

		return failure(
			shouldBeTypeOf(
				actual,
				path,
				expectedType,
				actualType
			)
		);
	}

	/**
	 * Check that given json contains given entries.
	 *
	 * @param actual JSON object.
	 * @param key Entry to check.
	 * @param other Other entries to check.
	 * @return Assertion result.
	 */
	public AssertionResult contains(String actual, String key, String... other) {
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
	public AssertionResult contains(String actual, Iterable<String> keys) {
		Set<RestAssertError> errors = new LinkedHashSet<>();

		for (String e : keys) {
			if (isEmpty(actual) || doesNotHaveEntry(actual, e)) {
				errors.add(
					shouldHaveEntry(actual, e)
				);
			}
		}

		return errors.isEmpty() ? success() : failure(composeErrors(errors));
	}

	/**
	 * Check that given json contains given entries.
	 *
	 * @param actual JSON object.
	 * @param entry Entry to check.
	 * @param other Other entries to check.
	 * @return Assertion result.
	 */
	public AssertionResult containsEntries(String actual, JsonEntry entry, JsonEntry... other) {
		Set<JsonEntry> entries = new LinkedHashSet<>();
		entries.add(entry);
		addAll(entries, other);
		return containsEntries(actual, entries);
	}

	/**
	 * Check that given json contains given entries.
	 *
	 * @param actual JSON object.
	 * @param entries Entries to check.
	 * @return Assertion result.
	 */
	public AssertionResult containsEntries(String actual, Iterable<JsonEntry> entries) {
		Set<RestAssertError> errors = new LinkedHashSet<>();

		// Collect errors
		for (JsonEntry e : entries) {
			String key = e.getKey();
			if (isEmpty(actual) || doesNotHaveEntry(actual, key)) {
				errors.add(shouldHaveEntry(actual, key));
				continue;
			}

			Object expectedValue = e.getValue();
			Object actualValue = getEntry(actual, key);
			if (!expectedValue.equals(actualValue)) {
				errors.add(
					shouldHaveEntryEqualTo(actual, key, actualValue, expectedValue)
				);
			}
		}

		return errors.isEmpty() ? success() : failure(composeErrors(errors));
	}

	private static boolean doesNotHaveEntry(String actual, String entry) {
		try {
			getEntry(actual, entry);
			return false;
		}
		catch (PathNotFoundException ex) {
			return true;
		}
	}

	private static <T> T getEntry(String actual, String entry) {
		String path = toJsonPath(entry);
		return JsonPath.read(actual, path);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param expected Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, String expected) {
		return doComparison(actual, expected);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param file Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, File file) {
		return isEqualTo(actual, readFileToString(file.toPath()));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param path Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, Path path) {
		return isEqualTo(actual, readFileToString(path));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param uri Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, URI uri) {
		if (uri == null) {
			throw new AssertionError("Cannot extract expected JSON from <null> URI");
		}

		return isEqualTo(actual, Paths.get(uri));
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param url Expected representation.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualTo(String actual, URL url) {
		if (url == null) {
			throw new AssertionError("Cannot extract expected JSON from <null> URL");
		}

		try {
			String expected = readUrl(url);
			return isEqualTo(actual, expected);
		}
		catch (Ios.UrlException ex) {
			throw new AssertionError(ex);
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
	public AssertionResult isEqualToIgnoring(String actual, String expected, Iterable<String> entries) {
		final String actualJson;
		final String expectedJson;

		// Check if some keys needs to be ignored
		if (entries.iterator().hasNext()) {
			DocumentContext actualCtx = JsonPath.parse(actual);
			DocumentContext expectedCtx = JsonPath.parse(expected);

			// Remove keys to ignore
			for (String entry : entries) {
				String path = toJsonPath(entry);
				actualCtx.delete(path);
				expectedCtx.delete(path);
			}

			actualJson = actualCtx.jsonString();
			expectedJson = expectedCtx.jsonString();
		}
		else {
			actualJson = actual;
			expectedJson = expected;
		}

		return doComparison(actualJson, expectedJson);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param file Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, File file, Iterable<String> entries) {
		return isEqualToIgnoring(actual, readFileToString(file.toPath()), entries);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param path Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, Path path, Iterable<String> entries) {
		return isEqualToIgnoring(actual, readFileToString(path), entries);
	}

	/**
	 * Check that two json representation are equals.
	 *
	 * @param actual Actual representation.
	 * @param uri Expected representation.
	 * @param entries Name of entries to ignore.
	 * @return Assertion result.
	 */
	public AssertionResult isEqualToIgnoring(String actual, URI uri, Iterable<String> entries) {
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
	public AssertionResult isEqualToIgnoring(String actual, URL url, Iterable<String> entries) {
		try {
			return isEqualToIgnoring(actual, new File(url.toURI()), entries);
		}
		catch (URISyntaxException ex) {
			throw new AssertionError(ex);
		}
	}

	private AssertionResult doComparison(String actual, String expected) {
		if (actual == null) {
			return failure(shouldNotBeNull("json"));
		}

		JsonComparator comparator = new DefaultJsonComparator(parser);
		List<RestAssertError> errors = comparator.compare(actual, expected);
		return errors.isEmpty() ? success() : failure(composeErrors(errors));
	}

	private static String toJsonPath(String path) {
		return path.startsWith("$.") ? path : ("$." + path);
	}
}
