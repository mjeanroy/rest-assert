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
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.Source;
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
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.allHosts;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.data;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.host;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.http;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.https;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.nonce;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.none;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.scheme;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.self;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.sha256;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.sha384;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.sha512;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.unsafeEval;
import static com.github.mjeanroy.restassert.core.data.ContentSecurityPolicy.unsafeInline;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ContentSecurityPolicyTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_define_self_source() {
		Source src = self();
		assertThat(src).isNotNull();
		assertThat(src.getValue()).isEqualTo("'self'");
	}

	@Test
	public void it_should_define_none_source() {
		Source src = none();
		assertThat(src).isNotNull();
		assertThat(src.getValue()).isEqualTo("'none'");
	}

	@Test
	public void it_should_define_unsafe_eval_source() {
		Source src = unsafeEval();
		assertThat(src).isNotNull();
		assertThat(src.getValue()).isEqualTo("'unsafe-eval'");
	}

	@Test
	public void it_should_define_unsafe_inline_source() {
		Source src = unsafeInline();
		assertThat(src).isNotNull();
		assertThat(src.getValue()).isEqualTo("'unsafe-inline'");
	}

	@Test
	public void it_should_define_http_source() {
		Source src = http();
		assertThat(src).isNotNull();
		assertThat(src.getValue()).isEqualTo("http:");
	}

	@Test
	public void it_should_define_https_source() {
		Source src = https();
		assertThat(src).isNotNull();
		assertThat(src.getValue()).isEqualTo("https:");
	}

	@Test
	public void it_should_define_data_source() {
		Source src = data();
		assertThat(src).isNotNull();
		assertThat(src.getValue()).isEqualTo("data:");
	}

	@Test
	public void it_should_define_scheme_source() {
		Source src = scheme("npm");
		assertThat(src).isNotNull();
		assertThat(src.getValue()).isEqualTo("npm:");
	}

	@Test
	public void it_should_define_nonce_source() {
		assertThat(nonce("abcd==").getValue()).isEqualTo("'nonce-abcd=='");
		assertThat(nonce("abcd123==").getValue()).isEqualTo("'nonce-abcd123=='");
		assertThat(nonce("abcd123+==").getValue()).isEqualTo("'nonce-abcd123+=='");
		assertThat(nonce("abcd123/==").getValue()).isEqualTo("'nonce-abcd123/=='");
	}

	@Test
	public void it_should_define_algo_source() {
		assertThat(sha256("abcd==").getValue()).isEqualTo("'sha256-abcd=='");
		assertThat(sha384("abcd==").getValue()).isEqualTo("'sha384-abcd=='");
		assertThat(sha512("abcd==").getValue()).isEqualTo("'sha512-abcd=='");

		assertThat(sha256("abcd123==").getValue()).isEqualTo("'sha256-abcd123=='");
		assertThat(sha256("abcd123+==").getValue()).isEqualTo("'sha256-abcd123+=='");
		assertThat(sha256("abcd123/==").getValue()).isEqualTo("'sha256-abcd123/=='");
	}

	@Test
	public void it_should_define_all_host_source() {
		assertThat(allHosts().getValue()).isEqualTo("*");
	}

	@Test
	public void it_should_define_host_source() {
		assertThat(host(null, "domain.com", null, null).getValue()).isEqualTo("domain.com");
		assertThat(host("http", "domain.com", null, null).getValue()).isEqualTo("http://domain.com");
		assertThat(host("http", "domain.com", "80", null).getValue()).isEqualTo("http://domain.com:80");
		assertThat(host("http", "domain.com", "80", "/").getValue()).isEqualTo("http://domain.com:80/");
		assertThat(host(null, "domain.com", "80", "/").getValue()).isEqualTo("domain.com:80/");
		assertThat(host(null, "domain.com", null, "/").getValue()).isEqualTo("domain.com/");
		assertThat(host(null, "domain.com", "*", null).getValue()).isEqualTo("domain.com:*");
		assertThat(host(null, "*.gravatar.com", null, null).getValue()).isEqualTo("*.gravatar.com");
	}

	@Test
	public void it_should_define_host_source_with_URL() throws Exception {
		assertThat(host(new URL("http://domain.com:80/")).getValue()).isEqualTo("http://domain.com:80/");
		assertThat(host(new URL("http://domain.com/")).getValue()).isEqualTo("http://domain.com/");
	}

	@Test
	public void it_should_handle_default_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			DEFAULT_SRC, sources("'none'")
		));

		assertThat(csp.serializeValue()).isEqualTo("default-src 'none';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"DEFAULT_SRC=['none']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_style_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			STYLE_SRC, sources("'none'")
		));

		assertThat(csp.serializeValue()).isEqualTo("style-src 'none';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"STYLE_SRC=['none']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_script_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			SCRIPT_SRC, sources("'none'")
		));

		assertThat(csp.serializeValue()).isEqualTo("script-src 'none';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"SCRIPT_SRC=['none']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_connect_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			CONNECT_SRC, sources("'none'")
		));

		assertThat(csp.serializeValue()).isEqualTo("connect-src 'none';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"CONNECT_SRC=['none']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_child_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			CHILD_SRC, sources("'self'", "'unsafe-inline'")
		));

		assertThat(csp.serializeValue()).isEqualTo("child-src 'self' 'unsafe-inline';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"CHILD_SRC=['self', 'unsafe-inline']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_font_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			FONT_SRC, sources("'self'", "'unsafe-inline'")
		));

		assertThat(csp.serializeValue()).isEqualTo("font-src 'self' 'unsafe-inline';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"FONT_SRC=['self', 'unsafe-inline']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_media_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			MEDIA_SRC, sources("'self'", "'unsafe-inline'")
		));

		assertThat(csp.serializeValue()).isEqualTo("media-src 'self' 'unsafe-inline';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"MEDIA_SRC=['self', 'unsafe-inline']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_form_actions() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			FORM_ACTION, sources("'self'", "'unsafe-inline'")
		));

		assertThat(csp.serializeValue()).isEqualTo("form-action 'self' 'unsafe-inline';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"FORM_ACTION=['self', 'unsafe-inline']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_img_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			IMG_SRC, sources("'self'", "'unsafe-inline'")
		));

		assertThat(csp.serializeValue()).isEqualTo("img-src 'self' 'unsafe-inline';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"IMG_SRC=['self', 'unsafe-inline']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_object_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			OBJECT_SRC, sources("'self'", "'unsafe-inline'")
		));

		assertThat(csp.serializeValue()).isEqualTo("object-src 'self' 'unsafe-inline';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"OBJECT_SRC=['self', 'unsafe-inline']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_base_uri() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			BASE_URI, sources("'self'")
		));

		assertThat(csp.serializeValue()).isEqualTo("base-uri 'self';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"BASE_URI=['self']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_frame_ancestors() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			FRAME_ANCESTORS, sources("'self'", "'unsafe-inline'")
		));

		assertThat(csp.serializeValue()).isEqualTo("frame-ancestors 'self' 'unsafe-inline';");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"FRAME_ANCESTORS=['self', 'unsafe-inline']" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_plugin_types() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			PLUGIN_TYPES, sources("application/json", "application/xml")
		));

		assertThat(csp.serializeValue()).isEqualTo("plugin-types application/json application/xml;");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"PLUGIN_TYPES=[application/json, application/xml]" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_report_uri() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			REPORT_URI, sources("http://domain.com", "http://fake.com", "http://google.com")
		));

		assertThat(csp.serializeValue()).isEqualTo("report-uri http://domain.com http://fake.com http://google.com;");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"REPORT_URI=[http://domain.com, http://fake.com, http://google.com]" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_sandbox() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			SANDBOX, sources(Sandbox.ALLOW_SCRIPTS, Sandbox.ALLOW_SAME_ORIGIN)
		));

		assertThat(csp.serializeValue()).isEqualTo("sandbox allow-scripts allow-same-origin;");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"SANDBOX=[ALLOW_SCRIPTS, ALLOW_SAME_ORIGIN]" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_handle_block_all_mixed_content() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy(singletonMap(
			BLOCK_ALL_MIXED_CONTENT, Collections.<Source>emptySet()
		));

		assertThat(csp.serializeValue()).isEqualTo("block-all-mixed-content;");
		assertThat(csp.toString()).isEqualTo(
			"ContentSecurityPolicy{" +
				"directives={" +
					"BLOCK_ALL_MIXED_CONTENT=[]" +
				"}" +
			"}"
		);
	}

	@Test
	public void it_should_implement_equals() {
		EqualsVerifier.forClass(ContentSecurityPolicy.class).verify();
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
