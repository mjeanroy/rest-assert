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

package com.github.mjeanroy.restassert.assertj.api;

import com.github.mjeanroy.restassert.core.internal.json.parsers.JsonParsers;
import org.assertj.core.api.BooleanAssert;
import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.MapAssert;
import org.assertj.core.api.StringAssert;

import java.util.List;
import java.util.Map;

public class JsonAssert extends AbstractJsonAssert<JsonAssert> {

	public JsonAssert(String actual) {
		super(actual, JsonAssert.class);
	}

	/**
	 * Ensure the actual JSON is a valid JSON of type `string`, the parsed value becoming the new value
	 * under test.
	 *
	 * Examples:
	 *
	 * <pre><code class='java'>
	 *   // assertion succeeds:
	 *   assertThatJson(&quot;\&quot;Hello world\&quot;&quot;).asString().isEqualTo(
	 *     &quot;Hello World&quot;
	 *   );
	 * </code></pre>
	 *
	 * @return a new {@link StringAssert} instance whose value under test is the result of the parse.
	 * @throws AssertionError if the actual value cannot be parsed as a JSON string.
	 */
	public StringAssert asString() {
		isString();
		return new StringAssert(
			parse(String.class)
		);
	}

	/**
	 * Ensure the actual JSON is a valid JSON of type `number`, the parsed value becoming the new value
	 * under test.
	 *
	 * Examples:
	 *
	 * <pre><code class='java'>
	 *   // assertion succeeds:
	 *   assertThatJson(&quot;0.5&quot;).asNumber().isEqualTo(0.5);
	 * </code></pre>
	 *
	 * @return a new {@link DoubleAssert} instance whose value under test is the result of the parse.
	 * @throws AssertionError if the actual value cannot be parsed as a JSON number.
	 */
	public DoubleAssert asNumber() {
		isNumber();
		return new DoubleAssert(
			parse(Double.class)
		);
	}

	/**
	 * Ensure the actual JSON is a valid JSON of type `boolean`, the parsed value becoming the new value
	 * under test.
	 *
	 * Examples:
	 *
	 * <pre><code class='java'>
	 *   // assertion succeeds:
	 *   assertThatJson(&quot;false&quot;).asBoolean().isFalse();
	 *   assertThatJson(&quot;true&quot;).asBoolean().isTrue();
	 * </code></pre>
	 *
	 * @return a new {@link BooleanAssert} instance whose value under test is the result of the parse.
	 * @throws AssertionError if the actual value cannot be parsed as a JSON boolean.
	 */
	public BooleanAssert asBoolean() {
		isBoolean();
		return new BooleanAssert(
			parse(Boolean.class)
		);
	}

	/**
	 * Ensure the actual JSON is a valid JSON of type `array`, the parsed value becoming the new value
	 * under test.
	 *
	 * Examples:
	 *
	 * <pre><code class='java'>
	 *   // assertion succeeds:
	 *   assertThatJson(&quot;[0,1,2]&quot;).asArray().hasSize(3).containsExactly(0, 1, 2);
	 *   assertThatJson(&quot;[]&quot;).asArray().isEmpty();
	 * </code></pre>
	 *
	 * @return a new {@link ListAssert} instance whose value under test is the result of the parse.
	 * @throws AssertionError if the actual value cannot be parsed as a JSON array.
	 */
	public ListAssert<Object> asArray() {
		isArray();
		return new ListAssert<Object>(
			parse(List.class)
		);
	}

	/**
	 * Ensure the actual JSON is a valid JSON of type `object`, the parsed value becoming the new value
	 * under test.
	 *
	 * Examples:
	 *
	 * <pre><code class='java'>
	 *   // assertion succeeds:
	 *   assertThatJson(&quot;{\&quot;id\&quot;:1}&quot;).asObject().hasSize(1).containsEntry(
	 *     entry("id", 1)
	 *   );
	 * </code></pre>
	 *
	 * @return a new {@link MapAssert} instance whose value under test is the result of the parse.
	 * @throws AssertionError if the actual value cannot be parsed as a JSON object.
	 */
	@SuppressWarnings("unchecked")
	public MapAssert<String, Object> asObject() {
		isObject();
		return new MapAssert<String, Object>(
			parse(Map.class)
		);
	}

	@SuppressWarnings("unchecked")
	private <T> T parse(Class<T> type) {
		Object v = JsonParsers.getParser().parse(actual);

		if (v == null || !type.isAssignableFrom(v.getClass())) {
			throw new AssertionError(
				"Expecting JSON to be of type " + type.getName() + ", but was " + (v == null ? "null" : v.getClass())
			);
		}

		return (T) v;
	}
}
