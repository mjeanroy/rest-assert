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

package com.github.mjeanroy.rest_assert.internal.json;

import java.util.Map;

/**
 * Set of json types.
 */
public enum JsonType {

	BOOLEAN {
		@Override
		protected boolean isValid(Object object) {
			return object instanceof Boolean;
		}
	},

	NUMBER {
		@Override
		protected boolean isValid(Object object) {
			return object instanceof Number;
		}
	},

	STRING {
		@Override
		protected boolean isValid(Object object) {
			return object instanceof String;
		}
	},

	OBJECT {
		@Override
		protected boolean isValid(Object object) {
			return object instanceof Map;
		}
	},

	ARRAY {
		@Override
		protected boolean isValid(Object object) {
			return object instanceof Iterable ||
					object instanceof Object[];
		}
	},

	NULL {
		@Override
		protected boolean isValid(Object object) {
			return object == null;
		}
	};

	/**
	 * Check if object is of expected json type.
	 *
	 * @param object Object.
	 * @return True if object is valid for type, false otherwise.
	 */
	protected abstract boolean isValid(Object object);

	/**
	 * Get name of json type, that can be used in error
	 * message.
	 *
	 * @return Type.
	 */
	public String getFormattedName() {
		return name().toLowerCase();
	}

	/**
	 * Find json type of object.
	 *
	 * @param object Object.
	 * @return Json Type.
	 */
	public static JsonType parseType(Object object) {
		for (JsonType jsonType : JsonType.values()) {
			if (jsonType.isValid(object)) {
				return jsonType;
			}
		}

		// It should not happen...
		throw new UnsupportedOperationException("Json type of object " + object + " cannot be found");
	}
}
