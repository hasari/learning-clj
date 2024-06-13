# Learning Clojure

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
