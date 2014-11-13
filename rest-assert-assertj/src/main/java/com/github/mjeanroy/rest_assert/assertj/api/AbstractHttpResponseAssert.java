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
 * furnished to do so, subject to the following httpResponses:
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

package com.github.mjeanroy.rest_assert.assertj.api;

import org.assertj.core.api.AbstractAssert;

import com.github.mjeanroy.rest_assert.assertj.internal.HttpResponses;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;

/**
 * Base class for all implementations of assertions for {@link HttpResponse}.
 *
 * @param <S> the "self" type of this assertion class. Please read &quot;<a href="http://bit.ly/anMa4g"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>&quot;
 *          for more details.
 */
public abstract class AbstractHttpResponseAssert<S extends AbstractHttpResponseAssert<S>> extends AbstractAssert<S, HttpResponse> {

	private final HttpResponses httpResponses = HttpResponses.instance();

	protected AbstractHttpResponseAssert(HttpResponse actual, Class<?> selfType) {
		super(actual, selfType);
	}

	public S isOk() {
		httpResponses.assertIsOk(info, actual);
		return myself;
	}

	public S isCreated() {
		httpResponses.assertIsCreated(info, actual);
		return myself;
	}

	public S isAccepted() {
		httpResponses.assertIsAccepted(info, actual);
		return myself;
	}

	public S isBadRequest() {
		httpResponses.assertIsBadRequest(info, actual);
		return myself;
	}

	public S isNotFound() {
		httpResponses.assertIsNotFound(info, actual);
		return myself;
	}

	public S isInternalServerError() {
		httpResponses.assertIsInternalServerError(info, actual);
		return myself;
	}
}
