(ns clj-wordpress.core
  (:require [clj-http.client :as client]
            [clojure.xml :as xml]
            [clojure.zip :as zip]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn assemble-request 
  "assemble the xmlrpc request: Method is the method to call, params is a vector of maps - each containing {:type :<type> and :value <value>"
  [method params]
  (with-out-str
    (xml/emit {:tag :methodCall, :content 
             [{:tag :methodName, :content [method]}
              {:tag :params, :content 
                (mapv (fn [x] {:tag :param, :content [
                        {:tag (get x :type), :content [
                                  (str (get x :value))]}]}) params)}]}
   )))