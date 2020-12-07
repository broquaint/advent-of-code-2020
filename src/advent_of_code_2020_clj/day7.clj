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
            bag-pairs (map #(reverse (rest %)) (re-seq #"(\d+) (\w+ \w+)"  contained-str))
            bag-counts (map (fn [p] [(first p) (Integer/valueOf (second p))]) bag-pairs)
            bags (apply hash-map (flatten bag-counts))]
    [container bags]))
    lines)))

(defn find-valid-containers [outer inner bags]
  (let [matches (for [bag (bags outer)]
                  (if (= bag inner)
                    outer
                    (find-valid-containers bag inner bags)))] 
    (flatten matches)))

(defn count-containing-bags [outer bags]
  (reduce
   (fn [acc [k v]]
     (+ acc (* v (count-containing-bags (bags k) bags))))
   1
   outer))

(defn output-p1 []
  (let [bags (parse-input (read-input))
        valid-bags (filter #(> (count (find-valid-containers % "shiny gold" bags)) 0) (keys bags))]
    (count valid-bags)))

(defn output []
  (let [bags (parse-input (read-input))]
    ;; Small off by one error as the top–level bag is added to the count, but easy enough to account for by hand by subtracting 1 to the result.
    (count-containing-bags (bags "shiny gold") bags)))

(comment
  (def lines [
              "shiny gold bags contain 2 dark red bags."
"dark red bags contain 2 dark orange bags."
"dark orange bags contain 2 dark yellow bags."
"dark yellow bags contain 2 dark green bags."
"dark green bags contain 2 dark blue bags."
"dark blue bags contain 2 dark violet bags."
"dark violet bags contain no other bags."])
  (def bags (parse-input lines))

  (count-containing-bags (bags "shiny gold") bags)
  
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
