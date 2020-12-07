(ns advent-of-code-2020.day7
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day7"))))

(defn parse-input [lines]
  (into (hash-map)
   (map
    (fn [line]
        (let [[container-str contained-str] (clojure.string/split line #" contain ")
        container (clojure.string/replace container-str " bags" "")
        bags (map #(last %) (re-seq #"(\d+) (\w+ \w+)"  contained-str))]
    [container bags]))
    lines)))

(defn find-valid-containers [outer inner bags]
  (let [matches (for [bag (bags outer)]
                  (if (= bag inner)
                    outer
                    (find-valid-containers bag inner bags)))] 
    (flatten matches)))

(defn output []
  (let [bags (parse-input (read-input))
        valid-bags (filter #(> (count (find-valid-containers % "shiny gold" bags)) 0) (keys bags))]
    (count valid-bags)))

(comment
  (def lines [
"light red bags contain 1 bright white bag, 2 muted yellow bags."
"dark orange bags contain 3 bright white bags, 4 muted yellow bags."
"bright white bags contain 1 shiny gold bag."
"muted yellow bags contain 2 shiny gold bags, 9 faded blue bags."
"shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags."
"dark olive bags contain 3 faded blue bags, 4 dotted black bags."
"vibrant plum bags contain 5 faded blue bags, 6 dotted black bags."
"faded blue bags contain no other bags."
              "dotted black bags contain no other bags."])
  (def bags (parse-input lines))
  
  (find-valid-containers "light red" "shiny gold" bags)

  (let [valid-bags (filter #(> (count (find-valid-containers % "shiny gold" bags)) 0) (keys bags))]
    valid-bags)
  

  (re-seq #"(\w+ \w+ bags) contain (no other bags)|((?:\d \w+ \w+ bags?[,.]\s?+))"
          "light red bags contain 1 bright white bag, 2 muted yellow bags.")
  (let [[container-str contained-str] (clojure.string/split
                                       "light red bags contain 1 bright white bag, 2 muted yellow bags."
                                       #" contain ")
        container (clojure.string/replace container-str " bags" "")
        bags (map #(drop 1 %) (re-seq #"(\d+) (\w+ \w+)"  contained-str))
        ]
    [container bags]
   )
  )
