(ns clj-wordpress.core
  (:require [clj-http.client :as client]
            [clojure.xml :as xml]
            [clojure.zip :as zip]))

(defn assemble-request 
  "assemble the xmlrpc request: Method is the method to call, params is a vector of values"
  [method params]
  (with-out-str
    (xml/emit {:tag :methodCall, :content 
             [{:tag :methodName, :content [method]}
              {:tag :params, :content 
                (mapv (fn [x] {:tag :param, :content [
                                {:tag :value, :content [  
                                  (str x)]}]}) params)}]}
   )))

(defn do-request 
  "makes an xmlhttp request"
  [host method params]
  (client/post (str host "/xmlrpc.php") 
               {:content-type :text/html
                :body (assemble-request method params)}))
                                   

(defn prepare-params
  "appends the params for the request to blog_id username pass"
  [config params]
  (loop [c (vector (:blog-id config)
                   (:username config)
                   (:password config))
         p params]
    (if (first p) 
      (recur (conj c (first p)) (rest p))
      c)))

(defn request
  "prepares and executes a request"
  [config method params]
  (do-request (:host config)
           method
           (prepare-params config params)))


(defn parse-xml 
  "parses xml from a string"
  [s]
  (zip/xml-zip (xml/parse (java.io.ByteArrayInputStream. (.getBytes s)))))


(defn beautify
  "beautifies the map received from parse-xml"
  [m]
  (if (not (map? m))
    [:text m]
    [(:tag m) (reduce (fn [x y]
                        (assoc x (first y) (second y)))
                      {}
                      (mapv beautify (:content m)))]))

(defn beautify-xml
  "beautifies the xml received from the commands"
  [s]
  (reduce (fn [x y] (assoc x (first y) (second y)))
          {}
          (mapv beautify (parse-xml s))))


(defn initialize
  "Initializing wordpress"
  [config]
  (fn [method params]
    (first
     (parse-xml 
      (:body 
       (request config (str "wp." method) params))))))
                       
