(ns demo.main
  (:require
   [tower-sampling.tower]))


(def events {"sleep" 0.33
             "walk" 0.05
             "watch" 0.35
             "eat" 0.27})
(println events)
(def generator  (tower-sampling.tower/tower-sampling events))
(take 100 (repeatedly generator))

