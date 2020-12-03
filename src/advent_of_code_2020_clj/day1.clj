(ns advent-of-code-2020.day1
  (:require [clojure.java.io :as io]))

(defn read-input []
  (let [lines (line-seq (io/reader (io/resource "day1")))]
    (map #(Integer/valueOf %) lines)))

(defn calc [lines]
  (let [[n1 n2] (first
                 (filter (fn [[n1 n2]] (= 2020 (+ n1 n2)))
                         (for [[offset n1] (map-indexed #(list (+ %1 1) %2) lines)
                               n2 (drop offset lines)]
                           [n1 n2]
                           )))]
    (* n1 n2)))

(defn output []
  (calc (read-input)))

