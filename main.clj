(def moon-period 29.530588853)
(def phase-length (/ moon-period 8))
; current moon phase is computed relative to the new moon of 2000-01-06
(def new-moon-start (java.time.LocalDate/of 2000 1 6))

(defn moon-phase [now]
  (let [days-since (.until new-moon-start now java.time.temporal.ChronoUnit/DAYS)
        since-last-new (mod days-since moon-period)
        offsetted-day (+ since-last-new (/ phase-length 2))]
    (cond
      ; since Java strings are stored with UTF-16,
      ; we have to represent emoji as integers, and
      ; later convert them into strings.
      (< offsetted-day (* 1 phase-length)) 127761
      (< offsetted-day (* 2 phase-length)) 127762
      (< offsetted-day (* 3 phase-length)) 127763
      (< offsetted-day (* 4 phase-length)) 127764
      (< offsetted-day (* 5 phase-length)) 127765
      (< offsetted-day (* 6 phase-length)) 127766
      (< offsetted-day (* 7 phase-length)) 127767
      (< offsetted-day (* 8 phase-length)) 127768
      true                                 127761)))

(defn emoji-int->string [emoji]
  (String. (int-array [emoji]) 0 1))

(-> (java.time.LocalDate/now)
    ; (java.time.LocalDate/of YEAR MONTH DAY) ; uncomment this and comment out the above line to use your own date - i use this for testing
    (moon-phase)
    (emoji-int->string)
    (println))