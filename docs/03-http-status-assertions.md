# HTTP Assertions

**Note:** Code samples use `rest-assert-assertj`. If you want to use `rest-assert-unit` instead, replace:

```java
assertThat(response).myAssertion(parameters);
```

With:

```java
assertMyAssertion(response, parameters);
```

## Status Code

### `isStatusBetween`

The most basic assertion: check that status code is between `start` expected and `end` expected (inclusive).

*Note: to improve readability, use this method if other provided assertions are not enough.*

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertThat(response).isStatusBetween(200, 299);
  }
}
```

### `isStatusOutOf`

The most basic assertion: check that status code is strictly less than `start` expected or strictly greater than `end` expected.

*Note: to improve readability, use this method if other provided assertions are not enough.*

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertThat(response).isOutOf(200, 299);
  }
}
```

### `isSuccess`

Check that status code is between **200** and **299** (inclusive).

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertThat(response).isSuccess();
  }
}
```

### `isNotSuccess`

Check that status code is **not** between **200** and **299**.

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertThat(response).isNotSuccess();
  }
}
```

### `isRedirection`

Check that status code is between **300** and **399** (inclusive).

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertThat(response).isRedirection();
  }
}
```

### `isNotRedirection`

Check that status code is between **300** and **399** (inclusive).

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertThat(response).isNotRedirection();
  }
}
```

### `isClientError`

Check that status code is between **400** and **499**.

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.preparePost(url);
      .execute()
      .get();

    assertThat(response).isClientError();
  }
}
```

### `isNotClientError`

Check that status code is **not** between **400** and **499**.

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.preparePost(url);
      .execute()
      .get();

    assertThat(response).isNotClientError();
  }
}
```

### `isServerError`

Check that status code is between **500** and **599**.

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertThat(response).isServerError();
  }
}
```

### `isNotServerError`

Check that status code is between **500** and **599**.

```java
import org.junit.test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.assertj.api.assertThat;

public class Test {

  @Test
  public void should_be_ok() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertThat(response).isNotServerError();
  }
}
```
