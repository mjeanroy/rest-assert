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
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Source;
import com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceValue;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Sandbox;
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
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.SCRIPT_SRC_ATTR;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.SCRIPT_SRC_ELEM;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.STYLE_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.STYLE_SRC_ATTR;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.STYLE_SRC_ELEM;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.UPGRADE_INSECURE_REQUEST;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.SourceDirective.WORKER_SRC;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.host;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.none;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.self;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.unsafeInline;
import static java.util.Collections.addAll;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

public class ContentSecurityPolicyBuilderTest {

	private ContentSecurityPolicyBuilder builder;

	@Before
	public void setUp() {
		builder = ContentSecurityPolicy.builder();
	}

	@Test
	public void it_should_handle_default_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'");
		assertThat(csp.getDirectives())
			.hasSize(1)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'"))
			);
	}

	@Test
	public void it_should_handle_style_src() {
		ContentSecurityPolicy csp = builder.addDefaultSrc(none()).addStyleSrc(self(), unsafeInline()).build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; style-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives()).containsExactly(
			entry(DEFAULT_SRC, sources("'none'")),
			entry(STYLE_SRC, sources("'self'", "'unsafe-inline'"))
		);
	}

	@Test
	public void it_should_handle_style_src_elem() {
		ContentSecurityPolicy csp = builder.addDefaultSrc(none()).addStyleSrcElem(self(), unsafeInline()).build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; style-src-elem 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives()).containsExactly(
			entry(DEFAULT_SRC, sources("'none'")),
			entry(STYLE_SRC_ELEM, sources("'self'", "'unsafe-inline'"))
		);
	}

	@Test
	public void it_should_handle_style_src_attr() {
		ContentSecurityPolicy csp = builder.addDefaultSrc(none()).addStyleSrcAttr(self(), unsafeInline()).build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; style-src-attr 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives()).containsExactly(
			entry(DEFAULT_SRC, sources("'none'")),
			entry(STYLE_SRC_ATTR, sources("'self'", "'unsafe-inline'"))
		);
	}

	@Test
	public void it_should_handle_script_src() {
		ContentSecurityPolicy csp = builder.addDefaultSrc(none()).addScriptSrc(self(), unsafeInline()).build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; script-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives()).containsExactly(
			entry(DEFAULT_SRC, sources("'none'")),
			entry(SCRIPT_SRC, sources("'self'", "'unsafe-inline'"))
		);
	}

	@Test
	public void it_should_handle_script_src_elem() {
		ContentSecurityPolicy csp = builder.addDefaultSrc(none()).addScriptSrcElem(self(), unsafeInline()).build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; script-src-elem 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives()).containsExactly(
			entry(DEFAULT_SRC, sources("'none'")),
			entry(SCRIPT_SRC_ELEM, sources("'self'", "'unsafe-inline'"))
		);
	}

	@Test
	public void it_should_handle_script_src_attr() {
		ContentSecurityPolicy csp = builder.addDefaultSrc(none()).addScriptSrcAttr(self(), unsafeInline()).build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; script-src-attr 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives()).containsExactly(
			entry(DEFAULT_SRC, sources("'none'")),
			entry(SCRIPT_SRC_ATTR, sources("'self'", "'unsafe-inline'"))
		);
	}

	@Test
	public void it_should_handle_connect_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addConnectSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; connect-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(CONNECT_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_child_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addChildSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; child-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(CHILD_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_font_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addFontSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; font-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FONT_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_media_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addMediaSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; media-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(MEDIA_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_form_actions() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addFormAction(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; form-action 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FORM_ACTION, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_img_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addImgSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; img-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(IMG_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_object_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addObjectSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; object-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(OBJECT_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_base_uri() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addBaseUri(self())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; base-uri 'self'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(BASE_URI, sources("'self'"))
			);
	}

	@Test
	public void it_should_handle_frame_ancestors() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addFrameAncestors(
				host(null, "domain.com", null, null),
				host("http", "domain.com", "80", "/"),
				host("http", "domain.com", "*", ""))
			.build();

		assertThat(csp.serializeValue()).isEqualTo(
			"default-src 'none'; " +
				"frame-ancestors " +
				"domain.com " +
				"http://domain.com:80/ " +
				"http://domain.com:*"
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
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addPluginTypes("application/json", "application/xml")
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; plugin-types application/json application/xml");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(PLUGIN_TYPES, sources("application/json", "application/xml"))
			);
	}

	@Test
	public void it_should_handle_report_uri() {
		final String uri1 = "http://domain.com";
		final String uri2 = "http://fake.com";
		final String uri3 = "http://google.com";
		final String value = "" + uri1 + " " + uri2 + " " + uri3 + "";
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addReportUri(uri1, uri2)
			.addReportUri(URI.create(uri3))
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; report-uri " + value);
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(REPORT_URI, sources(uri1, uri2, uri3))
			);
	}

	@Test
	public void it_should_handle_sandbox() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addSandbox(Sandbox.ALLOW_SCRIPTS, Sandbox.ALLOW_SAME_ORIGIN)
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; sandbox allow-scripts allow-same-origin");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(SANDBOX, sources(Sandbox.ALLOW_SCRIPTS, Sandbox.ALLOW_SAME_ORIGIN))
			);
	}

	@Test
	public void it_should_handle_block_all_mixed_content() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.blockAllMixedContent()
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; block-all-mixed-content");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(BLOCK_ALL_MIXED_CONTENT, sources())
			);
	}

	@Test
	public void it_should_handle_frame_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addFrameSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; frame-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(FRAME_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_prefetch_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addPrefetchSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; prefetch-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(PREFETCH_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_manifest_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addManifestSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; manifest-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(MANIFEST_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_worker_src() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addWorkerSrc(self(), unsafeInline())
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; worker-src 'self' 'unsafe-inline'");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(WORKER_SRC, sources("'self'", "'unsafe-inline'"))
			);
	}

	@Test
	public void it_should_handle_disown_opener() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addDisownOpener()
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; disown-opener");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(DISOWN_OPENER, sources())
			);
	}

	@Test
	public void it_should_handle_navigate_to() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addNavigateTo(
				self(),
				host(null, "domain.com", null, null),
				host("http", "domain.com", "80", "/"),
				host("http", "domain.com", "*", ""))
			.build();

		assertThat(csp.serializeValue()).isEqualTo(
			"default-src 'none'; " +
				"navigate-to " +
				"'self' " +
				"domain.com " +
				"http://domain.com:80/ " +
				"http://domain.com:*"
		);

		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(NAVIGATE_TO, sources("'self'", "domain.com", "http://domain.com:80/", "http://domain.com:*"))
			);
	}

	@Test
	public void it_should_handle_require_sri_for() {
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.addRequireSriFor(RequireSriFor.SCRIPT)
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; require-sri-for script");
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(REQUIRE_SRI_FOR, sources(RequireSriFor.SCRIPT))
			);
	}

	@Test
	public void it_should_handle_upgrade_insecure_request() {
		ContentSecurityPolicy csp = builder.addDefaultSrc(none()).upgradeInsecureRequest().build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; upgrade-insecure-request");
		assertThat(csp.getDirectives()).containsExactly(
			entry(DEFAULT_SRC, sources("'none'")),
			entry(UPGRADE_INSECURE_REQUEST, sources())
		);
	}

	@Test
	public void it_should_handle_report_to() {
		final String value = "#group1";
		final ContentSecurityPolicy csp = builder
			.addDefaultSrc(none())
			.setReportTo(value)
			.build();

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none'; report-to " + value);
		assertThat(csp.getDirectives())
			.hasSize(2)
			.containsOnly(
				entry(DEFAULT_SRC, sources("'none'")),
				entry(REPORT_TO, sources(value))
			);
	}

	@Test
	public void it_should_handle_frame_ancestors_and_fail_if_source_is_not_host() {
		assertThatThrownBy(addFrameAncestors(builder, self()))
				.isExactlyInstanceOf(IllegalArgumentException.class)
				.hasMessage("Source must be a valid host value");
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

	private static ThrowingCallable addFrameAncestors(final ContentSecurityPolicyBuilder builder, final ContentSecurityPolicy.Source source) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				builder.addFrameAncestors(source);
			}
		};
	}
}
