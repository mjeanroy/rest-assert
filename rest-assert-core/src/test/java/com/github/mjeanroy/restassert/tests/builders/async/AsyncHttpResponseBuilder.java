/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.tests.builders.async;

import com.github.mjeanroy.restassert.tests.builders.AbstractHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Response;
import org.asynchttpclient.netty.EagerResponseBodyPart;
import org.asynchttpclient.netty.NettyResponse;
import org.asynchttpclient.netty.NettyResponseStatus;
import org.asynchttpclient.uri.Uri;

import java.util.List;
import java.util.Map;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * DefaultCookieBuilder to create mock instance of {@link org.asynchttpclient.Response} class.
 */
public class AsyncHttpResponseBuilder extends AbstractHttpResponseBuilder<Response, AsyncHttpResponseBuilder> implements HttpResponseBuilder<Response> {

	@Override
	public Response build() {
		Uri uri = Uri.create("http://localhost:8080");

		io.netty.handler.codec.http.HttpResponseStatus rspStatus = io.netty.handler.codec.http.HttpResponseStatus.valueOf(status);
		HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, rspStatus);
		HttpResponseStatus status = new NettyResponseStatus(uri, httpResponse, null);

		HttpHeaders headers = new DefaultHttpHeaders();
		for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
		}

		final List<HttpResponseBodyPart> bodyParts;
		if (content != null) {
			ByteBuf byteBuf = Unpooled.copiedBuffer(content, defaultCharset());
			HttpResponseBodyPart part = new EagerResponseBodyPart(byteBuf, true);
			bodyParts = singletonList(part);
		}
		else {
			bodyParts = emptyList();
		}

		return new NettyResponse(status, headers, bodyParts);
	}
}
