(ns convolution.convolution)

;; Following following article
;; https://www.alvarorevuelta.com/posts/fft-polynomials


(defn sum* [coll1 coll2]
  (reduce + (mapv * coll1 coll2)))


(def p [2 3 4])
(def q [5 6 7])

(defn procedural-convo
  [p q]
  (let [results (transient {})]
    (doseq [i (range (count p))
            j (range (count q))
            :let [key (+ i j)
                  pi (get p i)
                  qj (get q j)
                  new-val (+ (or (get results key) 0) (* pi qj))]]
      (assoc! results key new-val))
    (vals (persistent! results))))



(procedural-convo p q)

(defn func-convo
  [p q]
  (let [pr (reverse p)
        qr (reverse q)
        pl (count p)
        ql (count q)
        fn-part (fn [n coll] (reverse (first (partition-all n coll))))
        part1 ; reverse q and slide until q completely overlaps p
        (for [i (range 1 (+ 1 ql))]
          (sum* (fn-part i q) p))

        part2 ;slide q to the right until it no longer overlaps p
        (for [i (range (- pl 1) 0  -1)]
          (sum* (fn-part i pr) qr))]

    (concat part1 part2)))


(func-convo p q)



(defn m-mult [p q] (do (println p q) (partition-all (count p) (for [x p y q] (* x y)))))

(defn mvec [n p q]
  (let [
        f (take (+ n 1) p)
        rp (rest p)
        ad (mapv + rp q)
        l (list (last q))]
    (do
      (println "--" p q " ad:" ad " rp:" rp " ---f:" f)
      (list (+ n 1) (concat f ad l)))))



;; (defn func-convo-proper
;;   [p q]
;;   (loop [parts (map-indexed (fn [idx itm] [idx itm]) (m-mult p q))]
;;     (println parts " :--->")
;;     (reduce mvec 1 parts)))


(def x [2 3 4])
(def h [5 6 7])


;(10 27 52 50 34 7)
;(func-convo-proper h x)