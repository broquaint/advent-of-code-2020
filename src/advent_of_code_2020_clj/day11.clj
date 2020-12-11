(ns advent-of-code-2020.day11
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day11"))))

(def seat-char-map {\L :empty \. :floor \# :occupied })

(defn parse-input [lines]
  (vec
   (map #(map seat-char-map %) lines)))

(defn get-seat [x y seating-map]
  (let [row (nth seating-map y [])
        seat (nth row x :empty)]
    seat))

(defn is-empty [seat]
  (or (= :empty seat) (= :floor seat)))

(defn compute-empty [x y seating-map]
  (let [up    (- y 1)
        down  (+ y 1)
        left  (- x 1)
        right (+ x 1)
        should-occupy (every?
                       is-empty
                       (map (fn [[x y]] (get-seat x y seating-map))
                            [[left up] [x up] [right up]
                             [left y] [right y]
                             [left down] [x down] [right down]]))]
    (if should-occupy
      :occupied
      :empty)))

(defn compute-occupied [x y seating-map]
  (let [up    (- y 1)
        down  (+ y 1)
        left  (- x 1)
        right (+ x 1)
        occupied (filter
                       #(= :occupied %)
                       (map (fn [[x y]] (get-seat x y seating-map))
                            [[left up] [x up] [right up]
                             [left y] [right y]
                             [left down] [x down] [right down]]))]
    (if (> (count occupied) 3)
      :empty
      :occupied)))

(defn alloc-seats [seating-map]
  (vec
   (map-indexed
    (fn [y row]
      (map-indexed
       (fn [x seat]
         (case seat
           :floor :floor
           :empty (compute-empty x y seating-map)
           :occupied (compute-occupied x y seating-map)
             ))
       row))
    seating-map)))

(defn count-occupied [seating-map]
  (count
   (filter (complement is-empty) (flatten seating-map))))

(defn compute-seating [seating-map]
  (let [next-map (alloc-seats seating-map)]
    (if (= next-map seating-map)
      (count-occupied seating-map)
      (compute-seating next-map))))

(def char-seat-map {:empty \L :floor \. :occupied \#})

(defn print-map [seating-map]
  (let [char-rows (map #(map char-seat-map %) seating-map)
        row-strings (map #(clojure.string/join "" %) char-rows)]
    (doseq [row row-strings]
      (println row))))

(defn output []
  (let [seating-map (parse-input (read-input))]
    (compute-seating seating-map)))

(comment
  (def lines [
              "L.LL.LL.LL"
              "LLLLLLL.LL"
              "L.L.L..L.."
              "LLLL.LL.LL"
              "L.LL.LL.LL"
              "L.LLLLL.LL"
              "..L.L....."
              "LLLLLLLLLL"
              "L.LLLLLL.L"
              "L.LLLLL.LL"])

  (def seating-map (parse-input read))

  (get-seat 1 1 seating-map)

  (compute-empty 2 0 seating-map)
  (compute-occupied 1 1 seating-map)

  (compute-seating seating-map)
  )
