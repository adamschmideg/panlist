(ns panlist.trello
  (:require
    [cheshire.core :as json]
    [clojure.set :as set]))

(defn add-index
  "Add an index column called key, :index by default"
  ([coll key]
   (map-indexed (fn [idx elem] (assoc elem key idx))
               coll))
  ([coll] (add-index coll :index)))

;; TODO: convert to a nested list of content only
(defn process-board
  [board]
  (let [lists (as-> board $
                    (:lists $)
                    (add-index $ :list-index)
                    (set/project $ [:name :id :list-index])
                    (set/rename $ {:name :listName}))
        cards (as-> board $
                    (:cards $)
                    (add-index $ :card-index)
                    (set/project $ [:name :desc :idList :card-index]))
        joined (set/join lists cards {:id :idList})
        grouped (->> joined
                     (sort-by (juxt :list-index :card-index))
                     (group-by :listName))]
    (map (fn [[k v]] [k (map :name v)]
           grouped))))

(defn load-board
  [file]
  (-> file slurp (json/parse-string true) process-board))

