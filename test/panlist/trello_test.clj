(ns panlist.trello-test
  (:require
    [clojure.test :refer :all]
    [panlist.trello :as trello]))

(testing "add-index"
         (is (= (trello/add-index [{:name "Jack"} {:name "Mary"}] :index)
                [{:name "Jack", :index 0} {:name "Mary", :index 1}])))
