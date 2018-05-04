# JSON Assertions

## JSON

### `isEqualTo(json)`

Compare two JSON representation.

This assertion take one parameter, this parameter may be:

- A string (must be a JSON string).
- A file containing the JSON expected.
- A path containing the JSON expected.
- An URL pointing to the JSON expected.
- An URI pointing to the JSON expected.


```java
import java.nio.file.Paths;
import org.junit.Test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static com.github.mjeanroy.restassert.internal.data.HttpResponse.JsonAssertions.assertJsonThat;

public class MyTest {
  @Test
  public void it_should_return_json() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertJsonThat(response.getContent())
      .isEqualTo(Paths.get("/user.json"));
  }
}
```

### `isEqualToIgnoring(json, keys)`

Compare two JSON representation and ignore some keys during the comparison.

This assertion take two parameter, first parameter may be:

- A string (must be a JSON string).
- A file containing the JSON expected.
- A path containing the JSON expected.
- An URL pointing to the JSON expected.
- An URI pointing to the JSON expected.

```java
import java.nio.file.Paths;
import org.junit.Test;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import static java.util.Collections.singletonList;
import static com.github.mjeanroy.restassert.internal.data.HttpResponse.JsonAssertions.assertJsonThat;

public class MyTest {
  @Test
  public void it_should_return_json() {
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    Response response = asyncHttpClient.prepareGet(url);
      .execute()
      .get();

    assertJsonThat(response.getContent())
      .isEqualToIgnoring(Paths.get("/user.json"), singletonList("id"));
  }
}
```
