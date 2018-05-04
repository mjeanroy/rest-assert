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

package com.github.mjeanroy.restassert.core.internal.common;

import com.github.mjeanroy.restassert.core.internal.common.Collections.Mapper;
import com.github.mjeanroy.restassert.core.internal.common.Collections.Predicate;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CollectionsTest {

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_map_inputs_to_outputs() {
		List<Integer> inputs = asList(1, 2, 3);

		Mapper<Integer, Integer> mapper = mock(Mapper.class);

		when(mapper.apply(anyInt())).thenAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) {
				int input = (Integer) invocation.getArguments()[0];
				return input * input;
			}
		});

		List<Integer> outputs = Collections.map(inputs, mapper);

		assertThat(outputs)
				.isNotNull()
				.hasSameSizeAs(inputs)
				.contains(1, 4, 9);

		verify(mapper).apply(1);
		verify(mapper).apply(2);
		verify(mapper).apply(3);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_map_inputs_array_to_outputs() {
		Integer[] inputs = new Integer[] {1, 2, 3};

		Mapper<Integer, Integer> mapper = mock(Mapper.class);

		when(mapper.apply(anyInt())).thenAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) {
				int input = (Integer) invocation.getArguments()[0];
				return input * input;
			}
		});

		List<Integer> outputs = Collections.map(inputs, mapper);

		assertThat(outputs)
				.isNotNull()
				.hasSameSizeAs(inputs)
				.contains(1, 4, 9);

		verify(mapper).apply(1);
		verify(mapper).apply(2);
		verify(mapper).apply(3);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_filter_inputs() {
		List<Integer> inputs = asList(1, 2, 3);

		Predicate<Integer> predicate = mock(Predicate.class);

		when(predicate.apply(anyInt())).thenAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) {
				int input = (Integer) invocation.getArguments()[0];
				return input % 2 == 0;
			}
		});

		List<Integer> outputs = Collections.filter(inputs, predicate);

		assertThat(outputs)
				.isNotNull()
				.hasSize(1)
				.contains(2);

		verify(predicate).apply(1);
		verify(predicate).apply(2);
		verify(predicate).apply(3);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_return_true_if_some_find_a_match() {
		Predicate<String> predicate = mock(Predicate.class);
		when(predicate.apply(anyString())).thenAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) {
				return invocation.getArguments()[0].equals("foo");
			}
		});

		List<String> inputs = asList("quix", "foo", "bar");

		boolean found = Collections.some(inputs, predicate);

		assertThat(found).isTrue();
		verify(predicate).apply("quix");
		verify(predicate).apply("foo");
		verify(predicate, never()).apply("bar");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_return_false_if_some_does_not_find_a_match() {
		Predicate<String> predicate = mock(Predicate.class);
		when(predicate.apply(anyString())).thenAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) {
				return invocation.getArguments()[0].equals("foo");
			}
		});

		List<String> inputs = asList("quix", "bar");

		boolean found = Collections.some(inputs, predicate);

		assertThat(found).isFalse();
		verify(predicate).apply("quix");
		verify(predicate).apply("bar");
	}
}
