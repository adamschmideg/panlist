(ns panlist.calendar
  [:import [java.util Calendar]])

(defn next-day [day]
  (let [d2 (.clone day)
        _ (.add d2 Calendar/DAY_OF_YEAR 1)]
    d2))

(defn days [start ndays]
  (loop [the-days [start]
         current (next-day start)
         ndays (dec ndays)]
    (if (zero? ndays)
      the-days
      (recur
        (conj the-days current)
        (next-day current)
        (dec ndays)))))


