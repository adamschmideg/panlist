(ns panlist.calendar
  [:import [java.util Calendar]])

(defn next-day [day]
  (let [d2 (.clone day)
        _ (.add d2 Calendar/DAY_OF_YEAR 1)]
    d2))

(defn zero-based-day-of-week [day]
  (dec (.get day Calendar/DAY_OF_WEEK)))

(defn get-month [day]
  (.get day Calendar/MONTH))

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

(defn group-by-week [dates]
  (let [from-monday (drop
                      (zero-based-day-of-week (first dates))
                      dates)]
    (partition 7 from-monday)))

(defn group-by-month [weeks]
  (partition-by #(get-in % [0 0])
    weeks))

(defn yearly-calendar [year]
  (let [start (Calendar/getInstance)
        _ (.set start year Calendar/JANUARY 1)]
    (-> start (days 365) group-by-week group-by-month)))


