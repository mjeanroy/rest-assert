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

package com.github.mjeanroy.restassert.data;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StrictTransportSecurityTest {
	@Test
	public void it_should_create_strict_transport_security_with_max_age() {
		StrictTransportSecurity sts = new StrictTransportSecurity.Builder(3600).build();
		assertThat(sts.value()).isEqualTo("max-age=3600");
		assertThat(sts.toString()).isEqualTo("max-age=3600");
	}

	@Test
	public void it_should_create_strict_transport_security_with_include_sub_domains() {
		StrictTransportSecurity sts = new StrictTransportSecurity.Builder(3600)
				.includeSubDomains()
				.build();

		assertThat(sts.value()).isEqualTo("max-age=3600; includeSubDomains");
		assertThat(sts.toString()).isEqualTo("max-age=3600; includeSubDomains");
	}

	@Test
	public void it_should_create_strict_transport_security_with_include_preload() {
		StrictTransportSecurity sts = new StrictTransportSecurity.Builder(3600)
				.includeSubDomains()
				.preload()
				.build();

		assertThat(sts.value()).isEqualTo("max-age=3600; includeSubDomains; preload");
		assertThat(sts.toString()).isEqualTo("max-age=3600; includeSubDomains; preload");
	}

	@Test
	public void it_should_create_match_strict_transport_security_with_max_age() {
		StrictTransportSecurity sts = new StrictTransportSecurity.Builder(3600).build();

		assertThat(sts.match("max-age=3600")).isTrue();
		assertThat(sts.match("max-age=1800")).isFalse();
		assertThat(sts.match("max-age=3600; includeSubDomains")).isFalse();
		assertThat(sts.match("max-age=3600; preload")).isFalse();
	}

	@Test
	public void it_should_create_match_strict_transport_security_with_include_sub_domains() {
		StrictTransportSecurity sts = new StrictTransportSecurity.Builder(3600)
				.includeSubDomains()
				.build();

		assertThat(sts.match("max-age=3600; includeSubDomains")).isTrue();
		assertThat(sts.match("max-age=1800")).isFalse();
		assertThat(sts.match("max-age=3600;")).isFalse();
		assertThat(sts.match("max-age=3600; preload")).isFalse();
		assertThat(sts.match("max-age=3600; includeSubDomains; preload")).isFalse();
	}

	@Test
	public void it_should_create_match_strict_transport_security_with_preload() {
		StrictTransportSecurity sts = new StrictTransportSecurity.Builder(3600)
				.includeSubDomains()
				.preload()
				.build();

		assertThat(sts.match("max-age=3600; includeSubDomains; preload")).isTrue();
		assertThat(sts.match("max-age=1800")).isFalse();
		assertThat(sts.match("max-age=3600;")).isFalse();
		assertThat(sts.match("max-age=3600; preload")).isFalse();
		assertThat(sts.match("max-age=3600; includeSubDomains")).isFalse();
	}

	@Test
	public void it_should_create_ignore_duplicated_header() {
		StrictTransportSecurity sts = new StrictTransportSecurity.Builder(3600)
				.includeSubDomains()
				.preload()
				.build();

		assertThat(sts.match("max-age=3600; includeSubDomains; preload; max-age=1800")).isTrue();
		assertThat(sts.match("max-age=1800; includeSubDomains; preload; max-age=3600")).isFalse();
	}

	@Test
	public void it_should_parse_with_case_insensitive_header() {
		StrictTransportSecurity sts = new StrictTransportSecurity.Builder(3600)
				.includeSubDomains()
				.preload()
				.build();

		assertThat(sts.match("max-age=3600; includeSubDomains; preload")).isTrue();
		assertThat(sts.match("MAX-AGE=3600; INCLUDESUBDOMAINS; PRELOAD")).isTrue();
	}

	@Test
	public void it_should_implement_equals() {
		EqualsVerifier.forClass(StrictTransportSecurity.class).verify();
	}
}
