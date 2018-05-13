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

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import com.github.mjeanroy.restassert.core.data.XssProtection.Directive;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class XssProtectionTest {

	@Test
	public void it_should_create_disable_header() {
		final XssProtection header = XssProtection.disable();
		assertThat(header.getDirective()).isEqualTo(Directive.DISABLE);
		assertThat(header.getParameter()).isNull();
		assertThat(header.serializeValue()).isEqualTo("0");
		assertThat(header.toString()).isEqualTo(
			"XssProtection{" +
				"directive=DISABLE, " +
				"parameter=null" +
			"}"
		);
	}

	@Test
	public void it_should_create_enable_header() {
		final XssProtection header = XssProtection.enable();
		assertThat(header.getDirective()).isEqualTo(Directive.ENABLE);
		assertThat(header.getParameter()).isNull();
		assertThat(header.serializeValue()).isEqualTo("1");
		assertThat(header.toString()).isEqualTo(
			"XssProtection{" +
				"directive=ENABLE, " +
				"parameter=null" +
			"}"
		);
	}

	@Test
	public void it_should_create_enable_with_mode_block_header() {
		final XssProtection header = XssProtection.enableModeBlock();
		assertThat(header.getDirective()).isEqualTo(Directive.ENABLE);
		assertThat(header.getParameter().getName()).isEqualTo("mode");
		assertThat(header.getParameter().getValue()).isEqualTo("block");
		assertThat(header.serializeValue()).isEqualTo("1; mode=block");
		assertThat(header.toString()).isEqualTo(
			"XssProtection{" +
				"directive=ENABLE, " +
				"parameter=Parameter{name=mode, value=block}" +
			"}"
		);
	}

	@Test
	public void it_should_create_enable_with_report_uri_string_header() {
		final String uri = "https://google.com";
		final XssProtection header = XssProtection.enableModeReport(uri);
		assertThat(header.getDirective()).isEqualTo(Directive.ENABLE);
		assertThat(header.getParameter().getName()).isEqualTo("report");
		assertThat(header.getParameter().getValue()).isEqualTo(uri);
		assertThat(header.serializeValue()).isEqualTo("1; report=https://google.com");
		assertThat(header.toString()).isEqualTo(
			"XssProtection{" +
				"directive=ENABLE, " +
				"parameter=Parameter{name=report, value=https://google.com}" +
			"}"
		);
	}

	@Test
	public void it_should_create_enable_with_report_uri_header() {
		final String uri = "https://google.com";
		final XssProtection header = XssProtection.enableModeReport(URI.create(uri));
		assertThat(header.getDirective()).isEqualTo(Directive.ENABLE);
		assertThat(header.getParameter().getName()).isEqualTo("report");
		assertThat(header.getParameter().getValue()).isEqualTo(uri);
		assertThat(header.serializeValue()).isEqualTo("1; report=https://google.com");
		assertThat(header.toString()).isEqualTo(
			"XssProtection{" +
				"directive=ENABLE, " +
				"parameter=Parameter{name=report, value=https://google.com}" +
			"}"
		);
	}

	@Test
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(XssProtection.class).verify();
	}
}
