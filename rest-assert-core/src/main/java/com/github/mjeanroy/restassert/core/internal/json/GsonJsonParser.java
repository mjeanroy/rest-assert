/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2026 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.json;

import com.google.gson.Gson;

/// Implementation of [JsonParser] using Google Gson as internal implementation.
///
/// This class is implemented as a singleton.
/// This class is thread safe.
final class GsonJsonParser extends AbstractJsonParser {

	/// Get parser.
	///
	/// @return Parser.
	static GsonJsonParser getInstance() {
		return Holder.INSTANCE;
	}

	/// Internal parser.
	private final Gson gson;

	private GsonJsonParser(Gson gson) {
		super();
		this.gson = gson;
	}

	@Override
	<T> T doParse(String json, Class<T> klass) {
		return gson.fromJson(json, klass);
	}

	private static final class Holder {
		private static final GsonJsonParser INSTANCE = new GsonJsonParser(
			new Gson()
		);
	}
}
