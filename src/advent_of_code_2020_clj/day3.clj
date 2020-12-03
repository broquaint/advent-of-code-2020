(ns advent-of-code-2020.day3
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day3"))))

(defn parse-input [lines]
  (map
   (fn [line]
     (vec (map #(if (= % \#) 1 0) line)))
   lines))

(defn did-hit [x base-tree-line]
  (let [n (+ 1 (int (/ x (count base-tree-line))))
        tree-line (flatten (repeat n base-tree-line))]
   (nth tree-line x)))

(defn calc-path
  ([map] (calc-path 3 1 map))
  ([right down map]
   (reduce
    (fn [[x count] idx]
      [(+ x right) (+ count (did-hit x (nth map idx)))])
    [0 0]
   (range 0 (count map) down))))

(defn output []
  (let [lines (read-input)
        tree-map (parse-input lines)
        results (map (fn [[r d]] (calc-path r d tree-map)) [[1 1] [3 1] [5 1] [7 1] [1 2]])]
    (reduce * (map last results))))

(comment
  (vec (map (partial = \#) "..##......."))
  (parse-input ["..##......." "#...#...#.."])
  (let [lines (clojure.string/split "..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#" #"\n")
        trees (parse-input lines)
        results (map (fn [[r d]] (calc-path r d trees)) [[1 1] [3 1] [5 1] [7 1] [1 2]])]
    (apply * (map last results)))
  )
