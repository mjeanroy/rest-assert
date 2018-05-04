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

package com.github.mjeanroy.restassert.core.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StrictTransportSecurityBuilderTest {

	@Test
	public void it_should_create_strict_transport_security_with_max_age() {
		StrictTransportSecurity sts = StrictTransportSecurity.builder(3600).build();

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isFalse();
		assertThat(sts.isPreload()).isFalse();
	}

	@Test
	public void it_should_create_strict_transport_security_with_include_sub_domains() {
		StrictTransportSecurity sts = StrictTransportSecurity.builder(3600)
			.includeSubDomains()
			.build();

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isTrue();
		assertThat(sts.isPreload()).isFalse();
	}

	@Test
	public void it_should_create_strict_transport_security_with_include_preload() {
		StrictTransportSecurity sts = StrictTransportSecurity.builder(3600)
			.includeSubDomains()
			.preload()
			.build();

		assertThat(sts.getMaxAge()).isEqualTo(3600);
		assertThat(sts.isIncludeSubDomains()).isTrue();
		assertThat(sts.isPreload()).isTrue();
	}
}
