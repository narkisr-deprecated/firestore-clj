(ns firestore-clj.document
  (:import
   com.google.cloud.firestore.DocumentReference
   java.util.HashMap))

(defn stringify-keys [m]
  (into {} (map (fn [[k v]] [(name k) v]) m)))

(defn put [db coll id doc]
  (.getUpdateTime
   (.get
    (.set
     (.document
      (.collection db coll) id) (HashMap. (stringify-keys doc))))))

(defn keywordize-keys [m]
  (into {} (map (fn [[k v]] [(keyword k) v]) m)))

(defn get [db coll id]
  (let [doc (.get (.get (.document (.collection db coll) id)))]
    (when (.exists doc)
      (keywordize-keys (.getData doc)))))
