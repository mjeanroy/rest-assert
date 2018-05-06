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

package com.github.mjeanroy.restassert.core.data;

import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;
import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

import java.util.Objects;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;

/**
 * The media type representation.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types">https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types</a>
 */
public final class MediaType implements HeaderValue {

	/**
	 * Create {@link MediaType} with {@code "text"} type.
	 *
	 * @param subtype The media-type subtype.
	 * @return The media-type.
	 * @throws NullPointerException If {@code subtype} is {@code null}.
	 * @throws IllegalArgumentException If {@code subtype} is empty or blank.
	 */
	public static MediaType text(String subtype) {
		return new MediaType("text", subtype);
	}

	/**
	 * Create {@link MediaType} with {@code "application"} type.
	 *
	 * @param subtype The media-type subtype.
	 * @return The media-type.
	 * @throws NullPointerException If {@code subtype} is {@code null}.
	 * @throws IllegalArgumentException If {@code subtype} is empty or blank.
	 */
	public static MediaType application(String subtype) {
		return new MediaType("application", subtype);
	}

	/**
	 * Create {@link MediaType} with {@code "audio"} type.
	 *
	 * @param subtype The media-type subtype.
	 * @return The media-type.
	 * @throws NullPointerException If {@code subtype} is {@code null}.
	 * @throws IllegalArgumentException If {@code subtype} is empty or blank.
	 */
	public static MediaType audio(String subtype) {
		return new MediaType("audio", subtype);
	}

	/**
	 * Create {@link MediaType} with {@code "video"} type.
	 *
	 * @param subtype The media-type subtype.
	 * @return The media-type.
	 * @throws NullPointerException If {@code subtype} is {@code null}.
	 * @throws IllegalArgumentException If {@code subtype} is empty or blank.
	 */
	public static MediaType video(String subtype) {
		return new MediaType("video", subtype);
	}

	/**
	 * Create {@link MediaType} with {@code "image"} type.
	 *
	 * @param subtype The media-type subtype.
	 * @return The media-type.
	 * @throws NullPointerException If {@code subtype} is {@code null}.
	 * @throws IllegalArgumentException If {@code subtype} is empty or blank.
	 */
	public static MediaType image(String subtype) {
		return new MediaType("image", subtype);
	}

	/**
	 * The parser instance.
	 */
	private static final MediaTypeParser PARSER = new MediaTypeParser();

	/**
	 * Get parser for {@link MediaType} raw value.
	 *
	 * @return The parser.
	 */
	public static MediaTypeParser parser() {
		return PARSER;
	}

	/**
	 * The media-type main type.
	 *
	 * Currently, only following types are "officially" supported:
	 * <ul>
	 *   <li>{@code "text"}</li>
	 *   <li>{@code "application"}</li>
	 *   <li>{@code "image"}</li>
	 *   <li>{@code "audio"}</li>
	 *   <li>{@code "video"}</li>
	 * </ul>
	 */
	private final String type;

	/**
	 * The media-type subtype.
	 * For example, with a type {@code "text"}, the subtype could be {@code "plain"} or {@code "css"}, etc.).
	 */
	private final String subtype;

	/**
	 * Create the media-type value.
	 *
	 * @param type The main type.
	 * @param subtype The subtype.
	 * @throws NullPointerException If {@code type} or {@code subtype} are {@code null}.
	 * @throws IllegalArgumentException If {@code type} or {@code subtype} are empty or blank.
	 */
	MediaType(String type, String subtype) {
		this.type = notBlank(type, "MediaType type must be defined");
		this.subtype = notBlank(subtype, "MediaType subtype must be defined");
	}

	/**
	 * Get {@link #type}
	 *
	 * @return {@link #type}
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get {@link #subtype}
	 *
	 * @return {@link #subtype}
	 */
	public String getSubtype() {
		return subtype;
	}

	@Override
	public String serializeValue() {
		return type + "/" + subtype;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof MediaType) {
			MediaType mt = (MediaType) o;
			return Objects.equals(type, mt.type) && Objects.equals(subtype, mt.subtype);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, subtype);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("type", type)
			.append("subtype", subtype)
			.build();
	}
}
