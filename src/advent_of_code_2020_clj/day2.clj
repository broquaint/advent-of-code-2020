(ns advent-of-code-2020.day2
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day2"))))

(defn parse-input [lines]
  (for [line lines]
    (let [[_ min max char password] (first (re-seq #"(\d+)-(\d+) ([a-z]): (\w+)" line))]
      [(Integer/valueOf min) (Integer/valueOf max) (first char) password])))

(defn find-valid-passwords [inputs]
  (filter
   (fn [[min max char password]]
     (when-some [freq ((frequencies password) char)]
       (and
        (>= freq min)
        (<= freq max))))
   inputs))

(defn output []
  (let [lines (read-input)
        inputs (parse-input lines)]
    (count (find-valid-passwords inputs))))

(comment
  (parse-input ["1-3 a: abcde" "1-3 b: cdefg" "2-9 c: ccccccccc"]))
  (let [inputs (parse-input ["1-3 a: abcde" "1-3 b: cdefg" "2-9 c: ccccccccc"])]
   (find-valid-passwords inputs))
  )
