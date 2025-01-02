/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class StringsTest {

	@Test
	void it_should_check_if_string_is_surrounded_by_given_chars() {
		assertThat(Strings.isSurroundedBy(null, '[', ']')).isFalse();
		assertThat(Strings.isSurroundedBy("", '[', ']')).isFalse();
		assertThat(Strings.isSurroundedBy("[", '[', ']')).isFalse();

		assertThat(Strings.isSurroundedBy("[]", '[', ']')).isTrue();
		assertThat(Strings.isSurroundedBy("[0,1,2]", '[', ']')).isTrue();
	}

	@Test
	void it_should_check_if_character_is_a_vowel() {
		List<Character> vowels = asList('a', 'e', 'i', 'o', 'u');

		for (char c : vowels) {
			assertThat(Strings.isVowel(Character.toLowerCase(c))).isTrue();
			assertThat(Strings.isVowel(Character.toUpperCase(c))).isTrue();
		}

		assertThat(Strings.isVowel('x')).isFalse();
		assertThat(Strings.isVowel('X')).isFalse();
	}

	@Test
	void it_should_check_if_string_is_quoted() {
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

	@Test
	void it_should_check_if_string_is_empty() {
		assertThat(Strings.isEmpty(null)).isTrue();
		assertThat(Strings.isEmpty("")).isTrue();
		assertThat(Strings.isEmpty(" ")).isFalse();
	}

	@Test
	void it_should_check_if_string_is_not_empty() {
		assertThat(Strings.isNotEmpty(null)).isFalse();
		assertThat(Strings.isNotEmpty("")).isFalse();
		assertThat(Strings.isNotEmpty(" ")).isTrue();
	}

	@Test
	void it_should_trim_value() {
		assertThat(Strings.trim(null)).isNull();
		assertThat(Strings.trim("")).isEqualTo("");
		assertThat(Strings.trim("   ")).isEqualTo("");
		assertThat(Strings.trim(" test ")).isEqualTo("test");
	}

	@Test
	void it_should_trim_value_to_null() {
		assertThat(Strings.trimToNull(null)).isNull();
		assertThat(Strings.trimToNull("")).isNull();
		assertThat(Strings.trimToNull("   ")).isNull();
		assertThat(Strings.trimToNull(" test ")).isEqualTo("test");
	}
}
