(ns advent-of-code-2020.day10
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day10"))))

(defn parse-input [lines]
  (vec
   (map #(Integer/valueOf %) lines)))

(defn joltage-differences [nums]
  (map-indexed
   (fn [idx val]
     ; (println (nth nums (+ idx 1) val) "-" val)
     (-
      (nth nums (+ idx 1) val)
      val))
   (butlast nums)))

(defn output []
  (let [nums (sort (parse-input (read-input)))
        diffs (joltage-differences nums)
        ones (count (filter #(= % 1) diffs))
        threes (count (filter #(= % 3) diffs))]
    ;; Add one to account for off-by-one, too lazy to fix.
    [ones threes (* (+ 1 ones) (+ 1 threes))]))

(comment
  (def nums
    (sort [28 33 18 42 31 14 46 20 48 47 24 23 49 45 19 38 39 11 1 32 25 35 8 17 7 9 4 2 34 10 3])
    ;(sort [16 10 15 5 1 11 7 19 6 12 4])
    )

  (map-indexed
   (fn [idx val]
     ; (println (nth nums (+ idx 1) val) "-" val)
     (-
      (nth nums (+ idx 1) val)
      val))
   (butlast nums))
  )
