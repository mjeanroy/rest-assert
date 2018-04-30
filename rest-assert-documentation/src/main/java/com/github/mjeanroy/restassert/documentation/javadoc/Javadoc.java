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

package com.github.mjeanroy.restassert.documentation.javadoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A limited and simple structure for Javadoc comment.
 */
public final class Javadoc {

	/**
	 * Javadoc Description.
	 */
	private final String description;

	/**
	 * Javadoc Parameters, may be empty.
	 */
	private final List<JavadocParam> params;

	/**
	 * Javadoc returns tag, may be null.
	 */
	private final JavadocReturn returns;

	/**
	 * Create Javadoc instance.
	 *
	 * @param description Javadoc Description.
	 * @param params Javadoc Parameters, may be empty.
	 * @param returns Javadoc returns tag, may be null.
	 */
	Javadoc(String description, List<JavadocParam> params, JavadocReturn returns) {
		this.description = description;
		this.params = params;
		this.returns = returns;
	}

	/**
	 * Get {@link #description}
	 *
	 * @return {@link #description}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get {@link #params}
	 *
	 * @return {@link #params}
	 */
	public List<JavadocParam> getParams() {
		return params;
	}

	/**
	 * Get {@link #returns}
	 *
	 * @return {@link #returns}
	 */
	public JavadocReturn getReturns() {
		 return returns;
	}

	@Override
	public String toString() {
		return String.format("Javadoc{description=%s, params=%s, returns=%s}", description, params, returns);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Javadoc) {
			Javadoc doc = (Javadoc) o;
			return Objects.equals(description, doc.description) && Objects.equals(params, doc.params) && Objects.equals(returns, doc.returns);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, params, returns);
	}

	/**
	 * Builder for {@link Javadoc}.
	 */
	public static class Builder {

		/**
		 * The Javadoc Description.
		 * @see Javadoc#description
		 */
		private String description;

		/**
		 * The Javadoc Parameters.
		 * @see Javadoc#params
		 */
		private final List<JavadocParam> params;

		/**
		 * The Javadoc Returns.
		 * @see Javadoc#returns
		 */
		private JavadocReturn returns;

		/**
		 * Create the builder.
		 */
		public Builder() {
			this.params = new ArrayList<>();
		}

		/**
		 * Update {@link #description}
		 *
		 * @param description New {@link #description}
		 * @return The builder.
		 */
		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		/**
		 * Add parameter to {@link #params}
		 *
		 * @param name Parameter name.
		 * @param description Parameter description.
		 * @return The builder.
		 */
		public Builder addParam(String name, String description) {
			this.params.add(new JavadocParam(name, description));
			return this;
		}

		/**
		 * Update {@link #returns}
		 *
		 * @param description New {@link #returns} description.
		 * @return The builder.
		 */
		public Builder setReturns(String description) {
			this.returns = new JavadocReturn(description);
			return this;
		}

		/**
		 * Create the {@link Javadoc} instance.
		 *
		 * @return The Javadoc Instance.
		 */
		public Javadoc build() {
			return new Javadoc(description, new ArrayList<>(params), returns);
		}
	}
}
