(ns firestore-clj.core
  (:require
   [firestore-clj.document :refer (put get collection document)]
   [firestore-clj.query :refer (into-query)]
   [firestore-clj.connection :refer (create-channel connect)]))

(def db (connect (create-channel "10.8.4.102:8086") "foo"))

(def landmarks {:SF [{:site "Golden Gate Bridge" :type "bridge"}
                     {:site "Legion of Honor" :type "museum"}]
                :LA  [{:site "Griffith Park" :type "park"}
                      {:site "The Getty" :type "museum"}]
                :DC [{:site "Lincoln Memorial" :type "memorial"}
                     {:site "National Air and Space Museum" :type "museum"}]
                :TOK [{:site "Ueno Park" :type "park"}
                      {:site "National Museum of Nature and Science" :type "museum"}]
                :BJ [{:site "Jingshan Park" :type "park"}
                     {:site "Beijing Ancient Observatory" :type "museum"}]})

(defn populate [db]
  (let [cities (collection db "cities")]
    (doseq [[city sites] landmarks]
      (doseq [site sites]
        (let [landmarks (collection (document cities (name city)) "landmarks")]
          (put landmarks site))))))

(comment
  (populate db)
  (collection db "users")
  (put (collection db "users") "foo" {:name "zz" :last "bar"})
  (get (collection (document (collection db "cities") "BJ") "landmarks")))
