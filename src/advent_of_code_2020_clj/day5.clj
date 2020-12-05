(ns advent-of-code-2020.day5
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day5"))))

(def row-map {\F :lower \B :upper})
(def col-map {\R :right \L :left})

(defn parse-pass [input]
  (let [rows (vec (map row-map (take 7 input)))
        cols (vec (map col-map (drop 7 input)))]
    {:rows rows :cols cols}))

(defn parse-passes [input]
  (map parse-pass input))

(def div-map {:upper drop :right drop :lower take :left take})

(defn bsp-divide [region rows]
  (let [half (int (/ (count rows) 2))]
    ((div-map region) half rows)))

(defn calc-region [regions size]
  (loop [regions regions
         bsp (range size)]
    (if (= (count regions) 0)
      (first bsp)
      (recur
       (drop 1 regions)
       (bsp-divide (first regions) bsp)))))

(defn calc-row [pass]
  (calc-region (pass :rows) 128))

(defn calc-col [pass]
  (calc-region (pass :cols) 8))

(defn calc-seat-id [pass]
  (let [row (calc-row pass)
        col (calc-col pass)]
    (+ (* row 8) col)))

(defn output []
  (let [passes (parse-passes (read-input))
        ids (map calc-seat-id passes)]
    (apply max ids)))

(comment
  (def pass "FFFBBBFRRR")
  (map row-map (take 7 pass))
  (parse-pass pass)
  (calc-row (parse-pass pass))
  (calc-col (parse-pass pass))
  (calc-seat-id (parse-pass pass))
  )
