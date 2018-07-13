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

import static com.github.mjeanroy.restassert.core.internal.data.HttpHeaders.CONTENT_ENCODING;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.github.mjeanroy.restassert.core.data.ContentEncoding.Directive;
import com.github.mjeanroy.restassert.core.internal.data.AbstractHttpHeaderParser;
import com.github.mjeanroy.restassert.core.internal.exceptions.InvalidHeaderValue;

/**
 * Parser for {@link ContentEncoding} header.
 */
public class ContentEncodingParser extends AbstractHttpHeaderParser<ContentEncoding> {

	// Ensure no public instantiation.
	ContentEncodingParser() {
	}

	@Override
	protected ContentEncoding doParse(String value) {
		String[] values = value.split(",");
		List<Directive> directives = new ArrayList<>(value.length());
		for (String encoding : values) {
			final String directiveValue = encoding.trim().toLowerCase();
			final Directive directive = Directive.byValue(directiveValue);
			if (directive == null) {
				throw new InvalidHeaderValue(CONTENT_ENCODING.getName(), encoding);
			}

			directives.add(directive);
		}

		return new ContentEncoding(unmodifiableList(directives));
	}
}
