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

package com.github.mjeanroy.restassert.tests.junit;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.platform.commons.util.AnnotationUtils;

import java.util.Optional;
import java.util.TimeZone;

class TimeZoneExtension implements BeforeEachCallback, AfterEachCallback {

	private static final Namespace NAMESPACE = Namespace.create(TimeZoneExtension.class);
	private static final String CURRENT_TIME_ZONE = "current_time_zone";

	TimeZoneExtension() {
	}

	@Override
	public void beforeEach(ExtensionContext extensionContext) {
		Optional<UseTimeZone> maybeAnnotation = AnnotationUtils.findAnnotation(
			extensionContext.getRequiredTestClass(),
			UseTimeZone.class
		);

		if (!maybeAnnotation.isPresent()) {
			throw new IllegalStateException(
				"Cannot use TimeZoneExtension without @UseTimeZone"
			);
		}

		TimeZone currentTimeZone = TimeZone.getDefault();
		getStore(extensionContext).put(CURRENT_TIME_ZONE, currentTimeZone);

		TimeZone newTimeZone = TimeZone.getTimeZone(maybeAnnotation.get().tzId());
		TimeZone.setDefault(newTimeZone);
	}

	@Override
	public void afterEach(ExtensionContext extensionContext) {
		TimeZone currentTimeZone = getStore(extensionContext).remove(CURRENT_TIME_ZONE, TimeZone.class);

		if (currentTimeZone == null) {
			throw new IllegalStateException(
				"Cannot get current time zone"
			);
		}

		TimeZone.setDefault(currentTimeZone);
	}

	private static Store getStore(ExtensionContext context) {
		return context.getStore(NAMESPACE);
	}
}
