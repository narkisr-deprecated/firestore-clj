(ns firestore-clj.connection
  (:import
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

(defn create-channel [host-port]
  (-> (InstantiatingGrpcChannelProvider/newBuilder)
      (.setEndpoint host-port)
      (.setChannelConfigurator
       (reify ApiFunction
         (apply [this managed-builder] (.usePlaintext managed-builder))))
      (.build)))

(defn creds-provider []
  (NoCredentialsProvider/create))

(defn connect
  ([project]
   (-> (FirestoreOptions/newBuilder)
       (.setProjectId project)
       (.build)
       (.getService)))
  ([c project]
   (-> (FirestoreOptions/newBuilder)
       (.setProjectId project)
       (.setChannelProvider c)
       (.build)
       (.getService))))
