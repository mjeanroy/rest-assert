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

package com.github.mjeanroy.rest_assert.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.github.mjeanroy.rest_assert.utils.Utils.firstNonNull;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UtilsTest {

	private static final String BR = System.getProperty("line.separator");

	@Rule
	public ExpectedException thrown = none();

	@Test
	public void it_should_get_first_non_null_value() {
		assertThat(firstNonNull(null, null)).isNull();
		assertThat(firstNonNull("foo", null)).isEqualTo("foo");
		assertThat(firstNonNull(null, "bar")).isEqualTo("bar");
		assertThat(firstNonNull("foo", "bar")).isEqualTo("foo");
	}

	@Test
	public void it_should_not_throw_npe_if_value_is_not_null() {
		String value = "foo";
		String result = Utils.notNull(value, "message");
		assertThat(result).isEqualTo(value);
	}

	@Test
	public void it_should_throw_npe_if_value_is_null() {
		String message = "message";

		thrown.expect(NullPointerException.class);
		thrown.expectMessage(message);

		Utils.notNull(null, "message");
	}

	@Test
	public void it_should_map_inputs_to_outputs() {
		List<Integer> inputs = asList(1, 2, 3);

		@SuppressWarnings("unchecked")
		Mapper<Integer, Integer> mapper = mock(Mapper.class);

		when(mapper.apply(anyInt())).thenAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				int input = (Integer) invocation.getArguments()[0];
				return input * input;
			}
		});

		List<Integer> outputs = Utils.map(inputs, mapper);

		assertThat(outputs)
			.isNotNull()
			.hasSameSizeAs(inputs)
			.contains(1, 4, 9);

		verify(mapper).apply(1);
		verify(mapper).apply(2);
		verify(mapper).apply(3);
	}

	@Test
	public void it_should_filter_inputs() {
		List<Integer> inputs = asList(1, 2, 3);

		@SuppressWarnings("unchecked")
		Predicate<Integer> predicate = mock(Predicate.class);

		when(predicate.apply(anyInt())).thenAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				int input = (Integer) invocation.getArguments()[0];
				return input % 2 == 0;
			}
		});

		List<Integer> outputs = Utils.filter(inputs, predicate);

		assertThat(outputs)
			.isNotNull()
			.hasSize(1)
			.contains(2);

		verify(predicate).apply(1);
		verify(predicate).apply(2);
		verify(predicate).apply(3);
	}

	@Test
	public void it_should_read_file_to_string() throws Exception {
		URL resource = getClass().getResource("/test.txt");
		Path path = Paths.get(resource.toURI());

		String content = Utils.readFileToString(path);

		String expectedContent =
			"Hello World" + BR +
			"Foo Bar" + BR +
			"Test";

		assertThat(content)
			.isNotNull()
			.isNotEmpty()
			.isEqualTo(expectedContent);
	}

	@Test
	public void it_should_fail_to_read_file_to_string_with_custom_exception() throws Exception {
		URL resource = getClass().getResource("/test-not-utf-8.txt");
		Path path = Paths.get(resource.toURI());

		thrown.expect(UnreadableFileException.class);

		Utils.readFileToString(path);
	}
}