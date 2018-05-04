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

import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Source;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Sandbox;
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
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.host;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.none;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.self;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.unsafeInline;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class ContentSecurityPolicyBuilderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_handle_default_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none';");
		assertThat(csp.getDirectives())
			.hasSize(1)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'"))
			);
	}

	@Test
	public void it_should_handle_style_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addStyleSrc(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; style-src 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(STYLE_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_script_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addScriptSrc(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; script-src 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(SCRIPT_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_connect_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addConnectSrc(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; connect-src 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(CONNECT_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_child_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addChildSrc(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; child-src 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(CHILD_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_font_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addFontSrc(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; font-src 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FONT_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_media_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addMediaSrc(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; media-src 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(MEDIA_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_form_actions() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addFormAction(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; form-action 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FORM_ACTION, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_img_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addImgSrc(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; img-src 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(IMG_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_object_src() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addObjectSrc(self(), unsafeInline())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; object-src 'self' 'unsafe-inline';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(OBJECT_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_base_uri() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addBaseUri(self())
				.build();

		assertThat(csp.serializeValue()).isEqualTo("base-uri 'self'; default-src 'none';");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(BASE_URI, sources("'self'"))
			);
	}

	@Test
	public void it_should_handle_frame_ancestors() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addFrameAncestors(
						host(null, "domain.com", null, null),
						host("http", "domain.com", "80", "/"),
						host("http", "domain.com", "*", "")
				)
				.build();

		assertThat(csp.serializeValue()).isEqualTo(
				"default-src 'none'; " +
						"frame-ancestors " +
						"domain.com " +
						"http://domain.com:80/ " +
						"http://domain.com:*;"
		);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FRAME_ANCESTORS, sources("domain.com", "http://domain.com:80/", "http://domain.com:*"))
			);
	}

	@Test
	public void it_should_handle_plugin_types() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addPluginTypes("application/json", "application/xml")
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; plugin-types application/json application/xml;");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(PLUGIN_TYPES, sources("application/json", "application/xml"))
			);
	}

	@Test
	public void it_should_handle_report_uri() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addReportUri("http://domain.com", "http://fake.com")
				.addReportUri(URI.create("http://google.com"))
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; report-uri http://domain.com http://fake.com http://google.com;");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(REPORT_URI, sources("http://domain.com", "http://fake.com", "http://google.com"))
			);
	}

	@Test
	public void it_should_handle_sandbox() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addSandbox(Sandbox.ALLOW_SCRIPTS, Sandbox.ALLOW_SAME_ORIGIN)
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; sandbox allow-scripts allow-same-origin;");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(SANDBOX, sources(Sandbox.ALLOW_SCRIPTS, Sandbox.ALLOW_SAME_ORIGIN))
			);
	}

	@Test
	public void it_should_handle_block_all_mixed_content() {
		ContentSecurityPolicy csp = ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.blockAllMixedContent()
				.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; block-all-mixed-content;");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(BLOCK_ALL_MIXED_CONTENT, Collections.<Source>emptySet())
			);
	}

	@Test
	public void it_should_handle_frame_ancestors_and_fail_if_source_is_not_host() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Source must be a valid host value");

		ContentSecurityPolicy.builder()
				.addDefaultSrc(none())
				.addFrameAncestors(self())
				.build();
	}

	private static Set<Source> sources(String... values) {
		Set<Source> sources = new LinkedHashSet<>();
		for (String value : values) {
			sources.add(new ContentSecurityPolicy.SourceValue(value));
		}

		return sources;
	}

	private static Set<Source> sources(Source... values) {
		Set<Source> sources = new LinkedHashSet<>();
		Collections.addAll(sources, values);
		return sources;
	}
}
