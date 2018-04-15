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

/**
 * List of http verbs.
 */
public enum  RequestMethod {

	/**
	 * GET method.
	 * @see <a href="https://tools.ietf.org/html/rfc2068#page-50">https://tools.ietf.org/html/rfc2068#page-50</a>
	 */
	GET,

	/**
	 * POST method.
	 * @see <a href="https://tools.ietf.org/html/rfc2068#section-9.5">https://tools.ietf.org/html/rfc2068#section-9.5</a>
	 */
	POST,

	/**
	 * PUT method.
	 * @see <a href="https://tools.ietf.org/html/rfc2068#section-9.6">https://tools.ietf.org/html/rfc2068#section-9.6</a>
	 */
	PUT,

	/**
	 * DELETE method.
	 * @see <a href="https://tools.ietf.org/html/rfc2068#section-9.7">https://tools.ietf.org/html/rfc2068#section-9.7</a>
	 */
	DELETE,

	/**
	 * PATCH method.
	 * @see <a href="https://tools.ietf.org/html/rfc2068#section-19.6.1.1">https://tools.ietf.org/html/rfc2068#section-19.6.1.1</a>
	 */
	PATCH,

	/**
	 * HEAD method.
	 * @see <a href="https://tools.ietf.org/html/rfc2068#section-9.4">https://tools.ietf.org/html/rfc2068#section-9.4</a>
	 */
	HEAD,

	/**
	 * OPTIONS method.
	 * @see <a href="https://tools.ietf.org/html/rfc2068#section-9.2">https://tools.ietf.org/html/rfc2068#section-9.2</a>
	 */
	OPTIONS,

	/**
	 * TRACE method.
	 * @see <a href="https://tools.ietf.org/html/rfc2068#section-9.8">https://tools.ietf.org/html/rfc2068#section-9.8</a>
	 */
	TRACE;

	/**
	 * Get verb value as it should appear during an HTTP request.
	 *
	 * @return Verb.
	 */
	public String verb() {
		return name();
	}
}
