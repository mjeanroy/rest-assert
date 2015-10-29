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

package com.github.mjeanroy.rest_assert.internal.data.defaults;

import com.github.mjeanroy.rest_assert.internal.data.HttpStatus;

/**
 * List of standard http status.
 */
public enum StandardHttpStatus implements HttpStatus {

	// 2XX
	OK(200),
	CREATED(201),
	ACCEPTED(202),
	NO_CONTENT(204),
	RESET_CONTENT(205),
	PARTIAL_CONTENT(206),

	// 3XX
	MOVED_PERMANENTLY(301),
	MOVED_TEMPORARILY(302),
	NOT_MODIFIED(304),

	// 4XX
	BAD_REQUEST(400),
	UNAUTHORIZED(401),
	FORBIDDEN(403),
	NOT_FOUND(404),
	METHOD_NOT_ALLOWED(405),
	NOT_ACCEPTABLE(406),
	CONFLICT(409),
	PRE_CONDITION_FAILED(412),
	UNSUPPORTED_MEDIA_TYPE(415),

	// 5XX
	INTERNAL_SERVER_ERROR(500),
	NOT_IMPLEMENTED(501);

	/**
	 * Http status value.
	 * This code is a valid http status.
	 */
	private final int status;

	// Private constructor.
	private StandardHttpStatus(int status) {
		this.status = status;
	}

	@Override
	public int getStatus() {
		return status;
	}
}
