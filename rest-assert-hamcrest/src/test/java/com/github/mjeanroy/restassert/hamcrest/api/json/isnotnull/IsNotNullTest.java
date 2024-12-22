package com.github.mjeanroy.restassert.hamcrest.api.json.isnotnull;

import com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.github.mjeanroy.restassert.hamcrest.api.json.JsonMatchers.isNotNull;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

class IsNotNullTest {

	@ParameterizedTest()
	@ValueSource(strings = {"[]", "{}"})
	void it_should_pass(String input) {
		MatcherAssert.assertThat(input, isNotNull());
		MatcherAssert.assertThat(input, isNotNull());
	}

	@Test
	final void it_should_fail() {
		String message = HamcrestTestUtils.generateHamcrestErrorMessage(
			"Expecting json not to be null",
			"was null"
		);

		assertFailure(message, () ->
			MatcherAssert.assertThat(null, isNotNull())
		);
	}
}
