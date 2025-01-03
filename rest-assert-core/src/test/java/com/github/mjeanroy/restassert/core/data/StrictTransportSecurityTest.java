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

package com.github.mjeanroy.restassert.core.data;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StrictTransportSecurityTest {

	@Test
	void it_should_create_strict_transport_security_with_max_age() {
		StrictTransportSecurity sts = new StrictTransportSecurity(3600, false, false);

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isFalse();
		assertThat(sts.isPreload()).isFalse();
		assertThat(sts.serializeValue()).isEqualTo("max-age=3600");
		assertThat(sts.toString()).isEqualTo(
			"StrictTransportSecurity{" +
				"maxAge=3600, " +
				"includeSubDomains=false, " +
				"preload=false" +
				"}"
		);
	}

	@Test
	void it_should_create_strict_transport_security_with_include_sub_domains() {
		StrictTransportSecurity sts = new StrictTransportSecurity(3600, true, false);

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isTrue();
		assertThat(sts.isPreload()).isFalse();
		assertThat(sts.serializeValue()).isEqualTo("max-age=3600; includeSubDomains");
		assertThat(sts.toString()).isEqualTo(
			"StrictTransportSecurity{" +
				"maxAge=3600, " +
				"includeSubDomains=true, " +
				"preload=false" +
				"}"
		);
	}

	@Test
	void it_should_create_strict_transport_security_with_include_preload() {
		StrictTransportSecurity sts = new StrictTransportSecurity(3600, true, true);

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isTrue();
		assertThat(sts.isPreload()).isTrue();
		assertThat(sts.serializeValue()).isEqualTo("max-age=3600; includeSubDomains; preload");
		assertThat(sts.toString()).isEqualTo(
			"StrictTransportSecurity{" +
				"maxAge=3600, " +
				"includeSubDomains=true, " +
				"preload=true" +
				"}"
		);
	}

	@Test
	void it_should_implement_equals() {
		EqualsVerifier.forClass(StrictTransportSecurity.class).verify();
	}
}
