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

import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Sandbox;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Source;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceValue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.BASE_URI;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.BLOCK_ALL_MIXED_CONTENT;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.CHILD_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.CONNECT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.DEFAULT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.FONT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.FORM_ACTION;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.FRAME_ANCESTORS;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.IMG_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.MEDIA_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.OBJECT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.PLUGIN_TYPES;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.REPORT_URI;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.SANDBOX;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.SCRIPT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.STYLE_SRC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class ContentSecurityPolicyParserTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_parse_default_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'unsafe-inline' 'unsafe-eval' 'self';");

		assertThat(csp.getDirectives())
			.hasSize(1)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'unsafe-inline'", "'unsafe-eval'", "'self'"))
			);
	}

	@Test
	public void it_should_parse_with_sandbox() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; sandbox allow-scripts allow-forms");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(SANDBOX, sources(Sandbox.ALLOW_SCRIPTS, Sandbox.ALLOW_FORMS))
			);
	}

	@Test
	public void it_should_parse_with_script_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; script-src 'unsafe-eval' 'unsafe-inline'");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(SCRIPT_SRC, sources("'unsafe-eval'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_parse_with_styles_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; style-src 'unsafe-inline' 'nonce-12345=='");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(STYLE_SRC, sources("'unsafe-inline'", "'nonce-12345=='"))
			);
	}

	@Test
	public void it_should_parse_with_connect_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; connect-src domain.com 'unsafe-inline'");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(CONNECT_SRC, sources("domain.com", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_parse_font_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; font-src http://domain.com 'unsafe-inline'");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FONT_SRC, sources("http://domain.com", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_parse_with_img_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; img-src http://domain.com 'unsafe-inline'");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(IMG_SRC, sources("http://domain.com", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_parse_with_media_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; media-src http://domain.com 'unsafe-inline'");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(MEDIA_SRC, sources("http://domain.com", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_parse_with_object_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; object-src http://domain.com 'unsafe-inline'");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(OBJECT_SRC, sources("http://domain.com", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_parse_with_child_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; child-src http://domain.com 'unsafe-inline'");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(CHILD_SRC, sources("http://domain.com", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_parse_with_form_action_src() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; form-action http://domain.com 'unsafe-inline'");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FORM_ACTION, sources("http://domain.com", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_check_if_csp_match_header_with_plugin_types() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; plugin-types application/xml application/json");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(PLUGIN_TYPES, sources("application/xml", "application/json"))
			);
	}

	@Test
	public void it_should_parse_with_frame_ancestors() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; frame-ancestors http://domain.com");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FRAME_ANCESTORS, sources("http://domain.com"))
			);
	}

	@Test
	public void it_should_parse_with_report_uri() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; report-uri http://domain.com");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(REPORT_URI, sources("http://domain.com"))
			);
	}

	@Test
	public void it_should_check_if_csp_match_header_with_base_uri() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; base-uri http://domain.com");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(BASE_URI, sources("http://domain.com"))
			);
	}

	@Test
	public void it_should_parse_with_block_all_mixed_content() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; block-all-mixed-content;");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(BLOCK_ALL_MIXED_CONTENT, Collections.<Source>emptySet())
			);
	}

	@Test
	public void it_should_ignore_duplicated_directives() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; default-src 'unsafe-inline';");

		assertThat(csp.getDirectives())
			.hasSize(1)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'"))
			);
	}

	@Test
	public void it_should_fail_if_directive_name_is_not_found() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cannot parse Content-Security-Policy value since directive foo seems not valid");

		parser.parse("default-src 'none'; foo http://domain.com");
	}

	@Test
	public void it_should_fail_if_directive_does_not_have_name() {
		ContentSecurityPolicyParser parser = ContentSecurityPolicy.parser();

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Header default-src 'none'; ; is not a valid Content-Security-Policy value");

		parser.parse("default-src 'none'; ;");
	}

	private static Set<Source> sources(String... values) {
		Set<Source> sources = new LinkedHashSet<>();
		for (String value : values) {
			sources.add(new SourceValue(value));
		}

		return sources;
	}

	private static Set<Source> sources(Source... values) {
		Set<Source> sources = new LinkedHashSet<>();
		Collections.addAll(sources, values);
		return sources;
	}
}
