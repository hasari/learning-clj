# Learning Clojure

This project contains various modules that exercise Clojure concepts. Moreover it includes deps.edn and build.clj file for dependencies and build. build.clj contains logic for creating uber jar.

## Module: Tower Sampler

This utility samples data according to given discrete distribution. This can be used to generate events for giving distribution. For example, for following distribution

```clojure
(def events {:sleep 0.33
             :walk 0.05
             :watch 0.35
             :eat 0.27})
(println events)
(def generator  (towersampling.tower/tower-sampling events))
(take 100 (repeatedly generator))

```

The sampler will return sleep with 33% probability and walk with 5% probability.

# Module: Optin/Optout

This module exercises Clojure/DB functionality

# Building uber jar

```bash
clj -T:build uber
```

Run the app as:

```bash
java -jar target/demo-lib-1.7-standalone.jar
```

# Run Test Cases

```bash
clj -M:test
```

# Generate docs

```bash
clj -X:codox
```
