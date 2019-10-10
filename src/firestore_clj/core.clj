(ns firestore-clj.core
  (:require
   [firestore-clj.document :refer (put get collection collection-group document)]
   [firestore-clj.query :refer (into-query)]
   [firestore-clj.connection :refer (create-channel connect)]))

(def db (atom nil))

(defn setup []
  (reset! db (connect (create-channel "10.8.4.102:8086") "foo")))

(def landmarks {:SF [{:site "Golden Gate Bridge" :type "bridge" :cost 0}
                     {:site "Legion of Honor" :type "museum" :cost 5}]
                :LA  [{:site "Griffith Park" :type "park" :cost 0}
                      {:site "The Getty" :type "museum" :code 10}]
                :DC [{:site "Lincoln Memorial" :type "memorial" :cost 0}
                     {:site "National Air and Space Museum" :type "museum" :cost 11}]
                :TOK [{:site "Ueno Park" :type "park" :cost 0}
                      {:site "National Museum of Nature and Science" :type "museum" :cost 1}]
                :BJ [{:site "Jingshan Park" :type "park" :cost 0}
                     {:site "Beijing Ancient Observatory" :type "museum" :cost 22}]})

(defn populate [db]
  (let [cities (collection db "cities")]
    (doseq [[city sites] landmarks]
      (doseq [site sites]
        (let [landmarks (collection (document cities (name city)) "landmarks")]
          (put landmarks site))))))

(comment
  (setup)
  (populate @db)
  (get (collection (document (collection @db "cities") "BJ") "landmarks"))
  (get (into-query (collection-group @db "landmarks") [:type == "museum"] [:cost < 6])))
