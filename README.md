# rest-assert

rest-assert is a Java library to make testing your web services a breeze.

## Installation

Currently, rest-assert provide two dependencies:

- If you are using AssertJ, then this one is made for you:

```xml
<dependency>
  <groupId>com.github.mjeanroy</groupId>
  <artifactId>rest-assert-assertj</artifactId>
  <version>0.1.0</version>
  <scope>test</scope>
</dependency>
```

- If you are not using anything except JUnit, then this one is made for you:

```xml
<dependency>
  <groupId>com.github.mjeanroy</groupId>
  <artifactId>rest-assert-unit</artifactId>
  <version>0.1.0</version>
  <scope>test</scope>
</dependency>
```

## Assertions

Rest-Assert offer you a lot of new assertions that you will use to test:
- HTTP Response (status codes, headers, body, etc.).
- HTTP Cookies (name, value, flags, etc.).

Note that Rest-Assert is not a library to execute HTTP requests, a lot of library already exists that do it well (Apache-HTTP-Component, Async-Http, etc.).
But, don't worry, Rest-Assert will let you use some bindings on top of your favorite library!

### Status Codes

#### AssertJ

You will be able able to test status code of your request using lot of new assertions.
Here is a really small sample:

```java
public class GetUserTest {

  @Test
  public void test_get() {
    HttpResponse rsp = doGet();
    assertThat(rsp).isOk();
  }
}
```

Two things:
- Don't worry, I will explain later where does HttpResponse come from!
- Assertion `isOk` is really simple and just check that http response status is exactly 200.

Here is the list of all available assertions:

- Generic
  - `isStatusEqual(int status)` : Check that status code is exactly equal to given parameter.
  - `isStatusBetween(int start, int end)` : Check that status code is included between `start` and `end` (inclusive).
  - `isStatusOutOf(int start, int end)` : Check that status code is not included between `start` and `end` (inclusive).
- General Status
  - `isSuccess()` : Check that status code is included between 200 and 299.
  - `isRedirection()` : Check that status code is included between 300 and 399.
  - `isClientError()` : Check that status code is included between 400 and 499.
  - `isServerError()` : Check that status code is included between 500 and 599.
  - `isNotSuccess()` : Check that status code is not included between 200 and 299.
  - `isNotRedirection()` : Check that status code is not included between 300 and 399.
  - `isNotClientError()` : Check that status code is not included between 400 and 499.
  - `isNotServerError()` : Check that status code is not included between 500 and 599.
- Status 2XX
  - `isOk()` : Check that status code is 200.
  - `isCreated()` : Check that status code is 201.
  - `isAccepted()` : Check that status code is 202.
  - `isNoContent()` : Check that status code is 204.
  - `isResetContent()` : Check that status code is 205.
  - `isPartialContent()` : Check that status code is 206.
  - `isPartialContent()` : Check that status code is 206.
- Status 3XX
  - `isMovedPermanently()` : Check that status code is 301.
  - `isMovedTemporarily()` : Check that status code is 302.
  - `isNotModified()` : Check that status code is 304.
- Status 4XX
  - `isBadRequest()` : Check that status code is 400.
  - `isUnauthorized()` : Check that status code is 401.
  - `isForbidden()` : Check that status code is 403.
  - `isNotFound()` : Check that status code is 404.
  - `isMethodNotAllowed()` : Check that status code is 405.
  - `isNotAcceptable()` : Check that status code is 406.
  - `isConflict()` : Check that status code is 409.
  - `isPreConditionFailed()` : Check that status code is 412.
  - `isUnsupportedMediaType()` : Check that status code is 415.
- Status 5XX
  - `isInternalServerError()` : Check that status code is 500.
  - `isNotImplemented()` : Check that status code is 501.

#### JUnit

You will be able able to test status code of your request using lot of new assertions.
Here is a really small sample:

```java
public class GetUserTest {

  @Test
  public void test_get() {
    HttpResponse rsp = doGet();
    assertIsOk(rsp);

    // Or with a custom message
    assertIsOk("It should be ok", rsp);
  }
}
```

Here is the list of all available assertions:

- Generic
  - `assertIsStatusEqual(HttpResponse rsp, int status)` : Check that status code is exactly equal to given parameter.
  - `assertIsStatusBetween(HttpResponse rsp, int start, int end)` : Check that status code is included between `start` and `end` (inclusive).
  - `assertIsStatusOutOf(HttpResponse rsp, int start, int end)` : Check that status code is not included between `start` and `end` (inclusive).
- General Status
  - `assertIsSuccess(HttpResponse rsp)` : Check that status code is included between 200 and 299.
  - `assertIsRedirection(HttpResponse rsp)` : Check that status code is included between 300 and 399.
  - `assertIsClientError(HttpResponse rsp)` : Check that status code is included between 400 and 499.
  - `assertIsServerError(HttpResponse rsp)` : Check that status code is included between 500 and 599.
  - `assertIsNotSuccess(HttpResponse rsp)` : Check that status code is not included between 200 and 299.
  - `assertIsNotRedirection(HttpResponse rsp)` : Check that status code is not included between 300 and 399.
  - `assertIsNotClientError(HttpResponse rsp)` : Check that status code is not included between 400 and 499.
  - `assertIsNotServerError(HttpResponse rsp)` : Check that status code is not included between 500 and 599.
- Status 2XX
  - `assertIsOk(HttpResponse rsp)` : Check that status code is 200.
  - `assertIsCreated(HttpResponse rsp)` : Check that status code is 201.
  - `assertIsAccepted(HttpResponse rsp)` : Check that status code is 202.
  - `assertIsNoContent(HttpResponse rsp)` : Check that status code is 204.
  - `assertIsResetContent(HttpResponse rsp)` : Check that status code is 205.
  - `assertIsPartialContent(HttpResponse rsp)` : Check that status code is 206.
  - `assertIsPartialContent(HttpResponse rsp)` : Check that status code is 206.
- Status 3XX
  - `assertIsMovedPermanently(HttpResponse rsp)` : Check that status code is 301.
  - `assertIsMovedTemporarily(HttpResponse rsp)` : Check that status code is 302.
  - `assertIsNotModified(HttpResponse rsp)` : Check that status code is 304.
- Status 4XX
  - `assertIsBadRequest(HttpResponse rsp)` : Check that status code is 400.
  - `assertIsUnauthorized(HttpResponse rsp)` : Check that status code is 401.
  - `assertIsForbidden(HttpResponse rsp)` : Check that status code is 403.
  - `assertIsNotFound(HttpResponse rsp)` : Check that status code is 404.
  - `assertIsMethodNotAllowed(HttpResponse rsp)` : Check that status code is 405.
  - `assertIsNotAcceptable(HttpResponse rsp)` : Check that status code is 406.
  - `assertIsConflict(HttpResponse rsp)` : Check that status code is 409.
  - `assertIsPreConditionFailed(HttpResponse rsp)` : Check that status code is 412.
  - `assertIsUnsupportedMediaType(HttpResponse rsp)` : Check that status code is 415.
- Status 5XX
  - `assertIsInternalServerError(HttpResponse rsp)` : Check that status code is 500.
  - `assertIsNotImplemented(HttpResponse rsp)` : Check that status code is 501.
