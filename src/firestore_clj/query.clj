(ns firestore-clj.query
  (:require
   [firestore-clj.document :refer (collection)]))

[[:state == "CA"] [:name >= "Denver"]]

(def fns {== (fn [k v] (fn [doc] (.whereEqualTo doc k v)))
          <= (fn [k v] (fn [doc] (.whereLessThanOrEqualTo doc k v)))
          >= (fn [k v] (fn [doc] (.whereGreaterThanOrEqualTo doc k v)))
          < (fn [k v] (fn [doc] (.whereLessThan doc k v)))
          > (fn [k v] (fn [doc] (.whereGreaterThan doc k v)))
          :contains  (fn [k v] (fn [doc] (.whereArrayContains doc k v)))})

(defn into-query [coll expr]
  (reduce (fn [d f] (f d)) (map (fn [[k f v]] ((fns f) k v))) coll))

(defn query [db coll expr]
  (.get (into-query (collection db coll) expr)))
