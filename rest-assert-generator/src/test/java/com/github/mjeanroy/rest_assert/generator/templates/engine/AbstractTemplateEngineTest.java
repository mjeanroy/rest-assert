/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.generator.templates.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.mjeanroy.rest_assert.generator.Template;
import com.github.mjeanroy.rest_assert.generator.TemplateEngine;

public abstract class AbstractTemplateEngineTest {

	@Test
	public void it_should_render_template() {
		Template template = createTemplate("Hello {{name}}");
		Map<String, Object> placeholders = placeholders("name", "foo");
		String content = templateEngine().execute(template, placeholders);
		assertThat(content).isEqualTo("Hello foo");
	}

	@Test
	public void it_should_render_template_with_three_mustache() {
		Template template = createTemplate("Hello {{{name}}}");
		Map<String, Object> placeholders = placeholders("name", "foo");
		String content = templateEngine().execute(template, placeholders);
		assertThat(content).isEqualTo("Hello foo");
	}

	protected abstract TemplateEngine templateEngine();

	protected Template createTemplate(String text) {
		Template template = mock(Template.class);
		when(template.read()).thenReturn(text);
		return template;
	}

	protected Map<String, Object> placeholders(String key, String value) {
		Map<String, Object> placeholders = new HashMap<>();
		placeholders.put(key, value);
		return placeholders;
	}
}
