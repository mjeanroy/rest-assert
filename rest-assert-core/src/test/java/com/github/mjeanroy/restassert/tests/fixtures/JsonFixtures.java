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

package com.github.mjeanroy.restassert.tests.fixtures;

import static com.github.mjeanroy.restassert.test.commons.IoTestUtils.fileFromClasspath;
import static com.github.mjeanroy.restassert.test.commons.IoTestUtils.pathFromClasspath;
import static com.github.mjeanroy.restassert.test.commons.IoTestUtils.uriFromClasspath;
import static com.github.mjeanroy.restassert.test.commons.IoTestUtils.urlFromClasspath;
import static com.github.mjeanroy.restassert.test.json.JsonArray.jsonArray;
import static com.github.mjeanroy.restassert.test.json.JsonEntry.jsonEntry;
import static com.github.mjeanroy.restassert.test.json.JsonObject.jsonObject;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import com.github.mjeanroy.restassert.test.json.JsonObject;

public final class JsonFixtures {

	private JsonFixtures() {
	}

	private static final String JSON_SUCCESS = "/json/success.json";

	private static final String JSON_FAILURE = "/json/failure.json";

	public static String jsonSuccess() {
		JsonObject object = jsonObject(
				jsonEntry("str", "foo"),
				jsonEntry("nb", 1.0),
				jsonEntry("bool", true),
				jsonEntry("array", jsonArray(1.0, 2.0, 3.0))
		);

		return object.toJson();
	}

	public static String jsonFailure() {
		JsonObject object = jsonObject(
				jsonEntry("str", "bar"),
				jsonEntry("nb", 2.0),
				jsonEntry("bool", false),
				jsonEntry("array", jsonArray(1.1, 2.1, 3.1))
		);

		return object.toJson();
	}

	public static URL jsonUrlSuccess() {
		return urlFromClasspath(JSON_SUCCESS);
	}

	public static URL jsonUrlFailure() {
		return urlFromClasspath(JSON_FAILURE);
	}

	public static URI jsonUriSuccess() {
		return uriFromClasspath(JSON_SUCCESS);
	}

	public static URI jsonUriFailure() {
		return uriFromClasspath(JSON_FAILURE);
	}

	public static File jsonFileSuccess() {
		return fileFromClasspath(JSON_SUCCESS);
	}

	public static File jsonFileFailure() {
		return fileFromClasspath(JSON_FAILURE);
	}

	public static Path jsonPathSuccess() {
		return pathFromClasspath(JSON_SUCCESS);
	}

	public static Path jsonPathFailure() {
		return pathFromClasspath(JSON_FAILURE);
	}
}
