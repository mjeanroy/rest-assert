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

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StringsTest {

	@Test
	public void it_should_join_strings() {
		assertThat(Strings.join(Collections.<String>emptyList(), " ")).isEqualTo("");
		assertThat(Strings.join(asList("foo", "bar"), " ")).isEqualTo("foo bar");
		assertThat(Strings.join(asList("foo", "bar"), " ")).isEqualTo("foo bar");
		assertThat(Strings.join(singletonList("foo"), " ")).isEqualTo("foo");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void it_should_join_strings_using_mapper() {
		final Strings.StringMapper<Integer> mapper = mock(Strings.StringMapper.class);
		when(mapper.apply(anyInt())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) {
				return invocation.getArgument(0).toString();
			}
		});

		final int v1 = 1;
		final int v2 = 2;
		final int v3 = 3;
		final List<Integer> inputs = asList(v1, v2, v3);

		assertThat(Strings.join(inputs, " ", mapper)).isEqualTo("1 2 3");
		verify(mapper).apply(v1);
		verify(mapper).apply(v2);
		verify(mapper).apply(v3);
	}

	@Test
	public void it_should_check_if_string_is_quoted() {
		assertThat(Strings.isQuoted(null)).isFalse();
		assertThat(Strings.isQuoted("")).isFalse();
		assertThat(Strings.isQuoted("a")).isFalse();
		assertThat(Strings.isQuoted("ab")).isFalse();
		assertThat(Strings.isQuoted("abc")).isFalse();
		assertThat(Strings.isQuoted("'a\"")).isFalse();
		assertThat(Strings.isQuoted("\"a'")).isFalse();
		assertThat(Strings.isQuoted("'a'")).isTrue();
		assertThat(Strings.isQuoted("\"a\"")).isTrue();
	}
}
