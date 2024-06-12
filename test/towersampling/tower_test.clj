(ns towersampling.tower_test
  (:require
   [clojure.test :refer [are deftest is testing run-all-tests]]))

(def events {"sleep" 0.33
             "walk" 0.05
             "watch" 0.35
             "eat" 0.27})

(def generator  (towersampling.tower/tower-sampling events))


(def sample-size 1000)
(deftest tower-sampling-test
  (testing "Test tower sampling"
    (let [results (frequencies (take sample-size (repeatedly generator)))
          freq-sleep-diff (- (results "sleep") 0.33)]
      (do
        (println "diff:" freq-sleep-diff " results:" results)
        (is (< freq-sleep-diff 1e-2))))))


(tower-sampling-test) 