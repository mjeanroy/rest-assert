/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonComparatorOptionsTest {

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_create_builder() throws Exception {
		JsonComparatorOptions.Builder builder = JsonComparatorOptions.builder();
		assertThat(builder).isNotNull();

		Collection<String> ignoringKeys = (Collection<String>) readField(builder, "ignoringKeys", true);
		assertThat(ignoringKeys).isNotNull().isEmpty();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_add_ignored_key() throws Exception {
		JsonComparatorOptions.Builder builder = JsonComparatorOptions.builder();
		JsonComparatorOptions.Builder result = builder.ignoreKeys("foo");

		assertThat(result).isSameAs(builder);

		Collection<String> ignoringKeys = (Collection<String>) readField(builder, "ignoringKeys", true);
		assertThat(ignoringKeys)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1)
			.contains("foo");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_add_ignored_keys() throws Exception {
		JsonComparatorOptions.Builder builder = JsonComparatorOptions.builder();
		JsonComparatorOptions.Builder result = builder.ignoreKeys("foo", "bar");

		assertThat(result).isSameAs(builder);

		Collection<String> ignoringKeys = (Collection<String>) readField(builder, "ignoringKeys", true);
		assertThat(ignoringKeys)
			.isNotNull()
			.isNotEmpty()
			.hasSize(2)
			.contains("foo", "bar");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_add_collection_of_ignored_keys() throws Exception {
		JsonComparatorOptions.Builder builder = JsonComparatorOptions.builder();
		JsonComparatorOptions.Builder result = builder.ignoreKeys(asList("foo", "bar"));

		assertThat(result).isSameAs(builder);

		Collection<String> ignoringKeys = (Collection<String>) readField(builder, "ignoringKeys", true);
		assertThat(ignoringKeys)
			.isNotNull()
			.isNotEmpty()
			.hasSize(2)
			.contains("foo", "bar");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_add_ignored_keys_and_ignore_duplicated() throws Exception {
		JsonComparatorOptions.Builder builder = JsonComparatorOptions.builder();
		JsonComparatorOptions.Builder result = builder.ignoreKeys("foo", "foo");

		assertThat(result).isSameAs(builder);

		Collection<String> ignoringKeys = (Collection<String>) readField(builder, "ignoringKeys", true);
		assertThat(ignoringKeys)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1)
			.contains("foo");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_create_options_object() throws Exception {
		JsonComparatorOptions options = JsonComparatorOptions.builder()
			.ignoreKeys("foo")
			.build();

		assertThat(options).isNotNull();

		Collection<String> ignoringKeys = (Collection<String>) readField(options, "ignoringKeys", true);
		assertThat(ignoringKeys)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1)
			.contains("foo");
	}
}
