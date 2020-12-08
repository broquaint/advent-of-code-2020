(ns advent-of-code-2020.day8
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day8"))))

(def op-str-map
  {"acc" :acc
   "jmp" :jmp
   "nop" :nop})

(defn parse-input [lines]
  (vec
   (map-indexed
    (fn [idx line]
      (let [[op-str val-str] (clojure.string/split line #" ")]
        [idx (op-str-map op-str) (Integer/valueOf val-str)]))
    lines)))

(defn run-op [[op val] acc pc]
  (case op
      :nop [acc (+ pc 1)]
      :acc [(+ acc val) (+ pc 1)]
      :jmp [acc (+ pc val)]))

(defn run-ops [ops acc cur-pc seen]
  (let [[pc op val] (ops cur-pc)]
    (if (seen pc)
      acc
      (let [[new-acc new-pc] (run-op [op val] acc pc)]
        (recur ops new-acc new-pc (conj seen pc))))))

(defn output []
  (run-ops (parse-input (read-input)) 0 0 #{}))

(comment
  (def lines ["nop +0"
"acc +1"
"jmp +4"
"acc +3"
"jmp -3"
"acc -99"
"acc +1"
"jmp -4"
"acc +6"])

  (parse-input lines)
  (run-op [:acc 3] 3 0)
  (run-ops (parse-input (read-input)) 0 0 #{})
  )
