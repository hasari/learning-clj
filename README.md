# TACC Redis

TACC redis integration api

# Building uber jar

```bash
clj -T:build uber
```

Run the app as:

```bash
java -jar target/tacc-redis-<version>-standalone.jar
```

# Run Test Cases

```bash
clj -M:test
```

# Generate docs

```bash
clj -X:codox
```
