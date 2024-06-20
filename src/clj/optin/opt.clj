(ns optin.opt
  (:require
   [clojure.java.jdbc :as jdbc]
   [optin.generator]))

;; DB connection properties
(def db-spec {:dbtype "postgresql"
              :port 5432
              :dbname "demo"
              :user "postgres"
              :password "postgres"})


;; optin/optout transitions
(def status-state
  {[:none :none] :none
   [:none :optin] :optin
   [:none :optout] :optout
   [:optin :optin] :optin
   [:optin :optout] :optout
   [:optout :optout] :optout
   [:optout :optin] :optin})


(def generator  optin.generator/generate-contact)

;; Insert contact records in batches.
(defn insert-contacts [total batch-size]
  (doseq [batch (partition batch-size batch-size nil (take total (repeatedly generator)))]
    (println "Inserting batch. batch-size:" (count batch))
    (jdbc/insert-multi! db-spec "optin.optin" batch)))

;; Fetches as single contact from DB
(defn fetch-contact [contact]
  (jdbc/query db-spec (str "SELECT * FROM optin.optin WHERE contact='" contact "'")))


;; Updates contact status (optin/optout) based on state transition defined in "status-update"
;; - Get the contact from DB
;; - Get next status optin/optout based on state transition
;; - Save the contact
(defn update-optin-status [contact status]
  (def db-contact (fetch-contact contact))
  (def db-status (or (:status db-contact) :none))
  (def next-status (get status-state [(keyword db-status) (keyword status)]))

  (when (nil? next-status)
    (throw (ex-info
            "Cannot fetch the contact"
            {:contact contact})))

  (println "New status:", next-status)
  ;; perhaps save keyword in db for status?
  (jdbc/execute! db-spec (str "UPDATE optin.optin SET status='" (name next-status) "' WHERE contact='" contact "'")))


;; to be executed from commandline
(defn import-contacts [opts]
  (println (str "Record size:" (:size opts)))
  (insert-contacts  (:size opts) (:batch-size opts)))

;; to be executed from commandline
(defn update-contact [opts]
  (println (str "Updating  contact:" (:contact opts) " status:" (:status opts)))
  (update-optin-status  (:contact opts) (:status opts)))