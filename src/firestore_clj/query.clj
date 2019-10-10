(ns firestore-clj.query
  (:require
   [firestore-clj.document :refer (collection)]))

[[:state == "CA"] [:name >= "Denver"]]

(def fns {== (fn [k v] (fn [coll] (.whereEqualTo coll k v)))
          <= (fn [k v] (fn [coll] (.whereLessThanOrEqualTo coll k v)))
          >= (fn [k v] (fn [coll] (.whereGreaterThanOrEqualTo coll k v)))
          < (fn [k v] (fn [coll] (.whereLessThan coll k v)))
          > (fn [k v] (fn [coll] (.whereGreaterThan coll k v)))
          :contains  (fn [k v] (fn [coll] (.whereArrayContains coll k v)))})

(defn into-query [coll & expr]
  (reduce (fn [d f] (f d)) coll (map (fn [[k f v]] ((fns f) (name k) v)) expr)))
