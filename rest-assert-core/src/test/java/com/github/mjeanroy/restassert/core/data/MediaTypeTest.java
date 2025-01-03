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

package com.github.mjeanroy.restassert.core.data;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MediaTypeTest {

	@Test
	void it_should_create_media_type_text() {
		MediaType mediaType = MediaType.text("plain");
		assertThat(mediaType.getType()).isEqualTo("text");
		assertThat(mediaType.getSubtype()).isEqualTo("plain");
		assertThat(mediaType.serializeValue()).isEqualTo("text/plain");
		assertThat(mediaType.toString()).isEqualTo(
			"MediaType{" +
				"type=text, " +
				"subtype=plain" +
				"}"
		);
	}

	@Test
	void it_should_create_media_type_application() {
		MediaType mediaType = MediaType.application("json");
		assertThat(mediaType.getType()).isEqualTo("application");
		assertThat(mediaType.getSubtype()).isEqualTo("json");
		assertThat(mediaType.serializeValue()).isEqualTo("application/json");
		assertThat(mediaType.toString()).isEqualTo(
			"MediaType{" +
				"type=application, " +
				"subtype=json" +
				"}"
		);
	}

	@Test
	void it_should_create_media_type_image() {
		MediaType mediaType = MediaType.image("png");
		assertThat(mediaType.getType()).isEqualTo("image");
		assertThat(mediaType.getSubtype()).isEqualTo("png");
		assertThat(mediaType.serializeValue()).isEqualTo("image/png");
		assertThat(mediaType.toString()).isEqualTo(
			"MediaType{" +
				"type=image, " +
				"subtype=png" +
				"}"
		);
	}

	@Test
	void it_should_create_media_type_video() {
		MediaType mediaType = MediaType.video("ogg");
		assertThat(mediaType.getType()).isEqualTo("video");
		assertThat(mediaType.getSubtype()).isEqualTo("ogg");
		assertThat(mediaType.serializeValue()).isEqualTo("video/ogg");
		assertThat(mediaType.toString()).isEqualTo(
			"MediaType{" +
				"type=video, " +
				"subtype=ogg" +
				"}"
		);
	}

	@Test
	void it_should_create_media_type_audio() {
		MediaType mediaType = MediaType.audio("mp3");
		assertThat(mediaType.getType()).isEqualTo("audio");
		assertThat(mediaType.getSubtype()).isEqualTo("mp3");
		assertThat(mediaType.serializeValue()).isEqualTo("audio/mp3");
		assertThat(mediaType.toString()).isEqualTo(
			"MediaType{" +
				"type=audio, " +
				"subtype=mp3" +
				"}"
		);
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(MediaType.class).verify();
	}

	@Test
	void it_should_fail_if_type_is_null() {
		assertThatThrownBy(() -> new MediaType(null, "subtype"))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage("MediaType type must be defined");
	}

	@Test
	void it_should_fail_if_type_is_empty() {
		assertThatThrownBy(() -> new MediaType("", "subtype"))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("MediaType type must be defined");
	}

	@Test
	void it_should_fail_if_type_is_blank() {
		assertThatThrownBy(() -> new MediaType("    ", "subtype"))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("MediaType type must be defined");
	}

	@Test
	void it_should_fail_if_subtype_is_null() {
		assertThatThrownBy(() -> new MediaType("type", null))
			.isExactlyInstanceOf(NullPointerException.class)
			.hasMessage("MediaType subtype must be defined");
	}

	@Test
	void it_should_fail_if_subtype_is_empty() {
		assertThatThrownBy(() -> new MediaType("type", ""))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("MediaType subtype must be defined");
	}

	@Test
	void it_should_fail_if_subtype_is_blank() {
		assertThatThrownBy(() -> new MediaType("type", "    "))
			.isExactlyInstanceOf(IllegalArgumentException.class)
			.hasMessage("MediaType subtype must be defined");
	}
}
