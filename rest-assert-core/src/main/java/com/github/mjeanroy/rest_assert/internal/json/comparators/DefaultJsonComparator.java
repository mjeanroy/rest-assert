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

package com.github.mjeanroy.rest_assert.internal.json.comparators;

import com.github.mjeanroy.rest_assert.error.RestAssertError;
import com.github.mjeanroy.rest_assert.internal.json.parsers.JsonParser;
import com.github.mjeanroy.rest_assert.internal.json.JsonType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.mjeanroy.rest_assert.error.json.ShouldBeAnArray.shouldBeAnArray;
import static com.github.mjeanroy.rest_assert.error.json.ShouldBeAnObject.shouldBeAnObject;
import static com.github.mjeanroy.rest_assert.error.json.ShouldBeEntryOf.shouldBeEntryOf;
import static com.github.mjeanroy.rest_assert.error.json.ShouldBeEqualTo.shouldBeEqualTo;
import static com.github.mjeanroy.rest_assert.error.json.ShouldHaveEntry.shouldHaveEntry;
import static com.github.mjeanroy.rest_assert.error.json.ShouldHaveSize.shouldHaveSize;
import static com.github.mjeanroy.rest_assert.error.json.ShouldNotHaveEntry.shouldNotHaveEntry;
import static com.github.mjeanroy.rest_assert.internal.json.JsonType.parseType;
import static com.github.mjeanroy.rest_assert.internal.json.comparators.JsonContext.rootContext;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;

/**
 * Default implementation for {@link JsonComparator}
 * interface.
 */
public class DefaultJsonComparator implements JsonComparator {

	private final JsonParser parser;

	// Use thread local to remains thread safe
	private final ThreadLocal<JsonContext> contexts = new ThreadLocal<>();

	public DefaultJsonComparator(JsonParser parser) {
		this.parser = parser;
	}

	public List<RestAssertError> compare(String actual, String expected) {
		contexts.set(rootContext());
		List<RestAssertError> errors = doCompare(actual.trim(), expected.trim());
		contexts.remove();
		return errors;
	}

	private List<RestAssertError> doCompare(String actual, String expected) {
		RestAssertError error = checkType(actual, expected);
		if (error != null) {
			return singletonList(error);
		}

		// Same types
		boolean isObject = isObject(actual);
		if (isObject) {
			Map<String, Object> actualMap = parser.parseObject(actual);
			Map<String, Object> expectedMap = parser.parseObject(expected);
			return compareObjects(actualMap, expectedMap);
		}
		else {
			List<Object> actualArray = parser.parseArray(actual);
			List<Object> expectedArray = parser.parseArray(expected);
			return compareArrays(actualArray, expectedArray);
		}
	}

	private RestAssertError checkType(String actual, String expected) {
		if (isObject(actual) && isArray(expected)) {
			return shouldBeAnArray();
		}
		else if (isArray(actual) && isObject(expected)) {
			// It should be an object
			return shouldBeAnObject();
		}

		return null;
	}

	private List<RestAssertError> compareObjects(Map<String, Object> actualMap, Map<String, Object> expectedMap) {
		List<RestAssertError> entriesErrors = checkMissingOrUnexpectedEntries(actualMap.keySet(), expectedMap.keySet());
		if (!entriesErrors.isEmpty()) {
			return unmodifiableList(entriesErrors);
		}

		// Same entries, check types
		List<RestAssertError> comparisonErrors = checkEntries(actualMap, expectedMap);
		if (!comparisonErrors.isEmpty()) {
			return unmodifiableList(comparisonErrors);
		}

		return emptyList();
	}

	private List<RestAssertError> checkMissingOrUnexpectedEntries(Set<String> actualEntries, Set<String> expectedEntries) {
		List<RestAssertError> errors = new LinkedList<>();

		Set<String> missingEntries = new HashSet<>(expectedEntries);
		missingEntries.removeAll(actualEntries);
		for (String missingEntry : missingEntries) {
			errors.add(shouldHaveEntry(contexts.get().toPath(missingEntry)));
		}

		Set<String> unexpectedEntries = new HashSet<>(actualEntries);
		unexpectedEntries.removeAll(expectedEntries);
		for (String unexpectedEntry : unexpectedEntries) {
			errors.add(shouldNotHaveEntry(contexts.get().toPath(unexpectedEntry)));
		}

		return errors;
	}

	private List<RestAssertError> checkEntries(Map<String, Object> actual, Map<String, Object> expected) {
		List<RestAssertError> errors = new LinkedList<>();
		for (Map.Entry<String, Object> entry : actual.entrySet()) {
			String key = entry.getKey();
			errors.addAll(compareValues(key, entry.getValue(), expected.get(key)));
		}
		return errors;
	}

	private List<RestAssertError> compareValues(String key, Object actualObject, Object expectedObject) {
		List<RestAssertError> errors = new LinkedList<>();

		JsonType actualType = parseType(actualObject);
		JsonType expectedType = parseType(expectedObject);
		if (actualType != expectedType) {
			errors.add(shouldBeEntryOf(contexts.get().toPath(key), actualType, expectedType));
		}
		else {
			// Same types, check values
			if (actualType == JsonType.OBJECT) {
				Map<String, Object> newActual = (Map<String, Object>) actualObject;
				Map<String, Object> newExpected = (Map<String, Object>) expectedObject;

				// Compare nested object
				contexts.get().append(key);
				errors.addAll(compareObjects(newActual, newExpected));
				contexts.get().remove();
			}
			else if (actualType == JsonType.ARRAY) {
				List<Object> newActualArray = (List<Object>) actualObject;
				List<Object> newExpectedArray = (List<Object>) expectedObject;

				// Compare arrays
				contexts.get().append(key);
				errors.addAll(compareArrays(newActualArray, newExpectedArray));
				contexts.get().remove();
			}
			else if (actualType != JsonType.NULL && !actualObject.equals(expectedObject)) {
				// Not null and not equals
				errors.add(shouldBeEqualTo(contexts.get().toPath(key), actualObject, expectedObject));
			}
		}

		return errors;
	}

	private List<RestAssertError> compareArrays(List<Object> actualArray, List<Object> expectedArray) {
		List<RestAssertError> errors = new LinkedList<>();

		int actualSize = actualArray.size();
		int expectedSize = expectedArray.size();
		if (actualSize != expectedSize) {
			errors.add(shouldHaveSize(contexts.get().toPath(""), actualSize, expectedSize));
		}

		// Same size
		int size = Math.min(actualSize, expectedSize);
		for (int i = 0; i < size; i++) {
			Object actualObject = actualArray.get(i);
			Object expectedObject = expectedArray.get(i);
			String key = format("[%s]", i);
			errors.addAll(compareValues(key, actualObject, expectedObject));
		}

		return errors;
	}

	private static boolean isObject(String json) {
		return json.charAt(0) == '{' &&
				json.charAt(json.length() - 1) == '}';
	}

	private static boolean isArray(String json) {
		return json.charAt(0) == '[' &&
				json.charAt(json.length() - 1) == ']';
	}
}
