(ns advent-of-code-2020.day6
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day6"))))

(defn parse-input-p1 [lines]
  (vec
   (map (partial clojure.string/join "")
    (filter #(not (= "" (first %))) (partition-by #(= % "") lines)))))

(defn parse-input [lines]
  (vec
   (filter #(not (= "" (first %))) (partition-by #(= % "") lines))))

(defn count-answers-p1 [groups]
  (map
   (fn [g]
     (count (set g)))
   groups))

(defn find-group-answers [group]
  (let [tot (count group)
        freqs (frequencies (clojure.string/join group))]
    (filter (fn [[_ v]] (= tot v)) freqs)))

(defn count-answers [groups]
  (map count
   (map find-group-answers groups)))

(defn part1 []
  (let [groups (parse-input (read-input))]
    (apply + (count-answers-p1 groups))))

(defn output []
  (let [groups (parse-input (read-input))
        counts (count-answers groups)]
    (apply + counts)))

(comment
  ;; (def lines ["w" "s" "q" "s" "" "klfrwivqhc" "w" "wgyze" "anw"])
  (def lines ["abc" "" "a" "b" "c" "" "ab" "ac" "" "a" "a" "a" "a" "" "b"])
  (parse-input lines)
  (filter (fn [[k v]] (= 4 v))
          (frequencies (clojure.string/join "" (second (parse-input lines)))))
  (find-group-answers (second (parse-input lines)))
  (map frequencies (parse-input lines))
  (count-answers (parse-input lines))
  (filter #(not (= "" (first %))) (partition-by #(= % "") lines))
  )
