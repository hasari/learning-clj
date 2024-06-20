(ns optin.generator
  "test test"
  (:require
   [clojure.java.io :as io]
   [towersampling.tower]
   [clojure.string :as string]))



(def contact-probs {:email 0.55
                    :phone 0.40
                    :sms 0.05})

(def status-probs {:optin 0.30
                   :optout 0.50
                   :none 0.20})

;; Generates a random email address
(defn generate-email []
  (let [n (long (rand-int 10000000))] (str "email_" n  "@test.com")))

;; Generates a random phone number
(defn generate-phone [] (let [n (long (rand-int 10000000))] n))


;; Generates a random contact record: phone, sms, email. 
;; Contact is generated based on distribution defined in contact-probs and status-probs
(defn generate-contact []
  (let [contact-sampler (towersampling.tower/tower-sampling contact-probs)
        status-sampler (towersampling.tower/tower-sampling status-probs)]
    ((fn [] (let [c (contact-sampler)
                  s (status-sampler)
                  status (if (= :none s) nil (name s))]
              (case c
                :email {:contact (generate-email) :contact_type "email" :status status}
                :sms {:contact (generate-phone) :contact_type "sms" :status status}
                :phone {:contact (generate-phone) :contact_type "phone" :status status}))))))


;; Generates contact records and saves it to file
(defn generate-data [file, size]
  (with-open [w (io/writer file)]
    (let [contact-generator (generate-contact)]
      (doseq [c (take size (repeatedly contact-generator))]
        (.write w (str (:contact c) "," (:contact_type c) "," (:status c) "\n"))))))



;; Method to be executed from commandline.
(defn generate [opts]
  (print (str "size:" (:size opts)))
  (generate-data "contacts.csv" (:size opts)))