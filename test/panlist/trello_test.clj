(ns panlist.trello-test
  (:require
    [clojure.test :refer :all]
    [panlist.trello :as trello]))

(deftest add-index-test
         (is (= (trello/add-index [{:name "Jack"} {:name "Mary"}] :index)
                [{:name "Jack", :index 0} {:name "Mary", :index 1}])))

(def sample
  {:lists
   [{:name "To do", :id 42}
    {:name "Done", :id 52}]
   :cards
   [{:name "Plan", :idList 52}
    {:name "Implement", :idList 42}
    {:name "Test", :idList 42, :desc "Everything"}]})

(def expected
  ["To do"
   ["Implement"
    ["Test" "Everything"]]
   "Done"
    ["Plan"]])

(deftest process-board-test
  (is (= (trello/process-board sample) expected)))
