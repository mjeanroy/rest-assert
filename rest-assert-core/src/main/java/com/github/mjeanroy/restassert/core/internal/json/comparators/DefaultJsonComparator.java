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

package com.github.mjeanroy.restassert.core.internal.json.comparators;

import com.github.mjeanroy.restassert.core.internal.error.RestAssertError;
import com.github.mjeanroy.restassert.core.internal.error.RestAssertJsonError;
import com.github.mjeanroy.restassert.core.internal.json.JsonType;
import com.github.mjeanroy.restassert.core.internal.json.parsers.JsonParser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldBeAnArray.shouldBeAnArray;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldBeAnObject.shouldBeAnObject;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldBeEntryOf.shouldBeEntryOf;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntry.shouldHaveEntry;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntryEqualTo.shouldHaveEntryEqualTo;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldHaveEntryWithSize.shouldHaveEntryWithSize;
import static com.github.mjeanroy.restassert.core.internal.error.json.ShouldNotHaveEntry.shouldNotHaveEntry;
import static com.github.mjeanroy.restassert.core.internal.json.JsonType.parseType;
import static com.github.mjeanroy.restassert.core.internal.json.comparators.JsonContext.rootContext;
import static java.lang.String.format;
import static java.util.Collections.singletonList;

/**
 * Default implementation for {@link JsonComparator}
 * interface.
 */
public class DefaultJsonComparator implements JsonComparator {

	/**
	 * JSON parser.
	 */
	private final JsonParser parser;

	// Use thread local to remains thread safe
	private final ThreadLocal<JsonContext> contexts = new ThreadLocal<>();

	/**
	 * Create new comparator with custom option.
	 *
	 * @param parser  Parser used to extract json data.
	 */
	public DefaultJsonComparator(JsonParser parser) {
		this.parser = parser;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<RestAssertError> compare(String actual, String expected) {
		contexts.set(rootContext());
		List<RestAssertJsonError> errors = doCompare(actual.trim(), expected.trim());
		contexts.remove();

		return (List) errors;
	}

	private List<RestAssertJsonError> doCompare(String actual, String expected) {
		RestAssertJsonError error = checkType(actual, expected);
		if (error != null) {
			return singletonList(error);
		}

		// Same types
		boolean isObject = isObject(actual);
		if (isObject) {
			Map<String, Object> actualMap = parser.parseObject(actual);
			Map<String, Object> expectedMap = parser.parseObject(expected);
			return compareObjects(actualMap, expectedMap);
		} else {
			List<Object> actualArray = parser.parseArray(actual);
			List<Object> expectedArray = parser.parseArray(expected);
			return compareArrays(actualArray, expectedArray);
		}
	}

	private RestAssertJsonError checkType(String actual, String expected) {
		if (isObject(actual) && isArray(expected)) {
			return shouldBeAnArray();
		} else if (isArray(actual) && isObject(expected)) {
			// It should be an object
			return shouldBeAnObject();
		}

		return null;
	}

	private List<RestAssertJsonError> compareObjects(Map<String, Object> actualMap, Map<String, Object> expectedMap) {
		LinkedList<RestAssertJsonError> errors = new LinkedList<>();
		errors.addAll(checkMissingOrUnexpectedEntries(actualMap.keySet(), expectedMap.keySet()));
		errors.addAll(checkEntries(actualMap, expectedMap));
		return errors;
	}

	private List<RestAssertJsonError> checkMissingOrUnexpectedEntries(Set<String> actualEntries, Set<String> expectedEntries) {
		List<RestAssertJsonError> errors = new LinkedList<>();

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

	private List<RestAssertJsonError> checkEntries(Map<String, Object> actual, Map<String, Object> expected) {
		List<RestAssertJsonError> errors = new LinkedList<>();
		for (Map.Entry<String, Object> entry : actual.entrySet()) {
			String key = entry.getKey();
			if (expected.containsKey(key)) {
				errors.addAll(compareValues(key, entry.getValue(), expected.get(key)));
			}
		}
		return errors;
	}

	private List<RestAssertJsonError> compareValues(String key, Object actualObject, Object expectedObject) {
		List<RestAssertJsonError> errors = new LinkedList<>();

		JsonType actualType = parseType(actualObject);
		JsonType expectedType = parseType(expectedObject);
		if (actualType != expectedType) {
			errors.add(shouldBeEntryOf(contexts.get().toPath(key), actualType, expectedType));
		} else {
			// Same types, check values
			if (actualType == JsonType.OBJECT) {
				@SuppressWarnings("unchecked") Map<String, Object> newActual = (Map<String, Object>) actualObject;
				@SuppressWarnings("unchecked") Map<String, Object> newExpected = (Map<String, Object>) expectedObject;

				// Compare nested object
				contexts.get().append(key);
				errors.addAll(compareObjects(newActual, newExpected));
				contexts.get().remove();
			} else if (actualType == JsonType.ARRAY) {
				@SuppressWarnings("unchecked") List<Object> newActualArray = (List<Object>) actualObject;
				@SuppressWarnings("unchecked") List<Object> newExpectedArray = (List<Object>) expectedObject;

				// Compare arrays
				contexts.get().append(key);
				errors.addAll(compareArrays(newActualArray, newExpectedArray));
				contexts.get().remove();
			} else if (actualType != JsonType.NULL && !actualObject.equals(expectedObject)) {
				// Not null and not equals
				errors.add(shouldHaveEntryEqualTo(contexts.get().toPath(key), actualObject, expectedObject));
			}
		}

		return errors;
	}

	private List<RestAssertJsonError> compareArrays(List<Object> actualArray, List<Object> expectedArray) {
		List<RestAssertJsonError> errors = new LinkedList<>();

		int actualSize = actualArray.size();
		int expectedSize = expectedArray.size();
		if (actualSize != expectedSize) {
			errors.add(shouldHaveEntryWithSize(contexts.get().toPath(""), actualSize, expectedSize));
		}

		// Same size
		int size = Math.min(actualSize, expectedSize);
		Iterator<Object> it1 = actualArray.iterator();
		Iterator<Object> it2 = expectedArray.iterator();
		for (int i = 0; i < size; ++i) {
			Object actualObject = it1.next();
			Object expectedObject = it2.next();
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
