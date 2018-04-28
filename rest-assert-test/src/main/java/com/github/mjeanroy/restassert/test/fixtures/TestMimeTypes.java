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

package com.github.mjeanroy.restassert.test.fixtures;

import static java.util.Arrays.asList;

import java.util.List;

/**
 * Static pre-configured mime-type values to use in unit test.
 */
public final class TestMimeTypes {

	// Ensure non instantiation.
	private TestMimeTypes() {
	}

	/**
	 * The {@code "text/css"} mime type, to use in unit test.
	 */
	public static final String TEXT_CSS = "text/css";

	/**
	 * The {@code "text/csv"} mime type, to use in unit test.
	 */
	public static final String TEXT_CSV = "text/csv";

	/**
	 * The {@code "text/html"} mime type, to use in unit test.
	 */
	private static final String TEXT_HTML = "text/html";

	/**
	 * The {@code "application/xhtml+xml"} mime type, to use in unit test.
	 */
	private static final String APPLICATION_XHTML = "application/xhtml+xml";

	/**
	 * The list of available HTML mime types, to use in unit test.
	 */
	public static final List<String> HTML = asList(TEXT_HTML, APPLICATION_XHTML);

	/**
	 * The {@code "application/javascript"} mime type, to use in unit test.
	 */
	private static final String APPLICATION_JAVASCRIPT = "application/javascript";

	/**
	 * The {@code "text/javascript"} mime type, to use in unit test.
	 */
	private static final String TEXT_JAVASCRIPT = "text/javascript";

	/**
	 * The list of available JavaScript mime types, to use in unit test.
	 */
	public static final List<String> JAVASCRIPT = asList(APPLICATION_JAVASCRIPT, TEXT_JAVASCRIPT);

	/**
	 * The {@code "text/css"} mime type, to use in unit test.
	 */
	public static final String APPLICATION_JSON = "application/json";

	/**
	 * The {@code "application/pdf"} mime type, to use in unit test.
	 */
	public static final String APPLICATION_PDF = "application/pdf";

	/**
	 * The {@code "text/plain"} mime type, to use in unit test.
	 */
	public static final String TEXT_PLAIN = "text/plain";

	/**
	 * The {@code "application/xml"} mime type, to use in unit test.
	 */
	private static final String APPLICATION_XML = "application/xml";

	/**
	 * The {@code "text/xml"} mime type, to use in unit test.
	 */
	private static final String TEXT_XML = "text/xml";

	/**
	 * The list of available XML mime types, to use in unit test.
	 */
	public static final List<String> XML = asList(APPLICATION_XML, TEXT_XML);
}
