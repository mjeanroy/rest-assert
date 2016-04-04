# Rest-Assert

## Introduction

Rest-Assert is a Java library that aims to make HTTP test simple and robust.

Why would you want to use it ?

- Make your unit tests more expressive by using simple and readable assertions.
- Integration with well known libraries (Apache HttpClient, Async HTTP, etc.).
- Integration with JUnit and AssertJ.

## How to use it ?

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

- If you only need a JUnit extension, then this one is made for you:

```xml
<dependency>
  <groupId>com.github.mjeanroy</groupId>
  <artifactId>rest-assert-unit</artifactId>
  <version>0.1.0</version>
  <scope>test</scope>
</dependency>
```

Note: if you want to compare JSON, you will have to include a JSON parsing library (currently Jackson and Gson are supported).
