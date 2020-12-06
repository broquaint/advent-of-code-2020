(ns advent-of-code-2020.day6
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day6"))))

(defn parse-input [lines]
  (vec
   (map (partial clojure.string/join "")
    (filter #(not (= "" (first %))) (partition-by #(= % "") lines)))))

(defn count-answers [groups]
  (map
   (fn [g]
     (count (set g)))
   groups))

(defn output []
  (let [groups (parse-input (read-input))]
    (apply + (count-answers groups))))

(comment
  (def lines ["w" "s" "q" "s" "" "klfrwivqhc" "w" "wgyze" "anw"])
  (parse-input lines)
  (count-answers (parse-input lines))
  (filter #(not (= "" (first %))) (partition-by #(= % "") lines))
  )
