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

(defn calc-path [map]
  (reduce
   (fn [[x y count] tree-line]
     [(+ x 3) (+ y 1) (+ count (did-hit x tree-line))])
   [0 0 0]
   map))

(defn output []
  (let [lines (read-input)
        map (parse-input lines)
        [x y result] (calc-path map)]
    result))

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
        trees (parse-input lines)]
    (calc-path trees))
  )
