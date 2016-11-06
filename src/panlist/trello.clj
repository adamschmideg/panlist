(ns panlist.trello
  (:require
    [cheshire.core :as json]
    [clojure.set :as set]))

;; TODO: keep order of cards and lists
(defn load-board
  [board-file]
  (let [board (-> board-file slurp (json/parse-string true))
        lists (map #(select-keys % [:name :id]) (:lists board))
        cards (map #(select-keys % [:name :desc :idList]) (:cards board))
        joined (set/join (set/rename lists {:name :listName})
                         cards
                         {:id :idList})
        narrowed (map #(select-keys % [:name :desc :listName]) joined)]
    (group-by :listName narrowed)))


