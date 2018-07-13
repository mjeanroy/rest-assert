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

import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.RequireSriFor;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Sandbox;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Source;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceValue;
import com.github.mjeanroy.restassert.core.internal.exceptions.InvalidHeaderValue;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.BASE_URI;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.BLOCK_ALL_MIXED_CONTENT;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.CHILD_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.CONNECT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.DEFAULT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.DISOWN_OPENER;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.FONT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.FORM_ACTION;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.FRAME_ANCESTORS;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.FRAME_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.IMG_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.MANIFEST_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.MEDIA_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.NAVIGATE_TO;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.OBJECT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.PLUGIN_TYPES;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.PREFETCH_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.REPORT_TO;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.REPORT_URI;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.REQUIRE_SRI_FOR;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.SANDBOX;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.SCRIPT_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.STYLE_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.UPGRADE_INSECURE_REQUEST;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.WORKER_SRC;
import static java.util.Collections.addAll;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

public class ContentSecurityPolicyParserTest {

	private ContentSecurityPolicyParser parser;

	@Before
	public void setUp() {
		parser = ContentSecurityPolicy.parser();
	}

	@Test
	public void it_should_parse_default_src() {
		final String v1 = "'unsafe-inline'";
		final String s = "'unsafe-eval'";
		final String v3 = "'self'";
		final String value = v1 + " " + s + " " + v3;
		final ContentSecurityPolicy csp = parser.parse("default-src " + value + ";");

		assertThat(csp.getDirectives())
			.hasSize(1)
			.containsOnly(
				entry(DEFAULT_SRC, sources(v1, s, v3))
			);
	}

	@Test
	public void it_should_parse_with_sandbox() {
		final String v1 = "allow-scripts";
		final String v2 = "allow-forms";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; sandbox " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(SANDBOX, sources(Sandbox.ALLOW_SCRIPTS, Sandbox.ALLOW_FORMS))
			);
	}

	@Test
	public void it_should_parse_with_script_src() {
		final String v1 = "'unsafe-eval'";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; script-src " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(SCRIPT_SRC, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_with_styles_src() {
		final String v1 = "'unsafe-inline'";
		final String v2 = "'nonce-12345=='";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; style-src " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(STYLE_SRC, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_with_connect_src() {
		final String v1 = "domain.com";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; connect-src " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(CONNECT_SRC, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_font_src() {
		final String v1 = "http://domain.com";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; font-src " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FONT_SRC, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_with_img_src() {
		final String v1 = "http://domain.com";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; img-src " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(IMG_SRC, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_with_media_src() {
		final String v1 = "http://domain.com";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; media-src " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(MEDIA_SRC, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_with_object_src() {
		final String v1 = "http://domain.com";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; object-src " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(OBJECT_SRC, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_with_child_src() {
		final String v1 = "http://domain.com";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; child-src " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(CHILD_SRC, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_with_form_action_src() {
		final String v1 = "http://domain.com";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; form-action " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FORM_ACTION, sources(v1, v2))
			);
	}

	@Test
	public void it_should_check_if_csp_match_header_with_plugin_types() {
		final String v1 = "application/xml";
		final String v2 = "application/json";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; plugin-types " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(PLUGIN_TYPES, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_with_frame_ancestors() {
		final String uri = "http://domain.com";
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; frame-ancestors " + uri);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FRAME_ANCESTORS, sources(uri))
			);
	}

	@Test
	public void it_should_parse_with_report_uri() {
		final String uri = "http://domain.com";
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; report-uri " + uri);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(REPORT_URI, sources(uri))
			);
	}

	@Test
	public void it_should_check_if_csp_match_header_with_base_uri() {
		final String uri = "http://domain.com";
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; base-uri " + uri);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(BASE_URI, sources(uri))
			);
	}

	@Test
	public void it_should_parse_with_block_all_mixed_content() {
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; block-all-mixed-content;");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(BLOCK_ALL_MIXED_CONTENT, sources())
			);
	}

	@Test
	public void it_should_parse_frame_src() {
		final String uri = "https://example.com/";
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; frame-src " + uri + ";");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FRAME_SRC, sources(uri))
			);
	}

	@Test
	public void it_should_parse_manifest_src() {
		final String uri = "https://example.com/";
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; manifest-src " + uri + ";");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(MANIFEST_SRC, sources(uri))
			);
	}

	@Test
	public void it_should_parse_prefetch_src() {
		final String uri = "https://example.com/";
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; prefetch-src " + uri + ";");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(PREFETCH_SRC, sources(uri))
			);
	}

	@Test
	public void it_should_parse_worker_src() {
		final String uri = "https://example.com/";
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; worker-src " + uri + ";");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(WORKER_SRC, sources(uri))
			);
	}

	@Test
	public void it_should_parse_disown_opener() {
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; disown-opener");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(DISOWN_OPENER, sources())
			);
	}

	@Test
	public void it_should_parse_with_navigate_to() {
		final String v1 = "http://domain.com";
		final String v2 = "'unsafe-inline'";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; navigate-to " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(NAVIGATE_TO, sources(v1, v2))
			);
	}

	@Test
	public void it_should_parse_upgrade_insecure_request() {
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; upgrade-insecure-request");

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(UPGRADE_INSECURE_REQUEST, sources())
			);
	}

	@Test
	public void it_should_parse_require_sri_for() {
		final String v1 = "script";
		final String v2 = "style";
		final String value = v1 + " " + v2;
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; require-sri-for " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(REQUIRE_SRI_FOR, sources(RequireSriFor.SCRIPT, RequireSriFor.STYLE))
			);
	}

	@Test
	public void it_should_parse_report_to() {
		final String value = "#test-group";
		final ContentSecurityPolicy csp = parser.parse("default-src 'none'; report-to " + value);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(REPORT_TO, sources(value))
			);
	}

	@Test
	public void it_should_ignore_duplicated_directives() {
		ContentSecurityPolicy csp = parser.parse("default-src 'none'; default-src 'unsafe-inline';");

		assertThat(csp.getDirectives())
			.hasSize(1)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'"))
			);
	}

	@Test
	public void it_should_fail_if_directive_name_is_not_found() {
		assertThatThrownBy(parse(parser, "default-src 'none'; foo http://domain.com"))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cannot parse Content-Security-Policy value since directive foo seems not valid");
	}

	@Test
	public void it_should_fail_if_directive_does_not_have_name() {
		assertThatThrownBy(parse(parser, "default-src 'none'; ;"))
				.isExactlyInstanceOf(InvalidHeaderValue.class)
				.hasMessage("Content-Security-Policy value 'default-src 'none'; ;' is not a valid one.");
	}

	private static Set<Source> sources() {
		return emptySet();
	}

	private static Set<Source> sources(String value, String... values) {
		Set<Source> sources = new LinkedHashSet<>();
		sources.add(new SourceValue(value));

		for (String v : values) {
			sources.add(new SourceValue(v));
		}

		return sources;
	}

	private static Set<Source> sources(Source value, Source... values) {
		Set<Source> sources = new LinkedHashSet<>();
		sources.add(value);
		addAll(sources, values);
		return sources;
	}

	private static ThrowingCallable parse(final ContentSecurityPolicyParser parser, final String value) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				parser.parse(value);
			}
		};
	}
}
