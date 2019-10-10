(ns firestore-clj.core
  (:require
   [firestore-clj.document :refer (put get)]
   [firestore-clj.connection :refer (create-channel connect)]))

(def db (connect (create-channel "10.8.4.102:8086") "foo"))

(comment
  (put db "users" "foo" {:name "zz" :last "bar"})
  (get db "users" "foo"))
