(ns advent-of-code-2020.day4
  (:require [clojure.java.io :as io]))

(defn read-input []
  (line-seq (io/reader (io/resource "day4"))))

(def all-fields
  #{
    "byr" ; (Birth Year)
    "iyr" ; (Issue Year)
    "eyr" ; (Expiration Year)
    "hgt" ; (Height)
    "hcl" ; (Hair Color)
    "ecl" ; (Eye Color)
    "pid" ; (Passport ID)
    "cid" ; (Country ID)
    })

(def required-fields
  (disj all-fields "cid"))

(defn parse-input [lines]
  (let [passports (filter #(not (= "" (first %))) (partition-by #(= % "") lines))]
    (vec (map
          (fn [p]
            (map #(clojure.string/split %1 #":")
             (clojure.string/split (clojure.string/join " " p) #" ")))
          passports))))

(defn is-valid-passport [p]
  (let [p-fields (set (map first p))]
   (clojure.set/superset?
    p-fields
    required-fields)))

(defn output []
  (let [passports (parse-input (read-input))]
    (count (filter identity (map is-valid-passport passports)))))

(comment
  (def example-input ["ecl:gry pid:860033327 eyr:2020 hcl:#fffffd"
"byr:1937 iyr:2017 cid:147 hgt:183cm"
""
"iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929"
""
"hcl:#ae17e1 iyr:2013"
"eyr:2024"
"ecl:brn pid:760753108 byr:1931"
"hgt:179cm"
""
"hcl:#cfa07d eyr:2025 pid:166559648"
                      "iyr:2011 ecl:brn hgt:59in"])
  (filter #(not (= "" (first %))) (partition-by #(= % "") example-input))
  (parse-input example-input)
  (map is-valid-passport (parse-input example-input))
  (is-valid-passport (first (parse-input example-input)))
  )
