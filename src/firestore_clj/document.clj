(ns firestore-clj.document
  (:import
   com.google.cloud.firestore.DocumentReference
   java.util.HashMap))

(defn collection [source coll]
  (.collection source coll))

(defn document [coll id]
  (.document coll id))

(defn stringify-keys [m]
  (into {} (map (fn [[k v]] [(name k) v]) m)))

(defn put
  ([coll doc]
   (.getUpdateTime
    (.get
     (.set
      (.document coll) (HashMap. (stringify-keys doc))))))
  ([coll id doc]
   (.getUpdateTime
    (.get
     (.set
      (document coll id) (HashMap. (stringify-keys doc)))))))

(defn keywordize-keys [m]
  (into {} (map (fn [[k v]] [(keyword k) v]) m)))

(defn get
  ([coll]
   (into {} (map (fn [doc] [(.getId doc) (keywordize-keys (.getData doc))]) (.getDocuments (.get (.get coll))))))
  ([coll id]
   (let [doc (.get (.get (document coll id)))]
     (when (.exists doc)
       (keywordize-keys (.getData doc))))))
