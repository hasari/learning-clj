(ns demo.main
  (:gen-class)
  (:require
   [towersampling.tower]))


(def events {:sleep 0.33
             :walk 0.05
             :watch 0.35
             :eat 0.27})

(def generator  (towersampling.tower/tower-sampling events))

(defn -main [& args]
  (println "Demo App....")
  (println (take 100 (repeatedly generator))))

