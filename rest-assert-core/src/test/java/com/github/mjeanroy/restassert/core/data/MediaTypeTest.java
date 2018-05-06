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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class MediaTypeTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void it_should_create_media_type_text() {
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
	public void it_should_create_media_type_application() {
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
	public void it_should_create_media_type_image() {
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
	public void it_should_create_media_type_video() {
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
	public void it_should_create_media_type_audio() {
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
	public void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(MediaType.class).verify();
	}

	@Test
	public void it_should_fail_if_type_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("MediaType type must be defined");
		new MediaType(null, "subtype");
	}

	@Test
	public void it_should_fail_if_type_is_empty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("MediaType type must be defined");
		new MediaType("", "subtype");
	}

	@Test
	public void it_should_fail_if_type_is_blank() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("MediaType type must be defined");
		new MediaType("    ", "subtype");
	}

	@Test
	public void it_should_fail_if_subtype_is_null() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("MediaType subtype must be defined");
		new MediaType("type", null);
	}

	@Test
	public void it_should_fail_if_subtype_is_empty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("MediaType subtype must be defined");
		new MediaType("type", "");
	}

	@Test
	public void it_should_fail_if_subtype_is_blank() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("MediaType subtype must be defined");
		new MediaType("type", "    ");
	}
}
