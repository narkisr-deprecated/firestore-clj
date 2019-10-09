(defproject firestore-clj "0.1.0"
  :description "Firestore Clojure client"
  :license  {:name "Apache License, Version 2.0" :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [
      [org.clojure/clojure "1.10.1"]
      [com.google.cloud/google-cloud-firestore "1.13.0"]
  ]
  :repl-options {:init-ns firestore-clj.core})
