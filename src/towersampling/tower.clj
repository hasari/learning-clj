(ns towersampling.tower
  "Simple sampling utility. It generates events based on pre-defined probability distribution"
  (:require
   [clojure.set :as set]
   [clojure.string :as str]))


;; Each bucket of probability sum of all previous probabilities
;; (def probabilities (reduce #(into %1 [(+ (if (last %1) (last %1) 0) %2)]) [] event-probabilities))
(defn contruct-probabilities [event-probabilities]
  (loop [res []
         xs  event-probabilities]
    (if (empty? xs)
      res
      (recur (conj res (+ (first xs)
                          (or (last res) 0)))
             (rest xs)))))


;; Sampler samples data according to given discrete distribution
;; This can be used to generate events for giving distribution
;; For example, for following distribution,

;; (def events {:sleep 0.33
;;             :walk 0.05
;;             :watch 0.35
;;             :eat 0.27})
;; (def generator  (towersampling.tower/tower-sampling events))
;; (take 1000 (repeatedly generator))

;; the sampler will return sleep with 33% probability and  walk with 5% probability.
(defn tower-sampling [events]
 
  (let [event-names (keys events)
        probabilities (contruct-probabilities (vals events))]
    (fn [] (let [p (rand)
                 index (first (keep-indexed #(when (<= p %2) %1) probabilities))]
             (nth event-names index)))))

