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

package com.github.mjeanroy.restassert.tests.builders.spring;

import com.github.mjeanroy.restassert.tests.builders.AbstractHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * DefaultCookieBuilder to create instance of {@link ResultActions} class.
 */
public class SpringMockMvcHttpResponseBuilder extends AbstractHttpResponseBuilder<ResultActions, SpringMockMvcHttpResponseBuilder> implements HttpResponseBuilder<ResultActions> {

	@Override
	public ResultActions build() {
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setStatus(status);

		try {
			response.getWriter().write(content);
		} catch (UnsupportedEncodingException ex) {
			throw new AssertionError(ex);
		}

		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			for (String headerValue : entry.getValue()) {
				response.addHeader(headerName, headerValue);
			}
		}

		MvcResult mvcResult = new MockMvcResult(response);
		return new MockResultActions(mvcResult);
	}

	private static final class MockResultActions implements ResultActions {

		private final MvcResult mvcResult;

		private MockResultActions(MvcResult mvcResult) {
			this.mvcResult = mvcResult;
		}

		@Override
		public ResultActions andExpect(ResultMatcher matcher) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ResultActions andDo(ResultHandler handler) {
			throw new UnsupportedOperationException();
		}

		@Override
		public MvcResult andReturn() {
			return mvcResult;
		}
	}

	private static final class MockMvcResult implements MvcResult {

		private final MockHttpServletResponse response;

		private MockMvcResult(MockHttpServletResponse response) {
			this.response = response;
		}

		@Override
		public MockHttpServletRequest getRequest() {
			throw new UnsupportedOperationException();
		}

		@Override
		public MockHttpServletResponse getResponse() {
			return response;
		}

		@Override
		public Object getHandler() {
			throw new UnsupportedOperationException();
		}

		@Override
		public HandlerInterceptor[] getInterceptors() {
			throw new UnsupportedOperationException();
		}

		@Override
		public ModelAndView getModelAndView() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Exception getResolvedException() {
			throw new UnsupportedOperationException();
		}

		@Override
		public FlashMap getFlashMap() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object getAsyncResult() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object getAsyncResult(long timeToWait) {
			throw new UnsupportedOperationException();
		}
	}
}
