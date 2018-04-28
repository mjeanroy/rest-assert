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

package com.github.mjeanroy.restassert.test.data;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class HeaderTest {

	@Test
	public void it_should_create_header() {
		String name = "foo";
		String value = "bar";
		Header header = Header.header(name, value);

		assertThat(header).isNotNull();
		assertThat(header.getName()).isEqualTo(name);
		assertThat(header.getValue()).isEqualTo(value);
		assertThat(header.getValues()).isEqualTo(singletonList(value));
	}

	@Test
	public void it_should_create_multi_value_header() {
		String name = "foo";
		String v1 = "bar1";
		String v2 = "bar1";
		String value = v1 + "," + v2;
		Header header = Header.header(name, value);

		assertThat(header).isNotNull();
		assertThat(header.getName()).isEqualTo(name);
		assertThat(header.getValue()).isEqualTo(value);
		assertThat(header.getValues()).isEqualTo(asList(v1, v2));
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(Header.class).verify();
	}

	@Test
	public void it_should_implement_to_string() {
		String name = "foo";
		String value = "bar";
		Header header = Header.header(name, value);
		assertThat(header.toString()).isEqualTo("Header{name=foo, value=bar}");
	}
}
