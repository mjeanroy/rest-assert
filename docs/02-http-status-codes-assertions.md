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

### `isStatusEqual`

The most basic assertion: check that status code is strictly equal to given number.

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

    assertThat(response).isStatusEqual(200);
  }
}
```

---

### 2XX

#### `isOk`

Check that status code is strictly equal to **200**.

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

    assertThat(response).isOk();
  }
}
```

#### `isCreated`

Check that status code is strictly equal to **201**.

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

    assertThat(response).isCreated();
  }
}
```

#### `isAccepted`

Check that status code is strictly equal to **202**.

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

    assertThat(response).isAccepted();
  }
}
```

#### `isNoContent`

Check that status code is strictly equal to **204**.

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

    assertThat(response).isNoContent();
  }
}
```

#### `isResetContent`

Check that status code is strictly equal to **205**.

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

    assertThat(response).isResetContent();
  }
}
```

#### `isPartialContent`

Check that status code is strictly equal to **206**.

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

    assertThat(response).isPartialContent();
  }
}
```

---

### 3XX

#### `isMovedPermanently`

Check that status code is strictly equal to **301**.

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

    assertThat(response).isMovedPermanently();
  }
}
```

#### `isMovedTemporarily`

Check that status code is strictly equal to **302**.

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

    assertThat(response).isMovedTemporarily();
  }
}
```

#### `isNotModified`

Check that status code is strictly equal to **304**.

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

    assertThat(response).isNotModified();
  }
}
```

---

### 4XX

#### `isBadRequest`

Check that status code is strictly equal to **400**.

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

    assertThat(response).isBadRequest();
  }
}
```

#### `isUnauthorized`

Check that status code is strictly equal to **401**.

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

    assertThat(response).isUnauthorized();
  }
}
```

#### `isForbidden`

Check that status code is strictly equal to **403**.

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

    assertThat(response).isForbidden();
  }
}
```

#### `isNotFound`

Check that status code is strictly equal to **404**.

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

    assertThat(response).isNotFound();
  }
}
```

#### `isMethodNotAllowed`

Check that status code is strictly equal to **405**.

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

    assertThat(response).isMethodNotAllowed();
  }
}
```

#### `isNotAcceptable`

Check that status code is strictly equal to **406**.

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

    assertThat(response).isNotAcceptable();
  }
}
```

#### `isConflict`

Check that status code is strictly equal to **409**.

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

    assertThat(response).isConflict();
  }
}
```

#### `isPreConditionFailed`

Check that status code is strictly equal to **412**.

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

    assertThat(response).isPreConditionFailed();
  }
}
```

#### `isUnsupportedMediaType`

Check that status code is strictly equal to **415**.

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

    assertThat(response).isUnsupportedMediaType();
  }
}
```

---

### 5XX

#### `isInternalServerError`

Check that status code is strictly equal to **500**.

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

    assertThat(response).isInternalServerError();
  }
}
```

#### `isNotImplemented`

Check that status code is strictly equal to **501**.

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

    assertThat(response).isNotImplemented();
  }
}
```
