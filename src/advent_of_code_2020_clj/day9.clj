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

(defn add-up [target x remainder]
  (loop [acc x
         rem remainder]
    (let [y (first rem)
          result (+ acc y)]
      (if (or (empty? rem) (> result target))
        false
        (if (= result target)
          [x y]
          (recur result (rest rem)))))))

(defn find-contiguous-range [target nums]
  (loop [n (first nums)
         remainder (rest nums)]
    (if-let [result (add-up target n remainder)]
      (concat (take-while #(not (= % (second result))) remainder) result)
      (recur (first remainder) (rest remainder)))))

(defn output-p1 []
  (find-invalid (parse-input (read-input)) 25))

(defn output []
  (let [nums (parse-input (read-input))
        result (find-contiguous-range 1492208709 nums)]
    (+ (apply min result) (apply max result))))

(comment
  (def lines ["35" "20" "15" "25" "47" "40" "62" "55" "65" "95" "102" "117" "150" "182" "127" "219" "299" "277" "309" "567"])
  (def nums (parse-input lines))
  (is-valid 40 (take 5 nums))
  (is-valid 127 [95 102 117 150 182])
  (find-contiguous-range 127 nums)
  )
