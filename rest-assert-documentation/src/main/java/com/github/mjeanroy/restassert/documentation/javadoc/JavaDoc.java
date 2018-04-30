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
public final class JavaDoc {

	/**
	 * Javadoc Description.
	 */
	private final String description;

	/**
	 * Javadoc Parameters, may be empty.
	 */
	private final List<JavaDocParam> params;

	/**
	 * Javadoc returns tag, may be null.
	 */
	private final JavaDocReturn returns;

	/**
	 * JavaDoc see tag, may be empty.
	 */
	private final List<JavaDocSee> see;

	/**
	 * Create Javadoc instance.
	 *
	 * @param description Javadoc Description.
	 * @param params Javadoc Parameters, may be empty.
	 * @param returns Javadoc returns tag, may be null.
	 */
	JavaDoc(String description, List<JavaDocParam> params, JavaDocReturn returns, List<JavaDocSee> see) {
		this.description = description;
		this.params = params;
		this.returns = returns;
		this.see = see;
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
	public List<JavaDocParam> getParams() {
		return params;
	}

	/**
	 * Get {@link #returns}
	 *
	 * @return {@link #returns}
	 */
	public JavaDocReturn getReturns() {
		 return returns;
	}

	/**
	 * Get {@link #see}
	 *
	 * @return {@link #see}
	 */
	public List<JavaDocSee> getSee() {
		return see;
	}

	@Override
	public String toString() {
		return String.format("JavaDoc{description=%s, params=%s, returns=%s, see=%s}", description, params, returns, see);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof JavaDoc) {
			JavaDoc doc = (JavaDoc) o;
			return Objects.equals(description, doc.description)
					&& Objects.equals(params, doc.params)
					&& Objects.equals(returns, doc.returns)
					&& Objects.equals(see, doc.see);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, params, returns, see);
	}

	/**
	 * Builder for {@link JavaDoc}.
	 */
	public static class Builder {

		/**
		 * The Javadoc Description.
		 * @see JavaDoc#description
		 */
		private String description;

		/**
		 * The Javadoc Parameters.
		 * @see JavaDoc#params
		 */
		private final List<JavaDocParam> params;

		/**
		 * The Javadoc See Tag.
		 * @see JavaDoc#see
		 */
		private final List<JavaDocSee> see;

		/**
		 * The Javadoc Returns.
		 * @see JavaDoc#returns
		 */
		private JavaDocReturn returns;

		/**
		 * Create the builder.
		 */
		public Builder() {
			this.params = new ArrayList<>();
			this.see = new ArrayList<>();
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
			this.params.add(new JavaDocParam(name, description));
			return this;
		}

		/**
		 * Add new entry to {@link #see} section.
		 *
		 * @param description Parameter description.
		 * @return The builder.
		 */
		public Builder addSee(String description) {
			this.see.add(new JavaDocSee(description));
			return this;
		}

		/**
		 * Update {@link #returns}
		 *
		 * @param description New {@link #returns} description.
		 * @return The builder.
		 */
		public Builder setReturns(String description) {
			this.returns = new JavaDocReturn(description);
			return this;
		}

		/**
		 * Create the {@link JavaDoc} instance.
		 *
		 * @return The Javadoc Instance.
		 */
		public JavaDoc build() {
			return new JavaDoc(description, new ArrayList<>(params), returns, new ArrayList<>(see));
		}
	}
}
