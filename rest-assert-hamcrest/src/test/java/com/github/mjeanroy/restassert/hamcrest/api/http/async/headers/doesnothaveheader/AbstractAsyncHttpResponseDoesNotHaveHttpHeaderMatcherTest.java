/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.hamcrest.api.http.async.headers.doesnothaveheader;

import com.github.mjeanroy.junit4.runif.RunIf;
import com.github.mjeanroy.junit4.runif.RunIfRunner;
import com.github.mjeanroy.junit4.runif.conditions.AtLeastJava8Condition;
import com.github.mjeanroy.restassert.hamcrest.api.http.AbstractHttpResponseDoesNotHaveHttpHeaderMatcherTest;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.async.AsyncHttpResponseBuilder;
import org.asynchttpclient.Response;
import org.junit.runner.RunWith;

@RunWith(RunIfRunner.class)
@RunIf(AtLeastJava8Condition.class)
abstract class AbstractAsyncHttpResponseDoesNotHaveHttpHeaderMatcherTest extends AbstractHttpResponseDoesNotHaveHttpHeaderMatcherTest<Response> {

	@Override
	protected HttpResponseBuilder<Response> getBuilder() {
		return new AsyncHttpResponseBuilder();
	}
}
