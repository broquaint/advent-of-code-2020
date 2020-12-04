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
            (let [pairs (map #(clojure.string/split %1 #":")
                             (clojure.string/split (clojure.string/join " " p) #" "))]
              (apply hash-map (flatten pairs))))
          passports))))

(defn valid-fields? [p]
  (letfn [(matches [re s]
            (re-matches (re-pattern (str "^" re "$")) (p s)))]
    (some? (and
            ;; byr (Birth Year) - four digits; at least 1920 and at most 2002.
            (matches #"(?:19[2-9]\d)|(?:200[0-2])" "byr")
            ;; iyr (Issue Year) - four digits; at least 2010 and at most 2020.
            (matches #"20(?:1\d|20)" "iyr")
            ;; eyr (Expiration Year) - four digits ; at least 2020 and at most 2030.
            (matches #"20(?:2\d|30)" "eyr")
            ;; hgt (Height) - a number followed by either cm or in:
            ;; If cm, the number must be at least 150 and at most 193.
            ;; If in, the number must be at least 59 and at most 76.
            (matches #"(?:1(?:[5-8]\d|9[0-3])cm)|(?:(?:59|6\d|7[0-6])in)" "hgt")
            ;; hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            (matches #"#[0-9a-f]{6}" "hcl")
            ;; ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} (p "ecl"))
            ;; pid (Passport ID) - a nine-digit number, including leading zeroes.
            (matches #"\d{9}" "pid")
            ;; cid (Country ID) - ignored, missing or not.
            ))))

(defn is-valid-passport-p1 [p]
  (clojure.set/superset?
   (set (keys p))
   required-fields))

(defn is-valid-passport [p]
  (and
   (clojure.set/superset?
    (set (keys p))
    required-fields)
   (valid-fields? p)))

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
  (apply hash-map (flatten (first (parse-input example-input))))
  )
