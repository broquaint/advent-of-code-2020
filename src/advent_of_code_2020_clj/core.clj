(ns advent-of-code-2020-clj.core
  (comment (:gen-class)))

(ns advent-of-code-2019.core
  (:require [advent-of-code-2020.day1 :as day1]
            ;; [advent-of-code-2020.day2 :as day2]
            ;; [advent-of-code-2020.day3 :as day3]
            ;; [advent-of-code-2020.day4 :as day4]
            ;; [advent-of-code-2020.day5 :as day5]
            ;; [advent-of-code-2020.day6 :as day6]
            ;; [advent-of-code-2020.day6 :as day7]
            ))

(defn -main [& args]
  (let [day (first args)
        call (read-string (str "advent-of-code-2020.day" day "/output"))]
    (println (str "Running day" day ":"))
    (println (eval (list call)))))
