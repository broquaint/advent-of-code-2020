(ns advent-of-code-2020.day9
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day9"))))

(defn parse-input [lines]
  (vec
   (map #(Long/valueOf %) lines)))

(defn is-valid-fail [next-num nums]
  (loop [n (first nums)
         remainder (rest nums)]
    (if (not (some #(= next-num (+ n %)) remainder))
      n
      (if (empty? remainder)
        true
        (recur (first remainder) remainder)))))


(defn is-valid [next-num nums]
  (loop [n (first nums)
         remainder (rest nums)]
    (if (some #(= next-num (+ n %)) remainder)
      true
      (if (empty? remainder)
        next-num
        (recur (first remainder) (rest remainder))))))

(defn find-invalid [nums window]
  (loop [to-consider (take window nums)
         remainder (drop (+ window 1) nums)
         n (nth nums window)]
    ;; Assumes we _will_ encounter an invalid number
    (if (true? (is-valid n to-consider))
      (recur (conj (vec (rest to-consider)) n) (rest remainder) (first remainder))
      n)))

(defn output []
  (find-invalid (parse-input (read-input)) 25))

(comment
  (def lines ["35" "20" "15" "25" "47" "40" "62" "55" "65" "95" "102" "117" "150" "182" "127" "219" "299" "277" "309" "567"])
  (def nums (parse-input lines))
  (is-valid 40 (take 5 nums))
  (is-valid 127 [95 102 117 150 182])
  
  )
