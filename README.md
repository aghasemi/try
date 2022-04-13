# Try4J
![Try4J JitPack Status](https://jitpack.io/v/aghasemi/try4j.svg)

A very basic mimicking of Scala's Try construct. It also allows  "avoiding" checked exceptions whether a computations returns some result or not.


## Usage

```java
Optional<URL> y = Try4J.Try(() -> new URL("http://google.ch"));
```
`y` is `Optional.empty()` if an exception happens during the computation (here for example if the URL does not have the right format). Otherwise, it contains the result of the computation.

You can also replace the lambda with something that does not return any value.

## Installation using Maven

### Step 1. Add the [JitPack](https://jitpack.io/) repository to the project

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### Step 2. Add the Try4J dependency 

```xml
<dependency>
    <groupId>com.github.aghasemi</groupId>
        <artifactId>try4j</artifactId>
    <version>Tag</version>
</dependency>
```

`Tag` is the desired version.



