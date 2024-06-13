(ns towersampling.tower
  "Simple sampling utility. It generates events based on pre-defined probability distribution"
  (:require
   [clojure.set :as set]
   [clojure.string :as str]))


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
  "Samples data according to given discrete distribution.
   This can be used to generate events for giving  probability distribution
  "

  (def event-probabilities (vals events))
  (def event-names (keys events))

  ;; (def probabilities (loop [prev 0 result [] probs event-probabilities]
  ;;                      (if (first probs)
  ;;                        (let [next-prob (+ prev (first probs))]
  ;;                          (recur next-prob (conj result next-prob) (rest probs)))
  ;;                        result)))
  (def probabilities
    (loop [res []
           xs  event-probabilities]
      (if (empty? xs)
        res
        (recur (conj res (+ (first xs)
                            (or (last res) 0)))
               (rest xs)))))

  ; each bucket of probability sum of all previous probabilities
  ;; (def probabilities (reduce #(into %1 [(+ (if (last %1) (last %1) 0) %2)]) [] event-probabilities))

  (fn [] (let [p (rand)
               index (first (keep-indexed #(when (<= p %2) %1) probabilities))]
           (nth event-names index))))


