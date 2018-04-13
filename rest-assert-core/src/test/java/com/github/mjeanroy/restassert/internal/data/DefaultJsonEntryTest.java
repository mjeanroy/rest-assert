/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.restassert.internal.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultJsonEntryTest {

	@Test
	public void it_should_create_json_entry() {
		String key = "foo";
		String value = "bar";
		JsonEntry entry = new DefaultJsonEntry(key, value);
		assertThat(entry.getKey()).isEqualTo(key);
		assertThat(entry.getValue()).isEqualTo(value);
	}

	@Test
	public void it_should_have_to_string() {
		JsonEntry entry = new DefaultJsonEntry("foo", "bar");
		assertThat(entry.toString()).isEqualTo("foo = bar");
	}

	@Test
	public void it_should_implement_equals() {
		JsonEntry e1 = new DefaultJsonEntry("foo", "bar");
		JsonEntry e2 = new DefaultJsonEntry("foo", "bar");
		JsonEntry e3 = new DefaultJsonEntry("foo", "bar");
		JsonEntry e4 = new DefaultJsonEntry("foo", "quix");

		assertThat(e1.equals(null)).isFalse();
		assertThat(e1.equals(e4)).isFalse();
		assertThat(e4.equals(e1)).isFalse();

		// Reflective
		assertThat(e1.equals(e1)).isTrue();

		// Symmetric
		assertThat(e1.equals(e2)).isTrue();
		assertThat(e2.equals(e1)).isTrue();

		// Transitive
		assertThat(e1.equals(e2)).isTrue();
		assertThat(e2.equals(e3)).isTrue();
		assertThat(e1.equals(e3)).isTrue();
	}

	@Test
	public void it_should_implement_hash_code() {
		JsonEntry e1 = new DefaultJsonEntry("foo", "bar");
		JsonEntry e2 = new DefaultJsonEntry("foo", "bar");
		assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
	}
}
