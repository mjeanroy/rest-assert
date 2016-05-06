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

package com.github.mjeanroy.rest_assert.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URI;
import java.net.URL;

import static com.github.mjeanroy.rest_assert.data.ContentSecurityPolicy.*;
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
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none';");
		assertThat(csp.value()).isEqualTo("default-src 'none';");
	}

	@Test
	public void it_should_handle_style_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addStyleSrc(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; style-src 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; style-src 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_script_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addScriptSrc(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; script-src 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; script-src 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_connect_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addConnectSrc(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; connect-src 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; connect-src 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_child_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addChildSrc(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; child-src 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; child-src 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_font_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addFontSrc(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; font-src 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; font-src 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_media_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addMediaSrc(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; media-src 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; media-src 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_form_actions() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addFormAction(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; form-action 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; form-action 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_img_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addImgSrc(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; img-src 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; img-src 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_object_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addObjectSrc(self(), unsafeInline())
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; object-src 'self' 'unsafe-inline';");
		assertThat(csp.value()).isEqualTo("default-src 'none'; object-src 'self' 'unsafe-inline';");
	}

	@Test
	public void it_should_handle_base_uri() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addBaseUri(self())
				.build();

		assertThat(csp.toString()).isEqualTo("base-uri 'self'; default-src 'none';");
		assertThat(csp.value()).isEqualTo("base-uri 'self'; default-src 'none';");
	}

	@Test
	public void it_should_handle_frame_ancestors() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addFrameAncestors(
						host(null, "domain.com", null, null),
						host("http", "domain.com", "80", "/"),
						host("http", "domain.com", "*", "")
				)
				.build();

		assertThat(csp.toString()).isEqualTo(
				"default-src 'none'; " +
						"frame-ancestors " +
							"domain.com " +
							"http://domain.com:80/ " +
							"http://domain.com:*;"
		);

		assertThat(csp.value()).isEqualTo(
				"default-src 'none'; " +
						"frame-ancestors " +
						"domain.com " +
						"http://domain.com:80/ " +
						"http://domain.com:*;"
		);
	}

	@Test
	public void it_should_handle_plugin_types() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addPluginTypes("application/json", "application/xml")
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; plugin-types application/json application/xml;");
		assertThat(csp.value()).isEqualTo("default-src 'none'; plugin-types application/json application/xml;");
	}

	@Test
	public void it_should_handle_report_uri() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addReportUri("http://domain.com", "http://fake.com")
				.addReportUri(URI.create("http://google.com"))
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; report-uri http://domain.com http://fake.com http://google.com;");
		assertThat(csp.value()).isEqualTo("default-src 'none'; report-uri http://domain.com http://fake.com http://google.com;");
	}

	@Test
	public void it_should_handle_sandbox() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addSandbox(Sandbox.ALLOW_SCRIPTS, Sandbox.ALLOW_SAME_ORIGIN)
				.build();

		assertThat(csp.toString()).isEqualTo("default-src 'none'; sandbox allow-scripts allow-same-origin;");
		assertThat(csp.value()).isEqualTo("default-src 'none'; sandbox allow-scripts allow-same-origin;");
	}

	@Test
	public void it_should_handle_frame_ancestors_and_fail_if_source_is_not_host() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Source must be a valid host value");

		new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addFrameAncestors(self())
				.build();
	}

	@Test
	public void it_should_check_if_csp_match_header_without_order_comparison() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(self(), unsafeEval(), unsafeInline())
				.build();

		assertThat(csp.match("default-src 'unsafe-inline' 'unsafe-eval' 'self';")).isTrue();
		assertThat(csp.match("default-src 'unsafe-inline' 'unsafe-eval';")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_default_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.build();

		assertThat(csp.match("default-src 'none';")).isTrue();
		assertThat(csp.match("default-src 'self';")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_sandbox() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addSandbox(Sandbox.ALLOW_FORMS, Sandbox.ALLOW_SCRIPTS)
				.build();

		assertThat(csp.match("default-src 'none'; sandbox allow-scripts allow-forms")).isTrue();
		assertThat(csp.match("default-src 'self'; sandbox allow-scripts")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_script_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addScriptSrc(unsafeEval(), unsafeInline())
				.build();

		assertThat(csp.match("default-src 'none'; script-src 'unsafe-eval' 'unsafe-inline'")).isTrue();
		assertThat(csp.match("default-src 'none'; script-src 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_styles_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addStyleSrc(unsafeInline(), nonce("12345=="))
				.build();

		assertThat(csp.match("default-src 'none'; style-src 'unsafe-inline' 'nonce-12345=='")).isTrue();
		assertThat(csp.match("default-src 'none'; style-src 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_connect_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addConnectSrc(unsafeInline(), host(null, "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; connect-src domain.com 'unsafe-inline'")).isTrue();
		assertThat(csp.match("default-src 'none'; connect-src 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_font_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addFontSrc(unsafeInline(), host("http", "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; font-src http://domain.com 'unsafe-inline'")).isTrue();
		assertThat(csp.match("default-src 'none'; font-src https://domain.com 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_img_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addImgSrc(unsafeInline(), host("http", "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; img-src http://domain.com 'unsafe-inline'")).isTrue();
		assertThat(csp.match("default-src 'none'; img-src https://domain.com 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_media_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addMediaSrc(unsafeInline(), host("http", "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; media-src http://domain.com 'unsafe-inline'")).isTrue();
		assertThat(csp.match("default-src 'none'; media-src https://domain.com 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_object_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addObjectSrc(unsafeInline(), host("http", "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; object-src http://domain.com 'unsafe-inline'")).isTrue();
		assertThat(csp.match("default-src 'none'; object-src https://domain.com 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_child_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addChildSrc(unsafeInline(), host("http", "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; child-src http://domain.com 'unsafe-inline'")).isTrue();
		assertThat(csp.match("default-src 'none'; child-src https://domain.com 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_form_action_src() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addFormAction(unsafeInline(), host("http", "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; form-action http://domain.com 'unsafe-inline'")).isTrue();
		assertThat(csp.match("default-src 'none'; form-action https://domain.com 'unsafe-inline'")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_plugin_types() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addPluginTypes("application/json", "application/xml")
				.build();

		assertThat(csp.match("default-src 'none'; plugin-types application/xml application/json")).isTrue();
		assertThat(csp.match("default-src 'none'; plugin-types application/xml")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_frame_ancestors() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addFrameAncestors(host("http", "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; frame-ancestors http://domain.com")).isTrue();
		assertThat(csp.match("default-src 'none'; frame-ancestors https://domain.com")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_report_uri() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addReportUri("http://domain.com")
				.build();

		assertThat(csp.match("default-src 'none'; report-uri http://domain.com")).isTrue();
		assertThat(csp.match("default-src 'none'; report-uri https://domain.com")).isFalse();
	}

	@Test
	public void it_should_check_if_csp_match_header_with_base_uri() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addBaseUri(host("http", "domain.com", null, null))
				.build();

		assertThat(csp.match("default-src 'none'; base-uri http://domain.com")).isTrue();
		assertThat(csp.match("default-src 'none'; base-uri https://domain.com")).isFalse();
	}

	@Test
	public void it_should_fail_if_directive_name_is_not_found() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addBaseUri(host("http", "domain.com", null, null))
				.build();

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cannot parse Content-Security-Policy header since directive foo seems not valid");

		csp.match("default-src 'none'; foo http://domain.com");
	}

	@Test
	public void it_should_fail_if_directive_does_not_have_name() {
		ContentSecurityPolicy csp = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.addBaseUri(host("http", "domain.com", null, null))
				.build();

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Header default-src 'none'; ; is not a valid Content-Security-Policy header");

		csp.match("default-src 'none'; ;");
	}

	@Test
	public void it_should_implement_equals() {
		ContentSecurityPolicy csp1 = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.build();

		ContentSecurityPolicy csp2 = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.build();

		ContentSecurityPolicy csp3 = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.build();

		ContentSecurityPolicy csp4 = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(self())
				.build();

		assertThat(csp1.equals(csp2)).isTrue();
		assertThat(csp1.equals(csp4)).isFalse();
		assertThat(csp1.equals(null)).isFalse();

		// Reflective
		assertThat(csp1.equals(csp1)).isTrue();

		// Symmetric
		assertThat(csp1.equals(csp2)).isTrue();
		assertThat(csp2.equals(csp1)).isTrue();

		// Transitive
		assertThat(csp1.equals(csp2)).isTrue();
		assertThat(csp2.equals(csp3)).isTrue();
		assertThat(csp1.equals(csp3)).isTrue();
	}

	@Test
	public void it_should_implement_hash_code() {
		ContentSecurityPolicy csp1 = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.build();

		ContentSecurityPolicy csp2 = new ContentSecurityPolicy.Builder()
				.addDefaultSrc(none())
				.build();

		assertThat(csp1.hashCode()).isEqualTo(csp2.hashCode());
	}
}
