(ns firestore-clj.connection
  (:import
   com.google.cloud.firestore.DocumentReference
   com.google.api.core.ApiFunction
   com.google.api.gax.core.CredentialsProvider
   com.google.api.gax.core.NoCredentialsProvider
   com.google.api.gax.grpc.GrpcTransportChannel
   com.google.api.gax.grpc.InstantiatingGrpcChannelProvider
   com.google.api.gax.rpc.FixedTransportChannelProvider
   io.grpc.ManagedChannel
   io.grpc.ManagedChannelBuilder
   com.google.cloud.firestore.FirestoreOptions
   com.google.auth.oauth2.GoogleCredentials))

(defn channel-builder [hostport]
  (-> (ManagedChannelBuilder/forTarget hostport)
      (.usePlaintext)
      (.build)))

#_(defn create-channel [host-port]
    (FixedTransportChannelProvider/create
     (GrpcTransportChannel/create (channel-builder host-port))))

(defn create-channel [host-port]
  (-> (InstantiatingGrpcChannelProvider/newBuilder)
      (.setEndpoint host-port)
      (.setChannelConfigurator
       (reify ApiFunction
         (apply [this managed-builder] (.usePlaintext managed-builder))))
      (.build)))

(defn creds-provider []
  (NoCredentialsProvider/create))

(defn connect [c project]
  (-> (FirestoreOptions/newBuilder)
      (.setProjectId project)
      (.setChannelProvider c)
      (.build)
      (.getService)))

(comment
  (def db (connect (create-channel "10.8.4.102:8086") "foo"))
  (.get (.set (.document (.collection db "users") "ronen") (java.util.HashMap. {"foo" 1}))))
